package entity;


public class Cup {
    private Dice d1;
    private Dice d2;

    public Cup(){}

    public Cup(Dice tern1, Dice tern2) {
        d1 = tern1;
        d2 = tern2;
    }
    // Henter facevalues fra terning.
    public int[] values() {
        return new int[]{ d1.getValue(), d2.getValue() };
    }

    // Ruller terningerne.
    public void rollAll() {
        d1.roll();
        d2.roll();
    }

    // Henter summen af terningernes facevalues.
    public int getSum() {
        return values()[0] + values()[1];
    }

    // Besvarer om terningerne danner et par
	public boolean isPair() {
		return values()[0] == values()[1];
	}
}
