package entity.fields;

import controller.GameHelper;
import entity.Player;


public class Jailer extends Field {
    public Jailer() {
        super("fængsel");
    }

    // Når spilleren lander på dette felt skal han rykkes til fængslet
    @Override
    public void landOnField(Player lander) {
        GameHelper.sendToJail(lander);
    }
}
