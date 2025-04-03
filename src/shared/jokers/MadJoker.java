package shared.jokers;

import shared.jokers.interfaces.Independent;
import shared.Main;
import shared.Hand;

public class MadJoker extends Joker implements Independent {

    public MadJoker(){
        name = "Mad Joker";
        id = 8;
        description = "+10 Mult if played hand contains a Two Pair";
    }

    @Override
    public void independent() {
        if(Hand.twoPair(Main.getScored())) Main.addMult(10);
    }
}
