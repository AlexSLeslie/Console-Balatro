import java.util.*;

public class Hand {
    public enum Type{
        HIGH_CARD,
        PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH,
        ROYAL_FLUSH,
        FIVE_OF_A_KIND,
        FLUSH_HOUSE,
        FLUSH_FIVE
    }
    public int chips;
    public double mult;

    public Hand(int chips, double mult){
        this.chips = chips;
        this.mult = mult;
    }


    public static boolean hasRank(ArrayList<Card> cards, int rank){
        return cards.stream().anyMatch(c -> c.getRank() == rank);
    }

    public static boolean hasRank(ArrayList<Card> cards, int[] ranks){
        for(int i: ranks){
            if(!hasRank(cards, i)) return false;
        }
        return true;
    }

    // Gut feeling this method will cause problems later
    public static boolean flush(ArrayList<Card> cards){
        for(Card card: cards){
            if(card.getSuit() != cards.get(0).getSuit() || card.getEnhance() != Card.Enhance.WILD) return false;
        }
        return true;
    }

    public static boolean straight(ArrayList<Card> cards){
        cards.sort(Main.cardSort);
        if(Hand.hasRank(cards, new int[]{1, 10, 11, 12, 13})) return true;
        for(int i=1; i<cards.size(); ++i){
            if(cards.get(i).getRank() - cards.get(i-1).getRank() != 1) return false;
        }
        return true;
    }

    public static boolean ofAKind(ArrayList<Card> cards, int n){
        int[] count = new int[14];
        for(Card c: cards) {
            if(++count[c.getRank()] >= n) return true;
        }

        return false;
    }

    public static boolean fullHouse(ArrayList<Card> cards){
        int[] count = new int[14];
        for(Card c:cards) ++count[c.getRank()];

        boolean three = false, two = false;
        for(int i: count){
            if(i>=3 && !three) three = true;
            else if(i >= 2) two = true;
            if(three && two) return true;
        }
        return false;
    }

    public static boolean twoPair(ArrayList<Card> cards){
        int[] count = new int[14];
        for(Card c: cards) ++count[c.getRank()];

        boolean found = false;
        for(int i: count){
            if(i >= 2 && found) return true;
            else if(i >= 2) found = true;
        }
        return false;
    }

}
