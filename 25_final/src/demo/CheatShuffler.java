package demo;

import entity.DeckShuffler;

// Snydeblander! bruges til lægge chancekort i en ønsket rækkefølge, til test/demo
public class CheatShuffler extends DeckShuffler {
    private int[] shuffledDeck;
    public CheatShuffler(int[] deck){
        shuffledDeck = deck;
    }

    @Override
    public int[] shuffle(int[] deck){
        return shuffledDeck;
    }
}
