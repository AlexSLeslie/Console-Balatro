abstract class GameObject {

    public enum Type{
        CARD,
        JOKER,
        TAROT,
        PLANET,
        SPECTRAL // Not yet added
    }

    protected int price;

    public abstract int getPrice();
    public void setPrice(int price){ this.price = price; }
}
