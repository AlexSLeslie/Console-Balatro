package shared.jokers;

import shared.jokers.interfaces.Independent;
import shared.*;

public class DefaultJoker extends Joker implements Independent {

    public DefaultJoker(){
        name = "Joker";
    }

    @Override
    public void independent() {
        // continue from here
    }
}
