package shared.jokers;

import shared.GameObject;

public abstract class Joker extends GameObject {

    public enum Edition{
        BASE,
        FOIL,
        HOLOGRAPHIC,
        POLYCHROME,
        NEGATIVE
    }

    public enum Rarity{
        COMMON,
        UNCOMMON,
        RARE,
        LEGENDARY
    }

    public enum EffectType{
        CHIPS,
        MULT,
        XMULT,
        CHIPS_MULT,
        EFFECT,
        RETRIGGER,
        ECONOMY
    }

    protected Edition edition;
    protected Rarity rarity;

    protected String name;

    protected int chips = 0;
    protected double mult = 0;

    public Joker(){}

    @Override
    public int getPrice() {
        return price + switch(edition){
            case BASE -> 0;
            case FOIL -> 2;
            case HOLOGRAPHIC -> 3;
            case POLYCHROME, NEGATIVE -> 5;
        };
    }

    public Edition getEdition(){ return edition; }
    public void setEdition(Edition edition){ this.edition = edition; }

    public Rarity getRarity(){ return rarity; }

    public String getName(){ return name; }

    public int getChips(){ return chips; }
    public void setChips(int chips){ this.chips = chips; }

    public double getMult(){ return mult; }


}
