package shared.gameObjects.jokers;

import shared.gameObjects.Card;
import shared.Main;
import shared.gameObjects.jokers.interfaces.OnScored;

public class GluttonousJoker extends Joker implements OnScored {

    public GluttonousJoker(){
        name = "Gluttonous Joker";
        id = 5;
        description = "Played cards with Club suit give +3 mult when scored";
        rarity = Rarity.COMMON;
        price = 5;
    }
    @Override
    public void onScored(Card card) {
        if(card.getSuit() == Card.Suit.CLUB) Main.addMult(3);
    }
}
