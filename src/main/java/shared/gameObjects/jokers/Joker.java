package shared.gameObjects.jokers;

import shared.gameObjects.GameObject;

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

    protected String description;

    protected int id;


    public Joker(){
        edition = Edition.BASE;
    }

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


    public int getID(){ return id; }

    public String getDescription(){ return description; }

    @Override
    public String toString(){
        return "[ " + switch(edition){
            case FOIL -> "Foil ";
            case HOLOGRAPHIC -> "Holo ";
            case NEGATIVE -> "Neg ";
            case POLYCHROME -> "Poly ";
            default -> "";
        } + name + " ]";


    }


}
