package shared.gameObjects.jokers;

import shared.gameObjects.jokers.interfaces.Independent;
import shared.Main;
import shared.Hand;

public class CrazyJoker extends Joker implements Independent {

    public CrazyJoker(){
        name = "Crazy Joker";
        id = 9;
        description = "+12 Mult if played hand contains a Straight";
        rarity = Rarity.COMMON;
        price = 4;
    }

    @Override
    public void independent() {
        if(Hand.straight(Main.getScored())) Main.addMult(12);
    }
}
