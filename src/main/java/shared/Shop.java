package shared;

import shared.gameObjects.*;

import java.util.*;

public class Shop {

    private ArrayList<GameObject> cards;
//    ArrayList of vouchers here
//    ArrayList of packs here

    private int rerollPrice;

    public Shop(){
        cards = new ArrayList<>();
        rerollPrice = 5;
    }

    public void addCard(GameObject gameObject){ cards.add(gameObject); }

    public ArrayList<GameObject> getCards(){ return cards; }

    public void clear(){ cards.clear(); }

    public int totalObjects(){ return cards.size(); }

    public int getRerollPrice(){ return rerollPrice; }
    public void setRerollPrice(int price){ rerollPrice = price; }
    public void rerollIncrease(){ ++rerollPrice; }

    // current plan is to have consumables and vouchers be stored in different lists,
    // but will be accessed externally as if part of the same list
    public GameObject getGameObject(int index){ return cards.get(index); }



}
