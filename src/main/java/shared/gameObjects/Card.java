package shared.gameObjects;

import java.util.Locale;

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

        this.chips = rank == 1? 11: Math.min(rank, 10);

    }

    @Override
    public String toString() {
        return String.format("%s[%c/%d]", getEnhanceShort(), this.suit.toString().charAt(0), this.rank);
    }

    private String getEnhanceShort(){
        return switch(enhance){
          case STEEL -> "SL";
          case STONE -> "SN";
          case NONE -> "";
          default -> "" + enhance.toString().charAt(0);
        };
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

    @Override
    public String getName(){ return ""; }

    @Override
    public String getDescription(){
        String out = "";

        if(enhance != Enhance.NONE)
            out += capitalize(suit.name()) + " ";

        out += switch(rank){
            case 1 -> "Ace";
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            default -> "" + rank;
        };

        return out + " of " + capitalize(suit.name()) + "s";
    }

    private String capitalize(String s){
        if(s.length() < 2) return s.toUpperCase();
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }

}

