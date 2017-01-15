package entity;

// Bruges til at blande kortdecket med prøv-lykken kort
public class FisherYatesShuffler extends DeckShuffler {
    @Override
    public int[] shuffle(int[] deck) {
        int n = deck.length;
        for (int i = 0; i < deck.length; i++) {
            // Get a random index of the array past i.
            int random = i + (int) (Math.random() * (n - i));
            // Swap the random element with the present element.
            int randomElement = deck[random];
            deck[random] = deck[i];
            deck[i] = randomElement;
        }
        return deck;
    }
}
