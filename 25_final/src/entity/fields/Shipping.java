package entity.fields;

import entity.Player;


public class Shipping extends Ownable {
    private int[] rents={500,1000,2000,4000};
    public Shipping(String fieldName, int price) {
        super(fieldName, price);
    }

    // En særlig udgave af land on field hvor spilleren betaler dobbelt leje - bruges med et chancekort
    public void landOnFieldDoubleRent(Player lander){
        if (owner == null){
            if (lander.canAffordAfterSellings(getPrice())){
                if (!lander.canAfford(getPrice()) && lander.decide().wantsToSellToAfford(this))
                    lander.decide().sellToAfford(this);
                if (lander.canAfford(getPrice()) && lander.decide().wantsToBuy(this))
                    buy(lander);
            }
        } else if (owner.getOwner() == lander){
            lander.decide().landedOn(this);
            // Landet på eget felt
        } else {
            lander.pay(getRent()*2, owner.getOwner());
        }
    }

    @Override
    protected int getRent(){
        return rents[owner.countPropertiesOfType(this.getClass())-1];
    }

}
