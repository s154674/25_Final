package entity;

import controller.IPlayerDecisions;

public class Player {
    private String name;
    private Account account;
    private HoldingCompany holdingCompany;
    private IPlayerDecisions brain;
    private boolean broke = false;
    private boolean jailed;
    private int daysJailed;
    private int pardons = 0;


    public Player(String name, Account account, HoldingCompany holdingCompany, IPlayerDecisions brain) {
        this.name = name;
        this.account = account;
        this.jailed = false;
        this.brain = brain;
        brain.setPlayer(this);
        this.holdingCompany = holdingCompany;
        holdingCompany.setOwner(this);
    }

    // Getter til attributter - og toString
    public String getName() {
        return name;
    }
    public String toString() {
        return name;
    }
    public Account getAccount() {
        return account;
    }
    public HoldingCompany getHoldingCompany() {
        return holdingCompany;
    }
    public IPlayerDecisions decide(){
        return brain;
    }
    public boolean getBroke(){
        return broke;
    }

    // Fængsel
    public boolean getJailed() {
        return jailed;
    }
    public int getDaysJailed() {
        return daysJailed;
    }
    public Player setJailed(boolean jailed) {
        this.jailed = jailed;
        return this;
    }
    public void resetDaysJailed(){
        daysJailed = 0;
    }
    public void tickDaysJailed(){
        daysJailed++;
    }
    public int getPardons(){
        return pardons;
    }
    public void grantPardon(){
        pardons++;
    }
    public void usePardon(){
        pardons--;
    }

    // Har en spiller nok penge på sin konto til at kunne betale
    // Kan en spiller sælge ting for at kunne få råd til at betale
    public boolean canAfford(int amount){
        return account.doesContain(amount);
    }
    public boolean canAffordAfterSellings(int amount){
        return fortune() >= amount;
    }

    // Beregner en den samlede værdi for en spiller.
    public int fortune(){
        return account.getBalance() + holdingCompany.value();
    }
    // Spilleren modtager penge
    public void receive(int amount) {
        account.deposit(amount);
    }
    // Spilleren betaler en anden spiller penge.
    public void pay(int amount, Player anotherPlayer) {
        int payAmount = pay(amount);
        anotherPlayer.receive(payAmount);
    }
    // Spilleren betaler banken penge
    public int pay(int amount) {
        if (canAffordAfterSellings(amount)){
            if (!canAfford(amount)) brain.sellToPay(amount);
            account.withdraw(amount);
            return amount;
        } else {
            // Spilleren har tabt hvis koden når hertil. en evt. anden spiller skal modtage den værdi spilleren havde
            bankrupt();
            int fortune = fortune();
            pay(fortune);
            return fortune;
        }
    }

    // Denne metode køres når spilleren går bankerot (har tabt)
    public void bankrupt(){
        // Alt skal sælges!
        holdingCompany.sellAll();
        broke = true;
        brain.wentBankrupt();
    }


    // Besvarer om en spiller kan gøre nogle handler handle. (købe huse/hoteller, sælge huse/hoteller eller sælge grunde)
    public boolean canTrade(){
        return holdingCompany.tradables().size()>0 || holdingCompany.downgradables().size()>0 || holdingCompany.downgradables().size()>0;
    }
}
