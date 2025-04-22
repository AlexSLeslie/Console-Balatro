package shared;

import shared.gameObjects.*;

import java.util.*;

public class Shop {
    private ArrayList<GameObject> cards;
//    ArrayList of vouchers here
//    ArrayList of packs here

    private int rerollPrice;

    public Shop(){
        rerollPrice = 5;
    }

    private void fill(){

    }

    public ArrayList<GameObject> getCards(){ return cards; }

    public int getRerollPrice(){ return rerollPrice; }
    public void setRerollPrice(int price){ rerollPrice = price; }



}
