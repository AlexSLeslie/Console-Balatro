package shared;

import org.reflections.Reflections;
import shared.gameObjects.GameObject;
import shared.gameObjects.Card;
//import shared.gameObjects.jokers.Joker;
import shared.gameObjects.jokers.*;

import java.util.*;

/** TODO:
 *      - bug testing
 *          - blind()
 *          - card enhancements
 *      - what to do after won/lost round
 *          - money
 *      - shop()
 *          - vouchers
 *          - packs
 *      - duplicateJoker()
 *      - better method for random with >2 outcomes
 *      - Jokers
 *      - Tarots
 *      - Planets
 *      - Cards are sorted/scored lo-hi, should be opposite
 */
public class Main {

    static int handSize = 8;
    static int maxJokers = 5;
    static int numHands = 4;
    static int numDiscards = 3;
    static int money = 15;
    static int chips;
    static int shopItems = 2;
    static int maxConsumables = 2;

    static double mult;

    static boolean gameLoop = true;

    final static String INVALID_INPUT_MESSAGE = "Error: Invalid input";

    static ArrayList<Card> deck;

    static ArrayList<Card> hand;
    static ArrayList<Card> played; // the hand the player is playing from their hand (not to be confused with hand objects)
    static ArrayList<Card> discard;
    static ArrayList<Card> scored;

    static ArrayList<Joker> jokers;
    static ArrayList<GameObject> consumables;
    static Set<Class<? extends Joker>> allJokers;
    static HashMap<Joker.Rarity, ArrayList<Class<? extends Joker>>> jokersByRarity;

    static HashMap<Hand.Type, Hand> handMap;

    static Scanner scanner;

    static CardSort cardSort;

    static Random random;

    static Reflections reflections;

    public static void main(String[] args) {

        random = new Random();

        // init and generate starting deck
        deck = new ArrayList<>();
        for(int i=1; i<=13; ++i){
            for(Card.Suit j: Card.Suit.values()) deck.add(new Card(i, j));
        }

        cardSort = new CardSort();
        initHands();

        reflections = new Reflections("shared.gameObjects.jokers");

        consumables = new ArrayList<>();
        jokers = new ArrayList<>();
        initAllJokers();

        scanner = new Scanner(System.in);



        while(gameLoop){
            shop();
//            blind(100);
//            blind(150);
            gameLoop = false;
        }

    }

    public static Hand.Type handType(ArrayList<Card> played){
        // check for all hand types
        if(played.size() == 5){
            if(Hand.flush(played) && Hand.ofAKind(played, 5)) return Hand.Type.FLUSH_FIVE;
            if(Hand.flush(played) && Hand.fullHouse(played)) return Hand.Type.FLUSH_HOUSE;
            if(Hand.hasRank(played, new int[]{1, 10, 11, 12, 13}) && Hand.flush(played)) return Hand.Type.ROYAL_FLUSH;
            if(Hand.straight(played) && Hand.flush(played)) return Hand.Type.STRAIGHT_FLUSH;
            if(Hand.ofAKind(played, 4)) return Hand.Type.FOUR_OF_A_KIND;
            if(Hand.fullHouse(played)) return Hand.Type.FULL_HOUSE;
            if(Hand.flush(played)) return Hand.Type.FLUSH;
            if(Hand.straight(played)) return Hand.Type.STRAIGHT;
        }
        if(played.size() >= 4){
            if(Hand.ofAKind(played, 4)) return Hand.Type.FOUR_OF_A_KIND;
            if(Hand.ofAKind(played, 3)) return Hand.Type.THREE_OF_A_KIND;
            if(Hand.twoPair(played)) return Hand.Type.TWO_PAIR;
        }
        if(played.size() >= 3 && Hand.ofAKind(played, 3)) return Hand.Type.THREE_OF_A_KIND;
        if(played.size() >= 2 && Hand.ofAKind(played, 2)) return Hand.Type.PAIR;
        return Hand.Type.HIGH_CARD;
    }

    // returns if true player won blind
    public static boolean blind(int ante){
        Collections.shuffle(deck);


        boolean blindLoop = true, discarding = false;
        int hands = numHands, discards = numDiscards;//, chips = 0;
        //double mult;

        Hand.Type scoredHandType;

        hand = new ArrayList<>();
        played = new ArrayList<>(); // the hand the player is playing from their hand (not to be confused with hand objects)
        discard = new ArrayList<>();
        scored = new ArrayList<>();

        System.out.printf("Ante: %d\n", ante);
        while(blindLoop){
            System.out.printf("Chips: %d\n", chips);

            while(hand.size() < handSize) hand.add(deck.remove(deck.size()-1));
            hand.sort(cardSort);
            for(int i=0; i<handSize; ++i) System.out.printf("%d%s ", i, hand.get(i));

            // Determine if player is playing or discarding cards
            boolean validInput = false;
            while(!validInput){
                System.out.printf("\nHands: %d | Discards: %d\nEnter <p> to play, or <d> to discard: ", hands, discards);
                switch(firstCharInScanner()){
                    case 'p':
                        discarding = false;
                        validInput = true;
                        break;
                    case 'd':
                        if(discards > 0) {
                            discarding = true;
                            validInput = true;
                        }
                        else System.out.println("No discards remaining");
                        break;
                    default:
                        System.out.println(INVALID_INPUT_MESSAGE+"\n");

                }
            }

            // Get list of inputted numbers, sort, then move from hand to play
            System.out.print("\nEnter card numbers separated by spaces: ");
            int x;
            for(String s: scanner.nextLine().split(" ")){
                try{
                    if(played.size() >= 5) break;
                    x = Integer.parseInt(s);
                    if(x < 0 || handSize <= x || hand.get(Integer.parseInt(s)) == null) {
                        validInput = false;
                        break;
                    }
                    played.add(hand.get(x));
                    hand.set(x, null);
                } catch(Exception e){
                    validInput = false;
                    break;
                }
            }
            if(!validInput){
                System.out.println(INVALID_INPUT_MESSAGE);
                continue;
            }

            hand.removeAll(Collections.singleton(null));

            // Move cards to played list even if discarded, in case checks on discarded cards necessary
            if(discarding){
                --discards;

            }
            else{
                --hands;
                scoredHandType = handType(played);

                // Ensure cards not necessary for score are not counted
                scored.addAll(played);
                for(Card c: played){
                    scored.remove(c);
                    if(scoredHandType != handType(scored)) scored.add(c);
                }

                if(scored.size() == 0) scored.add(played.get(played.size()-1));

                System.out.printf("shared.Hand: %s | Base Chips: %d | Base Mult: %.0f\n",
                        scoredHandType, handMap.get(scoredHandType).chips, handMap.get(scoredHandType).mult);
                chips = handMap.get(scoredHandType).chips;
                mult = handMap.get(scoredHandType).mult;

                for(Card c: scored){
                    chips += c.getChips();
                    mult = switch(c.getEnhance()){
                        case MULT -> mult + 4;
                        case GLASS -> mult * 2;
                        case LUCKY -> mult + (oneIn(5)? 20 : 0);
                        default -> mult;
                    };

                    if(c.getEnhance() == Card.Enhance.LUCKY && oneIn(15)) money += 20;

                    System.out.printf("%s -> %d/%.2f | ", c, chips, mult);
                }

                for(Card c: hand){
                    if(c.getEnhance() == Card.Enhance.STEEL) mult *= 1.5;
                    // Other effects will use cards left in hand
                }

                chips = (int)(chips * mult);
                System.out.println();
            }

            System.out.println();

            for(Card c: played){
                if(c.getEnhance() == Card.Enhance.GLASS && oneIn(4)) destroy(c);
                else discard.add(c);
            }

            played.clear();
            scored.clear();


            if (hands <= 0 || chips >= ante) blindLoop = false;


        }
        System.out.printf("Chips: %d\n", chips);
        if(chips >= ante){

            for(Card c: hand){
                if(c.getEnhance() == Card.Enhance.GOLD) money += 3;
            }
            deck.addAll(discard);
            return true;
        }
        return false;
    }

    public static void shop(){
        Shop shop = new Shop();
        fillShop(shop);
        // will have ArrayLists of vouchers and packs later

        boolean shopLoop = true;

        while(shopLoop){
            displayShop(shop);

            // Add packs, vouchers later
            boolean validInput = false;
            char actionType = '\0';
            while (!validInput) {
                System.out.print("Enter selection: Purchase <p> | Inspect <i> | Sell <s> | Reroll <r> | Quit shop <q>: ");

                actionType = firstCharInScanner();
                validInput = charIn(actionType, "pisrq");
                printErrorIfInvalid(validInput);
            }

            if(actionType == 'q') return;
            if(actionType == 'r'){
                if(money >= shop.getRerollPrice()){
                    fillShop(shop);
                    money -= shop.getRerollPrice();
                    shop.rerollIncrease();
                }
                else System.out.println("Insufficient funds for reroll");
                continue;
            }
            if(actionType == 'p' && jokers.size() + consumables.size() == maxJokers + maxConsumables){
                System.out.println("No room for new purchases!");
                continue;
            }

            if(actionType == 's' && jokers.size() + consumables.size() == 0){
                System.out.println("Nothing to sell!");
                continue;
            }

            validInput = false;
            int shopIndex = -1;

            while(!validInput){
                System.out.print("Enter a number corresponding to a card: ");
                if(!scanner.hasNextInt()) {
                    System.out.println(INVALID_INPUT_MESSAGE);
                    continue;
                }

                shopIndex = scanner.nextInt();
                validInput = validateShopInput(shop, shopIndex, actionType);

                // TODO: unique error message when trying to buy own card or sell shop card
                printErrorIfInvalid(validInput);

            }

            if(shopIndex == -1) throw new IndexOutOfBoundsException("My loop didn't work ;-;");

            switch(actionType){
                case 'p':
                    purchaseShopItem(shop, shopIndex);
                    break;
                case 's':
                    //sellShopItem(shop, shopIndex);
                    break;
            }

            System.out.println();
        }
    }

    public static void printErrorIfInvalid(boolean input){
        if(!input)
            System.out.println(INVALID_INPUT_MESSAGE);
    }

    public static void sellShopItem(Shop shop, int shopIndex){
        return;
    }

    public static void purchaseShopItem(Shop shop, int shopIndex){
        GameObject gameObject = getGameObjectFromShopIndex(shop, shopIndex);

        if(money < gameObject.getPrice()) {
            System.out.println("Insufficient funds!");
            return;
        }

        // TODO: continue here, also implement sellShopItem()

    }

    public static boolean validateShopInput(Shop shop, int input, char actionType){
        return switch(actionType){
            case 'p' -> inRange(input, shop.totalObjects());
            case 's' -> input > totalShopOptions(shop);
            default -> inRange(input, shop.totalObjects() + jokers.size());
        };
    }


    public static GameObject getGameObjectFromShopIndex(Shop shop, int shopIndex){
        if(!inRange(shopIndex, totalShopOptions(shop)))
            throw new IndexOutOfBoundsException();


        else if(shopIndex < shop.totalObjects())
            return shop.getGameObject(shopIndex);

        else if(shopIndex < shop.totalObjects() + jokers.size())
            return jokers.get(shopIndex - shop.totalObjects());

        else return null;
    }

    // Will eventually be shop items + vouchers + player owned jokers & consumables
    public static int totalShopOptions(Shop shop){
        return shop.totalObjects() + jokers.size();
    }

    // min <= value < max
    public static boolean inRange(int value, int min, int max){
        return min <= value && value < max;
    }

    // 0 <= value < max
    public static boolean inRange(int value, int max){
        return inRange(value, 0, max);
    }

    public static char firstCharInScanner(){
        return scanner.nextLine().toLowerCase().charAt(0);
    }

    public static boolean charIn(char c, String s){
        return s.indexOf(c) >= 0;
    }

    public static void displayShop(Shop shop){
        System.out.printf("=-=-= SHOP =-=-=\n$%d\nCards: ", money);

        int i = 0;
        for (GameObject gameObject : shop.getCards())
            printShopItem(i++, gameObject);

        System.out.printf("Reroll: $%d\n\n", shop.getRerollPrice());
        if(jokers.size() > 0) {
            System.out.print("Your Jokers: ");
            for (Joker joker : jokers)
                printShopItem(i++, joker);

            System.out.print("\n\n");
        }

    }

    public static void printShopItem(int index, GameObject gameObject){
        System.out.printf("%d - $%d %s | ", index, gameObject.getPrice(), gameObject);
    }

    public static void fillShop(Shop shop){
        shop.clear();
        fillShopCards(shop);
    }

    public static void fillShopCards(Shop shop){
        for(int i=0; i<shopItems; ++i){
            int shopItemType = random.nextInt(28);
            if(shopItemType < 4) shop.addCard(randomJoker()); // change to tarot when added
            else if(shopItemType < 8) shop.addCard(randomJoker());
            else shop.addCard(randomJoker());
        }
    }

    public static Joker randomJoker(){
        int r = random.nextInt(100);

        if(r < 70) return randomJoker(Joker.Rarity.COMMON);
        else if(r < 95) return randomJoker(Joker.Rarity.UNCOMMON);
        else return randomJoker(Joker.Rarity.RARE);
    }

    public static Joker randomJoker(Joker.Rarity rarity){
        // gets the list of joker subclasses specified by rarity, then inits a new joker from a random selection
        // possible issue in late stages of game where unable to find joker in rarity unowned by player

        // uncommon/rare jokers not yet implemented
        if(jokersByRarity.get(Joker.Rarity.RARE).size() == 0) rarity = Joker.Rarity.COMMON;
        try {
            ArrayList<Class<? extends Joker>> givenRarityJokers = jokersByRarity.get(rarity);
            return newJoker(givenRarityJokers.get(random.nextInt(givenRarityJokers.size())));
        } catch(Exception e){
            e.printStackTrace();
            System.out.println(rarity + " " + jokersByRarity.get(rarity).size());
            return null;
        }
    }

    public static void initHands(){
        handMap = new HashMap<>();
        handMap.put(Hand.Type.HIGH_CARD, new Hand(5, 1));
        handMap.put(Hand.Type.PAIR, new Hand(10, 2));
        handMap.put(Hand.Type.TWO_PAIR, new Hand(20, 2));
        handMap.put(Hand.Type.THREE_OF_A_KIND, new Hand(30, 3));
        handMap.put(Hand.Type.STRAIGHT, new Hand(30, 4));
        handMap.put(Hand.Type.FLUSH, new Hand(35, 4));
        handMap.put(Hand.Type.FULL_HOUSE, new Hand(40, 4));
        handMap.put(Hand.Type.FOUR_OF_A_KIND, new Hand(60, 7));
        handMap.put(Hand.Type.STRAIGHT_FLUSH, new Hand(100, 8));
        handMap.put(Hand.Type.ROYAL_FLUSH, new Hand(100, 8));
        handMap.put(Hand.Type.FIVE_OF_A_KIND, new Hand(120, 12));
        handMap.put(Hand.Type.FLUSH_HOUSE, new Hand(140, 14));
        handMap.put(Hand.Type.FLUSH_FIVE, new Hand(160, 16));
    }

    public static void initAllJokers(){
        jokersByRarity = new HashMap<>();
        for(Joker.Rarity rarity: Joker.Rarity.values()) jokersByRarity.put(rarity, new ArrayList<>());

        allJokers = reflections.getSubTypesOf(Joker.class);
        for(Class<? extends Joker> c: allJokers){
            try {
                jokersByRarity.get(newJoker(c).getRarity()).add(c);
            }catch (Exception e) { System.out.println(e); }
        }

    }

    public static void printJokersByRarity(){
        for(Map.Entry<Joker.Rarity, ArrayList<Class<? extends Joker>>> entry: jokersByRarity.entrySet()){
            for(Class<? extends Joker> j: entry.getValue()){
                Joker joker = newJoker(j);
                System.out.printf("%s (%s), ", joker.getName(), (joker).getRarity().name());
            }
        }
    }

    public static Joker newJoker(Class<? extends Joker> c){
        try {
            Joker joker = c.getDeclaredConstructor().newInstance();

            int edition = random.nextInt(1000);
            // Odds can be increased with vouchers, not yet added
            if(edition < 3) joker.setEdition(Joker.Edition.NEGATIVE);
            else if(edition < 3 + 3) joker.setEdition(Joker.Edition.POLYCHROME);
            else if(edition < 3 + 3 + 14) joker.setEdition(Joker.Edition.HOLOGRAPHIC);
            else if(edition < 3 + 3 + 14 + 20) joker.setEdition(Joker.Edition.FOIL);

            return joker;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Wrapper for Random.nextInt()
    public static boolean oneIn(int value){ return random.nextInt(value) == 0; }

    // Not necessary now, may be for later compatibility
    public static void destroy(GameObject gameObject){
        System.out.printf("%s was destroyed!", gameObject);
    }

    public static int getChips(){ return chips; }
    public static void setChips(int chips){ Main.chips = chips; }

    public static double getMult(){ return mult; }
    public static void setMult(double mult){ Main.mult = mult; }
    public static void addMult(double mult){ Main.mult += mult; }
    public static void timesMult(double Mult){ Main.mult *= mult; }

    public static int getMoney(){ return money; }
    public static void setMoney(int money){ Main.money = money; }

    public static ArrayList<Card> getHand(){ return hand; }

    public static ArrayList<Card> getScored(){ return scored; }

}
