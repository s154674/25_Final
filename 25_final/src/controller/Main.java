package controller;


public class Main {
    public static void main(String[] args){
        GameState.ready();
        GameController gc = new GameController();
        gc.playOneGame();
    }
}
