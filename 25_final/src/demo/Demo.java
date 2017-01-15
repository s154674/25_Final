package demo;

import boundary.PlayerBoundary;
import controller.GameController;
import controller.GameState;
import controller.PlayerDecisions;
import desktop_codebehind.Car;
import entity.*;

import java.awt.*;

// En demo hvor 2 spillere går bankerot i anden runde
public class Demo {
    public static void main(String[] args){
        // Setup til demoen
        Player[] players = new Player[]{
                new Player("Emil", new Account(30000), new HoldingCompany(), new PlayerDecisions(new PlayerBoundary())),
                new Player("Frederik", new Account(30000), new HoldingCompany(), new PlayerDecisions(new PlayerBoundary())),
                new Player("Johan", new Account(30000), new HoldingCompany(), new PlayerDecisions(new PlayerBoundary()))
        };
        Car[] cars = new Car[]{
                new Car.Builder().typeRacecar().primaryColor(Color.BLUE).secondaryColor(Color.orange).patternHorizontalLine().build(),
                new Car.Builder().typeCar().primaryColor(Color.LIGHT_GRAY).secondaryColor(Color.DARK_GRAY).patternCheckered().build(),
                new Car.Builder().typeCar().primaryColor(Color.LIGHT_GRAY).secondaryColor(Color.DARK_GRAY).patternCheckered().build()
        };
        Cup cup = new CheatCup(new int[]{0,1,4,4,2,1,5,6,2,4,1,4,5}, new int[]{0,1,4,5,2,2,5,4,2,6,2,4,6});
        DeckShuffler shuffler = new CheatShuffler(new int[]{26,24,10,27,11,25,1,2,3,4,5,6,7,8,9,12,13,14,15,16,17,18,19,20,21,22,23,28,29,30,31,32,33,34});

        GameState.usePlayers(players);
        GameState.useCars(cars);
        GameState.useCup(cup);
        GameState.useShuffler(shuffler);

        GameState.ready();
        GameController gc = new GameController();
        gc.playOneGame();
    }
}
