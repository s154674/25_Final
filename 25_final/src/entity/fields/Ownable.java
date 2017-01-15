package entity.fields;

import entity.HoldingCompany;
import entity.Player;


public abstract class Ownable extends Field {
    protected HoldingCompany owner = null;
    private int price;

    public Ownable(String fieldName, int price) {
        super(fieldName);
        this.price = price;
    }

    // get, set
    public HoldingCompany getOwner() {
        return owner;
    }
    public void setOwner(HoldingCompany buyer) {
        owner = buyer;
    }

    // Procedure for hvad der sker når en spiller lander på en ejendom.
    @Override
    public void landOnField(Player lander) {
        if (owner == null){
            // Ejendommen ejes ikke - det kan købes
            if (lander.canAffordAfterSellings(price)){
                if (!lander.canAfford(price) && lander.decide().wantsToSellToAfford(this))
                    lander.decide().sellToAfford(this);
                if (lander.canAfford(price) && lander.decide().wantsToBuy(this))
                    buy(lander);
            }
        } else if (owner.getOwner() == lander){
            // Landet på egen Ejendom
            lander.decide().landedOn(this);

        } else {
            // Landet på en anden spillers ejendom
            lander.decide().landedOnAndPayedPlayer(this, getRent(), owner.getOwner());
            lander.pay(getRent(), owner.getOwner());
        }
    }

    // Hvad skal der ske når man køber en ejendom?
    protected void buy(Player player) {
        player.pay(price);
        player.getHoldingCompany().addProperty(this);
    }
    // Hvad skal der ske når man sælger en ejendom?
    public void sell() {
        owner.receive(value());
        owner.removeProperty(this);
        owner = null;
    }
    // Værdien af denne her ejendom
    public int value(){
        return price;
    }
    public boolean isTradable(){
        return true;
    }

    // Alle subklasser skal implementere getRent().
    protected abstract int getRent();

    // Getter price
    public int getPrice() {
        return price;
    }
}
