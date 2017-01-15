package boundary;

import desktop_resources.GUI;
import entity.Player;
import entity.fields.Field;
import entity.fields.Ownable;
import entity.fields.Street;


public class PlayerBoundary {
    public final String back = "tilbage";
    public final String cancelPurchase = "Stop køb";
    public final String sellBuildings = "Sælg bygninger";
    public final String sellProperties = "Sælg grunde";
    public final String purchaseBuildings = "Køb bygninger";
    public final String exchangeProperties = "Byt grunde med andre spillere";

    public final String selectOwnProperty = "Vælg din ejendom";
    public final String selectOthersProperty = "Vælg den ejendom du vil bytte med";
    public final String selectStreetToUpgrade = "Vælg en gade du vil bygge på";
    public final String selectStreetToDowngrade = "Vælg en gade du vil fjerne en bygning fra";
    public final String selectPropertyToSell = "Vælg en ejendom du vil sælge";

    // Spørg spilleren om hans navn
    public String yourName(int id, int players) {
        String name;
        do {
            name = GUI.getUserString("Spiller ("+id+"/"+players+") - Hvad hedder du?");
        } while (name.isEmpty());
        return name;
    }

    // Beskeder til spilleren:
    public void yourRoll(Player player, int pairCount) {
        String pairText = pairCount>0 ? pairCount+" par -" : "";
        GUI.showMessage(player + " - "+pairText+" Rul");
    }
    public void YourRollJailed(Player player, int tri, int tries) {
        GUI.showMessage(player + " - Fængslet: "+player.getDaysJailed()+". Forsøg: " + tri + "/" + tries + ". - rul");
    }
    public void landedOnAndPayed(Player player, Field field, int money) {
        GUI.showMessage(player +" - Du landede på "+field.getName()+", og betalte kr."+money);
    }
    public void landedOnAndPayedPlayer(Player player, Ownable property, int rent, Player owner) {
        GUI.showMessage(player +" - Du landede på "+property.getName()+", og skal betale "+owner+" kr."+rent);
    }
    public void landedOn(Player player, Field field) {
        GUI.showMessage(player+" - Du landede på "+field.getName());
    }
    public void landedOnAndDrewChanceCard(Player player) {
        GUI.showMessage(player + " - Du landede på \"Prøv lykken\" Tryk ok, når du har læst dit kort");
    }
    public void wentBankrupt(Player player) {
        GUI.showMessage(player + " - Du gik bankerot");
    }
    public void youWon(Player player) {
        GUI.showMessage(player + " - Tillykke, du vandt!");
    }


    // Boolske beslutninger:
    public boolean wantsToTrade(Player player) {
        return GUI.getUserLeftButtonPressed(player + " - Vil du mere i din tur?", "Ja", "Nej");
    }
    public boolean acceptTrade(Player player, Ownable ownProperty, Ownable otherProperty) {
        return GUI.getUserLeftButtonPressed(player + " - Du er blevet tilbudt at bytte din ejendom \""+ownProperty.getName()+"\" for \""+otherProperty.getName()+"\", vil du acceptere byttehandlen?", "Ja", "Nej");
    }
    public boolean wantsToBuy(Player player, Ownable property){
        return GUI.getUserLeftButtonPressed(player + " - vil du købe " + property + ", for "+property.getPrice(), "Ja", "Nej");
    }
    public boolean payPercentage(Player player, int amount, int percentage) {
        return GUI.getUserLeftButtonPressed(player + " - Vil du helst betale kr."+amount+" eller "+percentage+"% af din samlede værdier", percentage+"%","kr."+amount);
    }
    public boolean wantsToUsePardon(Player player) {
        return GUI.getUserLeftButtonPressed(player + " - Du er i fængsel. Du har "+player.getPardons()+" benådelser fra kongen. Vil du bruge en på at undslippe fængslet nu?", "Ja", "Nej");
    }
    public boolean wantsToPayBail(Player player) {
        return GUI.getUserLeftButtonPressed(player+" - Du er i fængsel. Vil du købe dig ud af fængslet for 1000?", "Ja", "Nej");
    }
    public boolean wantsToSellToAfford(Player player, Ownable property) {
        int missing = property.getPrice()-player.getAccount().getBalance();
        return GUI.getUserLeftButtonPressed(player+ " - Du mangler kr."+missing+", vil du prøve at sælge for at få råd?", "Ja", "Nej");
    }

    // Bekræftelser på køb, byg, sælg(huse) og sælg(grund):
    public boolean confirmUpgrade(Player player, Street street) {
        return GUI.getUserLeftButtonPressed(player + " - Er du sikker på at du vil bygge på "+street.getName()+" for kr."+street.getNeighbourhood().getHousePrice()+"?", "Ja", "Nej");
    }
    public boolean confirmDowngrade(Player player, Street street) {
        return GUI.getUserLeftButtonPressed(player + " - Er du sikker på at du vil fjerne en bygning på "+street.getName()+" til kr."+street.getNeighbourhood().getHousePrice()/2+"?", "Ja", "Nej");
    }
    public boolean confirmSell(Player player, Ownable property) {
        return GUI.getUserLeftButtonPressed(player + " - Er du sikker på at du vil sælge "+property.getName()+" for kr."+property.getPrice()+"?", "Ja", "Nej");
    }
    public boolean confirmTrade(Player player, Ownable ownProperty, Ownable otherProperty) {
        return GUI.getUserLeftButtonPressed(player + " - Er du sikker på at du vil bytte "+ownProperty.getName()+" for "+otherProperty.getName()+"?", "Ja", "Nej");
    }



    public String sellToAfford(Player player, Ownable property, String[] options) {
        int missing = property.getPrice() - player.getAccount().getBalance();
        return GUI.getUserButtonPressed(player + " - Du mangler kr."+missing+" til at kunne købe \""+property.getName()+"\", hvordan vil du fortsætte", options);
    }
    public String sellToPay(Player player, int amount, String[] options) {
        int missing = amount - player.getAccount().getBalance();
        return GUI.getUserButtonPressed(player + " - Du skylder kr."+missing+" til at kunne betale din gæld, hvordan vil du fortsætte", options);
    }

    public String optionalTrading(Player player, String[] options) {
        return GUI.getUserButtonPressed(player + " - Hvad vil du?", options);
    }

    // Hjælpefunktioner
    public String selectOwnable(Player player,String msg, String[] options) {
        return GUI.getUserSelection(player+" - "+msg, options);
    }

}
