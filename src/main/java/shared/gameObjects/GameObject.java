package shared.gameObjects;

public abstract class GameObject {

    protected int price;
    protected String name;

    public int getPrice(){ return price; }
    public void setPrice(int price){ this.price = price; }

    public String getName(){ return name; }
}
