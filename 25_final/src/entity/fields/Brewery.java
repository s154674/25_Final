package entity.fields;

import controller.GameState;


public class Brewery extends Ownable {

    public Brewery(String fieldName, int price){
        super(fieldName, price);
    }

    // Besvare hvad lejen er for at lande på et bryggeri
    @Override
    protected int getRent() {
        return GameState.cup.getSum()*owner.countPropertiesOfType(this.getClass())*100;
    }
}
