public class Card {
    public enum Suit{
        HEART,
        DIAMOND,
        CLUB,
        SPADE
    }

    public Suit suit;
    public int rank;
    public int chips;

    public Card(int rank, Suit suit){
        this.rank = rank;
        this.suit = suit;

        if(rank == 1) chips = 11;
        else if(rank > 10) chips = 10;
        else chips = rank;
    }

    @Override
    public String toString() {
        return String.format("[%c/%d]", this.suit.toString().charAt(0), this.rank);
    }
}

