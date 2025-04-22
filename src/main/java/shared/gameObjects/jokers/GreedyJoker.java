package shared.gameObjects.jokers;

import shared.gameObjects.Card;
import shared.gameObjects.jokers.interfaces.OnScored;
import shared.Main;

public class GreedyJoker extends Joker implements OnScored {

    public GreedyJoker(){
        name = "Greedy Joker";
        id = 2;
        description = "Played cards with Diamond suit give +3 mult when scored";
        rarity = Rarity.COMMON;
        price = 5;
    }

    @Override
    public void onScored(Card card) {
        if(card.getSuit() == Card.Suit.DIAMOND) Main.addMult(3);
    }
}
