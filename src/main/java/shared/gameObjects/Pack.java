package shared.gameObjects;

import java.util.HashMap;

public class Pack extends GameObject{
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

    private static final double[] weights = {4, 1.2, 0.6};

    private Type type;
    private Size size;


    public Pack(Type type, Size size){
        this.type = type;
        this.size = size;

        initPrice();
        initName();

    }


    private void initPrice(){
        price = switch(size){
            case NORMAL -> 4;
            case JUMBO -> 6;
            case MEGA -> 8;
        };
    }

    private void initName(){
        name = size == Size.NORMAL? "" : capitalizeFirst(size.name()) + " ";
        name += capitalizeFirst(type.name());
        name += " Pack";
    }



    public Type getType() {
        return type;
    }

    public Size getSize() {
        return size;
    }


    @Override
    public int getSellPrice(){
        System.out.println("Error: Attempting to sell a pack");
        return -1;
    }

    // For use by DiscreteProbabilityCollectionSampler
    public static HashMap<Type, Double> getWeightMap(){
        return new HashMap<Type, Double>(){{
            put(Type.STANDARD, weights[0]);
            put(Type.ARCANA, weights[0]);
            put(Type.CELESTIAL, weights[0]);
            put(Type.BUFFOON, weights[1]);
            put(Type.SPECTRAL, weights[2]);
        }};
    }



}
