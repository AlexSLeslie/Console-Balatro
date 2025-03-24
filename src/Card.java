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
    public double mult;
    public boolean multX; // indicates if mult is added or multiplied

    public Card(int rank, Suit suit){
        this.rank = rank;
        this.suit = suit;
        this.mult = 0;
        this.multX = false;

        if(rank == 1) chips = 11;
        else chips = Math.min(rank, 10);

    }

    @Override
    public String toString() {
        return String.format("[%c/%d]", this.suit.toString().charAt(0), this.rank);
    }
}

