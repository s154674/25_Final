package entity.fields;

import controller.ChanceCardController;
import entity.Player;


public class Chance extends Field {
    private ChanceCardController cardController;
    public Chance(ChanceCardController cardController) {
        super("Prøv lykken");
        this.cardController=cardController;
    }

    // Hvad sker der når man lander på et chance-felt(prøv lykken)
    @Override
    public void landOnField(Player lander) {
        cardController.drawCard(lander);
    }
}
