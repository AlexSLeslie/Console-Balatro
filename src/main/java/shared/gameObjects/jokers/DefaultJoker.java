package shared.gameObjects.jokers;

import shared.gameObjects.jokers.interfaces.Independent;
import shared.Main;

public class DefaultJoker extends Joker implements Independent {

    public DefaultJoker(){
        name = "Joker";
        id = 1;
        description = "+4 Mult";
        rarity = Rarity.COMMON;
        price = 2;
    }

    @Override
    public void independent() {
        Main.addMult(4);
    }
}
