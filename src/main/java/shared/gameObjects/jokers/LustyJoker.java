package shared.gameObjects.jokers;

import shared.gameObjects.Card;
import shared.Main;
import shared.gameObjects.jokers.interfaces.OnScored;

public class LustyJoker extends Joker implements OnScored {

    public LustyJoker(){
        name = "Lusty Joker";
        id = 3;
        description = "Played cards with Heart suit give +3 mult when scored";
        rarity = Rarity.COMMON;
        price = 5;
    }
    @Override
    public void onScored(Card card) {
        if(card.getSuit() == Card.Suit.HEART) Main.addMult(3);
    }
}
