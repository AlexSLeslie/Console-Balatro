package shared.gameObjects;

public abstract class GameObject {

    protected int price;
    protected String name;
    protected String description;

    public int getPrice(){ return price; }
    public int getSellPrice(){ return Math.max(1, getPrice()/2); }

    public void setPrice(int price){ this.price = price; }

    public String getName(){ return name; }

    public String getDescription(){ return description; }

    protected String capitalizeFirst(String s){
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
