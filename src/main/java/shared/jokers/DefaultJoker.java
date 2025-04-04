package shared.jokers;

import shared.jokers.interfaces.Independent;
import shared.Main;

public class DefaultJoker extends Joker implements Independent {

    public DefaultJoker(){
        name = "Joker";
        id = 1;
        description = "+4 Mult";
        rarity = Rarity.COMMON;
    }

    @Override
    public void independent() {
        Main.addMult(4);
    }
}
