package shared.gameObjects.jokers;

import shared.gameObjects.jokers.interfaces.Independent;
import shared.Main;
import shared.Hand;

public class MadJoker extends Joker implements Independent {

    public MadJoker(){
        name = "Mad Joker";
        id = 8;
        description = "+10 Mult if played hand contains a Two Pair";
        rarity = Rarity.COMMON;
        price = 4;
    }

    @Override
    public void independent() {
        if(Hand.twoPair(Main.getScored())) Main.addMult(10);
    }
}
