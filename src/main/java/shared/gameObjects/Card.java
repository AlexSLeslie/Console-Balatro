package shared.gameObjects;

public class Card extends GameObject {

    public enum Suit{
        HEART,
        DIAMOND,
        CLUB,
        SPADE,
        WILD
    }

    public enum Enhance{
        NONE,
        BONUS,
        MULT,
        WILD,
        GLASS,
        STEEL,
        STONE,
        GOLD,
        LUCKY
    }


    private Suit suit;
    private Enhance enhance;

    private int chips;
    private int rank;


    public Card(int rank, Suit suit){
        this.rank = rank;
        this.suit = suit;
        this.price = 1;
        this.enhance = Enhance.NONE;

        chips = rank == 1? 11: Math.min(rank, 10);

    }



    @Override
    public String toString() {
        return String.format("[%c/%d]", this.suit.toString().charAt(0), this.rank);
    }

    public Suit getSuit() { return enhance == Enhance.WILD? Suit.WILD : suit; }
    public void setSuit(Suit suit) { this.suit = suit; }

    public Enhance getEnhance() { return enhance; }
    public void setEnhance(Enhance enhance) { this.enhance = enhance; }

    public int getChips() {
        return switch(enhance){
            case BONUS -> chips + 30;
            case STONE -> 50; // Cannot currently be increased
            default -> chips;
        };
    }
    public void setChips(int chips) { this.chips = chips; }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }


    @Override
    public int getPrice() { return 0; }
}

