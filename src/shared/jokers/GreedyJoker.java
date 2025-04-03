package shared.jokers;

import shared.Card;
import shared.jokers.interfaces.OnScored;
import shared.Main;

public class GreedyJoker extends Joker implements OnScored {

    public GreedyJoker(){
        name = "Greedy Joker";
        id = 2;
        description = "Played cards with Diamond suit give +3 mult when scored";
    }

    @Override
    public void onScored(Card card) {
        if(card.getSuit() == Card.Suit.DIAMOND) Main.addMult(3);
    }
}
