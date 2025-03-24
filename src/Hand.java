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
    public int mult;

    public Hand(int chips, int mult){
        this.chips = chips;
        this.mult = mult;
    }


    public static boolean hasRank(ArrayList<Card> cards, int rank){
        return cards.stream().filter(c -> c.rank == 1).findAny().isPresent();
    }

    public static boolean hasRank(ArrayList<Card> cards, int[] ranks){
        for(int i: ranks){
            if(!hasRank(cards, i)) return false;
        }
        return true;
    }

    public static boolean flush(ArrayList<Card> cards){
        for(Card card: cards){
            if(card.suit != cards.get(0).suit) return false;
        }
        return true;
    }

    public static boolean straight(ArrayList<Card> cards){
        Collections.sort(cards, new CardSort());
        if(Hand.hasRank(cards, new int[]{1, 10, 11, 12, 13})) return true;
        for(int i=1; i<cards.size(); ++i){
            if(cards.get(i).rank - cards.get(i-1).rank != 1) return false;
        }
        return true;
    }

    public static boolean ofAKind(ArrayList<Card> cards, int n){
        int[] count = new int[13];
        for(Card c: cards) {
            if(++count[c.rank] >= n) return true;
        }

        return false;
    }
    
    public static boolean fullHouse(ArrayList<Card> cards){
        int[] count = new int[13];
        for(Card c:cards) ++count[c.rank];
        
        boolean three = false, two = false;
        for(int i: count){
            if(i>=3 && !three) three = true;
            else if(i >= 2) two = true;
            if(three && two) return true;
        }
        return false;
    }

    public static boolean twoPair(ArrayList<Card> cards){
        int[] count = new int[13];
        for(Card c: cards) ++count[c.rank];

        boolean found = false;
        for(int i: count){
            if(i >= 2 && found) return true;
            else if(i >= 2) found = true;
        }
        return false;
    }

}
