package controller;

import boundary.PlayerBoundary;
import entity.Player;
import entity.fields.Field;
import entity.fields.Ownable;
import entity.fields.Street;

import java.util.ArrayList;


public class PlayerDecisions implements IPlayerDecisions {
    private PlayerBoundary boundary;
    private Player player;
    public PlayerDecisions(PlayerBoundary boundary){
        this.boundary = boundary;
    }
    public void setPlayer(Player player){
            this.player = player;
    }
    public String yourName(int id, int players){
        return boundary.yourName(id, players);
    }

    // Beskeder til spilleren
    public void yourRoll(int paircount) { boundary.yourRoll(player, paircount); }
    public void YourRollJailed(int tri, int tries){
        boundary.YourRollJailed(player, tri, tries);
    }
    public void landedOn(Field field){
        boundary.landedOn(player, field);
    }
    public void landedOnAndPayed(Field field, int money){
        boundary.landedOnAndPayed(player, field, money);
    }
    public void landedOnAndPayedPlayer(Ownable property, int rent, Player owner){
        boundary.landedOnAndPayedPlayer(player, property, rent, owner);
    }
    public void landedOnAndDrewChanceCard(){
        boundary.landedOnAndDrewChanceCard(player);
    }
    public void wentBankrupt(){
        boundary.wentBankrupt(player);
    }
    public void youWon(){
        boundary.youWon(player);
    }


    // Boolske beslutninger:
    public boolean wantsToTrade(){
        return boundary.wantsToTrade(player);
    }
    public boolean acceptTrade(Ownable ownProperty, Ownable otherProperty) {
        return boundary.acceptTrade(player, ownProperty, otherProperty);
    }
    public boolean wantsToBuy(Ownable property) {
        return boundary.wantsToBuy(player, property);
    }
    public boolean wantsToUsePardon() {
        return boundary.wantsToUsePardon(player);
    }
    public boolean wantsToPayBail() {
        return boundary.wantsToPayBail(player);
    }
    public boolean payPercentage(int amount, int percentage) {
        return boundary.payPercentage(player, amount, percentage);
    }
    public boolean wantsToSellToAfford(Ownable property){
        return boundary.wantsToSellToAfford(player, property);
    }



    // Svære beslutninger
    // ----Spilleren handler af ejen fri vilje. Han kan købe huse/hoteller, sælge huse/hoteller og sælge grunde.
    public void optionalTrading(){
        String choice = "";
        String[] options = new String[5];
        while (choice != boundary.back){
            options[0] = player.getHoldingCompany().upgradables().size()>0 ? boundary.purchaseBuildings : " ";
            options[1] =  player.getHoldingCompany().downgradables().size()>0 ? boundary.sellBuildings : " ";
            options[2] =  player.getHoldingCompany().tradables().size()>0 ? boundary.sellProperties : " ";

            boolean otherTraders = false;
            for (Player aPlayer : GameState.players)
                if (aPlayer.getHoldingCompany().tradables().size() > 0 && aPlayer != player)
                    otherTraders = true;

            options[3] = player.getHoldingCompany().tradables().size()>0 && otherTraders ? boundary.exchangeProperties : " ";
            options[4] = boundary.back;

            choice = boundary.optionalTrading(player, options);
            evaluateMenuChoice(choice);

            GameHelper.updateGUI();
        }

    }
    // ----En spiller vil sælge for at få råd til en ejendom
    public void sellToAfford(Ownable property){
        String choice = "";
        String[] options = new String[3];
        while (!player.canAfford(property.getPrice()) && choice != boundary.cancelPurchase){
            options[0] = player.getHoldingCompany().downgradables().size()>0 ? boundary.sellBuildings : " ";
            options[1] = player.getHoldingCompany().tradables().size()>0 ? boundary.sellProperties : " ";
            options[2] = boundary.cancelPurchase;

            choice = boundary.sellToAfford(player, property, options);
            evaluateMenuChoice(choice);

            GameHelper.updateGUI();
        }
    }
    // ----En spiller skal sælge for at kunne betale en anden spiller
    public void sellToPay(int amount) {
        String choice = "";
        String[] options = new String[2];
        while (!player.canAfford(amount)){
            options[0] = player.getHoldingCompany().downgradables().size()>0 ? boundary.sellBuildings : " ";
            options[1] = player.getHoldingCompany().tradables().size()>0 ? boundary.sellProperties : " ";

            choice = boundary.sellToPay(player, amount, options);
            evaluateMenuChoice(choice);

            GameHelper.updateGUI();
        }
    }


    // HELPERE
    // Laver menuer en spiller kan bruge til at vælge hvad han vil
    private void tradeFields() {
        // Vælg min ejendom
        ArrayList<Ownable> myProperties = player.getHoldingCompany().tradables();
        Ownable ownProperty =  selectOwnable(myProperties, boundary.selectOwnProperty);

        // Vælg hvilken ejendom du vil bytte med
        ArrayList<Ownable> otherPlayersProperties = new ArrayList<>();
        for (Player somePlayer : GameState.players)
            otherPlayersProperties.addAll(somePlayer.getHoldingCompany().tradables());
        otherPlayersProperties.removeAll(myProperties);
        Ownable otherProperty =  selectOwnable(otherPlayersProperties, boundary.selectOthersProperty);
        Player otherPlayer = otherProperty.getOwner().getOwner();

        // Spørg begge spillere om de vil handle
        boolean bothSure = boundary.confirmTrade(player, ownProperty, otherProperty) && otherPlayer.decide().acceptTrade(otherProperty, ownProperty);
        if (bothSure){
            // Byt ejere
            player.getHoldingCompany().removeProperty(ownProperty).addProperty(otherProperty);
            otherPlayer.getHoldingCompany().removeProperty(otherProperty).addProperty(ownProperty);
        }
    }
    private void buyBuildnings() {
        Street street = selectStreet(player.getHoldingCompany().upgradables(), boundary.selectStreetToUpgrade);
        if (boundary.confirmUpgrade(player,street))
            street.upgrade();
    }
    private void sellBuildnings() {
        Street street = selectStreet(player.getHoldingCompany().downgradables(), boundary.selectStreetToDowngrade);

        if (boundary.confirmDowngrade(player,street))
            street.downgrade();
    }
    private void sellFields() {
        Ownable property = selectOwnable(player.getHoldingCompany().tradables(), boundary.selectPropertyToSell);

        if (boundary.confirmSell(player, property))
            property.sell();
    }


    // HELPERE til at lave menuer
    private Street selectStreet(ArrayList<Street> streets, String msg) {
        // Hvis der kun er én gade så brug den.
        if (streets.size()==1)
            return streets.get(0);

        // Lav en liste med navne på felter
        String[] stNames = new String[streets.size()];
        for (int i = 0; i < stNames.length; i++){
            stNames[i] = streets.get(i).getName();
        }
        String choice;
        choice = boundary.selectOwnable(player,msg, stNames);

        for (Street street : streets) {
            if (street.getName()==choice)
                return street;
        }
        return null;
    }
    private Ownable selectOwnable(ArrayList<Ownable> properties, String message) {
        // Hvis der kun er én gade så brug den.
        if (properties.size()==1)
            return properties.get(0);

        // Lav en liste med navne på felter
        String[] fNames = new String[properties.size()];
        for (int i = 0; i < fNames.length; i++){
            fNames[i] = properties.get(i).getName();
        }
        String choice;
        choice = boundary.selectOwnable(player,message, fNames);

        for (Ownable property : properties) {
            if (property.getName()==choice)
                return property;
        }
        return null;
    }

    private void evaluateMenuChoice(String choice){
        switch (choice){
            case "Køb bygninger": buyBuildnings(); break;
            case "Sælg bygninger": sellBuildnings(); break;
            case "Sælg grunde": sellFields(); break;
            case "Byt grunde med andre spillere": tradeFields(); break;
        }
    }
}

