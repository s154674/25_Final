package controller;

import entity.Player;
import entity.fields.Shipping;

import static controller.GameState.*;


public class GameHelper {
    // Ryk en spiller til et givent felt
    public static void move(Player player, int index, boolean receiveStartMoney){
        int startPos= map.get(player);
        map.put(player, index);

        // Skal spilleren modtage penge for at passere start?
        if (receiveStartMoney && index < startPos) player.receive(4000);

        boundary.movePlayer(player, startPos, index);
        fields[index].landOnField(player);
    }
    // Ryk en spiller et antal felter frem
    public static void forward(Player player, int distance){
        int startPos = map.get(player);
        int endPos = (startPos + distance) % 40;

        move(player, endPos, true);
    }
    // Ryk en spiller et antal felter frem
    public static void backward(Player player, int distance){
        int startPos = map.get(player);
        int endPos = (startPos - distance + 40) % 40;

        move(player, endPos, false);
    }
    // Ryk en spiller i fængsel han indkasserer IKKE noget for at passere start
    public static void sendToJail(Player player){
        player.setJailed(true);
        move(player, 10, false);
    }
    // Spilleren rykker frem til nærmeste redderi og betaler dobbelt leje (chancekort)
    public static void moveToNearestShipping(Player player) {
        int startPos=map.get(player);
        int endPos = (((startPos+5)/10)*10+5)%40;
        map.put(player, endPos);

        if (endPos < startPos){
            player.receive(4000);
        }

        boundary.movePlayer(player, startPos, endPos);

        ((Shipping) fields[endPos]).landOnFieldDoubleRent(player);
    }


    // Opdater GUI ud fra gamestate
    public static void updateGUI(){
        boundary.updatePlayers(players, map);
        boundary.updateOwnerships(fields);
        boundary.updateBuildings(fields);
        boundary.updateBalances(players);
    }
}
