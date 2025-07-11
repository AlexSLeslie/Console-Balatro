package shared.gameObjects;

public class Pack{
    public enum Type{
        STANDARD,
        ARCANA,
        CELESTIAL,
        BUFFOON,
        SPECTRAL
    }
    public enum Size{
        NORMAL,
        JUMBO,
        MEGA
    }

    private Type type;
    private Size size;

    public double rate;
    public int cost;

    public Pack(Type type, Size size){
        this.type = type;
        this.size = size;

        initRate();
        initCost();
    }

    private void initRate(){
        rate = switch(type){
            case STANDARD, ARCANA, CELESTIAL -> 4;
            case BUFFOON -> 1.2;
            case SPECTRAL -> 0.6;
        }
        / switch(size){
            case NORMAL -> 1;
            case JUMBO -> 2;
            case MEGA -> 8;
        };
    }

    private void initCost(){
        cost = switch(size){
            case NORMAL -> 4;
            case JUMBO -> 6;
            case MEGA -> 8;
        };
    }

    public Type getType() {
        return type;
    }

    public Size getSize() {
        return size;
    }

    public double getRate() {
        return rate;
    }

    public int getCost() {
        return cost;
    }
}
