package shared.gameObjects.jokers;

import shared.gameObjects.jokers.interfaces.Independent;
import shared.Main;
import shared.Hand;

public class ZanyJoker extends Joker implements Independent {

    public ZanyJoker(){
        name = "Zany Joker";
        id = 7;
        description = "+12 Mult if played hand contains a Three of a Kind";
        rarity = Rarity.COMMON;
        price = 4;
    }

    @Override
    public void independent(){
        if(Hand.ofAKind(Main.getScored(), 3)) Main.addMult(12);
    }
}
