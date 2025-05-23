package shared.gameObjects.jokers;

import shared.gameObjects.jokers.interfaces.Independent;
import shared.Main;
import shared.Hand;

public class DrollJoker extends Joker implements Independent {

    public DrollJoker(){
        name = "Droll Joker";
        id = 10;
        description = "+10 Mult if played hand contains a Flush";
        rarity = Rarity.COMMON;
        price = 4;
    }

    @Override
    public void independent() {
        if(Hand.flush(Main.getScored())) Main.addMult(10);
    }
}
