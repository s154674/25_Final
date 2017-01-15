package controller;

import entity.Player;
import entity.fields.Field;
import entity.fields.Ownable;


public interface IPlayerDecisions {
    void setPlayer(Player player);
    String yourName(int id, int players);

    // Beskeder til spilleren
    void yourRoll(int pairCount);
    void YourRollJailed(int tri, int tries);
    void landedOn(Field field);
    void landedOnAndPayed(Field field, int mone);
    void landedOnAndPayedPlayer(Ownable property, int rent, Player owner);
    void landedOnAndDrewChanceCard();
    void wentBankrupt();
    void youWon();

    // Boolske beslutninger:
    boolean wantsToTrade();
    boolean acceptTrade(Ownable ownProperty, Ownable otherProperty);
    boolean wantsToBuy(Ownable property);
    boolean wantsToUsePardon();
    boolean wantsToPayBail();
    boolean payPercentage(int amount, int percentage);
    boolean wantsToSellToAfford(Ownable property);

    // svære beslutninger
    void optionalTrading();
    void sellToAfford(Ownable property);
    void sellToPay(int amount);
}
