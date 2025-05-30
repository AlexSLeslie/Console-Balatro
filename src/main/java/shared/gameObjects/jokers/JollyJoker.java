package shared.gameObjects.jokers;

import shared.gameObjects.jokers.interfaces.Independent;
import shared.Main;
import shared.Hand;

public class JollyJoker extends Joker implements Independent {

    public JollyJoker(){
        name = "Jolly Joker";
        id = 6;
        description = "+8 Mult if played hand contains a Pair";
        rarity = Rarity.COMMON;
        price = 3;
    }

    @Override
    public void independent() {
        if(Hand.ofAKind(Main.getScored(), 2)) Main.addMult(8);
    }
}
