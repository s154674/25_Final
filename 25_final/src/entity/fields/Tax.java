package entity.fields;

import entity.Player;

public class Tax extends Field {
    private int amount;
    private int percentage=0;
    // Constructor til når der skal betales et fast beløb
    public Tax(String name, int amount) {
        super(name);
        this.amount = amount;
    }
    // Constructor til når der også må betales procent
    public Tax(String name, int amount, int percentage) {
        super(name);
        this.amount = amount;
        this.percentage = percentage;
    }

    @Override
    public void landOnField(Player lander) {
        boolean payPercentage = false;
        int money;
        if (percentage!=0){
            payPercentage = lander.decide().payPercentage(amount, percentage);
        }

        if (payPercentage){
            money = percentage*(lander.fortune()/100);
        } else {
            money = amount;
        }
        lander.decide().landedOnAndPayed(this, money);
        lander.pay(money);
    }
}
