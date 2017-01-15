package controller;
import entity.*;

import static controller.GameState.*;


public class GameController {
    // Spillerne skiftes til at have ture så længe spillet er i gang
    public void playOneGame(){
        int i = 0;
        Player currentPlayer;
        while(gameIsOn()){
            currentPlayer = players[i%players.length];
            if (!currentPlayer.getBroke())
                playOneTurn(currentPlayer);
            i++;
        }
        Player winner = findWinner();
        winner.decide().youWon();
    }

    // En tur for en spiller
    private void playOneTurn(Player currentPlayer){
        boundary.showRoll(currentPlayer, players, cup);;
        if (currentPlayer.getJailed()){
            // Spilleren er i fængsel
            playOneJailedTurn(currentPlayer);
        } else {
            // Spilleren starter almindeligt
            playOneNormalTurn(currentPlayer,0);
        }
    }
    // En tur hvor spilleren starter i fængsel
    private void playOneJailedTurn(Player currentPlayer) {
        int bail = 1000;
        int pairCount = 0;
        if (currentPlayer.getDaysJailed() == 3){
            // Spilleren har været fængslet i tre dage
            currentPlayer.pay(bail);
            currentPlayer.setJailed(false).resetDaysJailed();
        } else {
            currentPlayer.tickDaysJailed();
            // Spilleren har endnu ikke været i fængsel i tre dage
            if (currentPlayer.getPardons()>0 && currentPlayer.decide().wantsToUsePardon()){
                // Spilleren har et benådelseskort og vil gerne bruge det
                currentPlayer.usePardon();
                currentPlayer.setJailed(false).resetDaysJailed();
            } else {
                if (currentPlayer.canAffordAfterSellings(bail) && currentPlayer.decide().wantsToPayBail()) {
                    // Spilleren har råd til at betale sin kaution og vil gerne gøre det
                    currentPlayer.pay(bail); //
                    currentPlayer.setJailed(false).resetDaysJailed();
                } else {
                    // Spilleren vil flygte (slå sig ud af fængslet på 3 forsøg)
                    int tries = 3;
                    int tri = 1;
                    do {
                        currentPlayer.decide().YourRollJailed(tri, tries);
                        cup.rollAll();
                        if (cup.isPair()) {
                            currentPlayer.setJailed(false).resetDaysJailed();
                            GameHelper.forward(currentPlayer, cup.getSum());
                            pairCount = 1;
                        }
                        boundary.showRoll(currentPlayer, players, cup);
                        tri++;
                    } while (tri <= tries && currentPlayer.getJailed());
                }
            }
        }
        // Vi spiller almindeligt
        if (!currentPlayer.getJailed() && !currentPlayer.getBroke())
            playOneNormalTurn(currentPlayer, pairCount);
    }


    // En almindelig tur (slå med terninger og ryk) ekstraslag ved par
    private void playOneNormalTurn(Player currentPlayer, int initialPairCount){
        int pairCount = initialPairCount;
        do {
            // Rul terninger
            currentPlayer.decide().yourRoll(pairCount);
            cup.rollAll();
            boundary.showRoll(currentPlayer, players, cup);
            // husk at tælle par
            if (cup.isPair())
                pairCount++;

            if (pairCount >= 3){
                // På tredje par sendes spilleren i fængsel
                GameHelper.sendToJail(currentPlayer);
            } else {
                // ellers rykker spilleren som terningerne viser
                GameHelper.forward(currentPlayer, cup.getSum());
            }
            GameHelper.updateGUI();
        } while (cup.isPair() && !currentPlayer.getJailed());
        // Hvis spilleren kan handle og han vil handle
        if (currentPlayer.canTrade() && currentPlayer.decide().wantsToTrade())
            currentPlayer.decide().optionalTrading();
    }

    // Skal besvare om spillet er i gang
    private boolean gameIsOn(){
        int playing = 0;
        for (Player player : players) {
            if (!player.getBroke())
                playing++;
        }
        return playing>1;
    }
    // Undersøg hvem der vandt
    private Player findWinner() {
        for (Player player : players)
            if (!player.getBroke())
                return player;
        return null;
    }

}
