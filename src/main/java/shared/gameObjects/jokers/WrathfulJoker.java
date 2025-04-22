package shared.gameObjects.jokers;

import shared.gameObjects.Card;
import shared.Main;
import shared.gameObjects.jokers.interfaces.OnScored;

public class WrathfulJoker extends Joker implements OnScored {

    public WrathfulJoker(){
        name = "Wrathful Joker";
        id = 4;
        description = "Played cards with Spade suit give +3 mult when scored";
        rarity = Rarity.COMMON;
        price = 5;
    }

    @Override
    public void onScored(Card card) {
        if(card.getSuit() == Card.Suit.SPADE) Main.addMult(3);
    }
}
