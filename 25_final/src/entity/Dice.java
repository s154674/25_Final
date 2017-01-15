package entity;


public class Dice {
    private int value = 0;

    // Ruller terningen.
    public int roll() {
        double d1 = Math.random(); // 0-1
        double d2 = d1 * 6; // 0-6
        int d3 = (int) (d2); // 0-5 integer
        value = d3 + 1;
        return value;
    }

    // Returnerer facevalue.
    public int getValue() {
        return value;
    }
}
