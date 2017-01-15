package entity.fields;

import entity.Player;


public class EmptyField extends Field {
    public EmptyField(String name) {
        super(name);
    }
    // Der sker intet når man lander på et tomt felt (start, besøg fængsel, helle)
    public void landOnField(Player lander){
        lander.decide().landedOn(this);
    }
}
