package entity.fields;

import entity.Player;


public abstract class Field {
    private String name;

    public Field(String fieldName) {
        this.name = fieldName;
    }
    // landOnField skal implementeres i subklasser
    public abstract void landOnField(Player lander);
    // Bruges til at udskrive felter
    public String toString() {
        return this.name;
    }
    public String getName(){
        return this.name;
    }
}
