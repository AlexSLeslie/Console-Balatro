package shared.gameObjects;

public abstract class GameObject {

    protected int price;
    protected String name;
    protected String description;

    public int getPrice(){ return price; }
    public void setPrice(int price){ this.price = price; }

    public String getName(){ return name; }

    public String getDescription(){ return description; }
}
