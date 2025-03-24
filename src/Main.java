import java.util.*;

public class Main {
    static int handSize = 8;
    static int numHands = 4;
    static int numDiscards = 3;

    static boolean gameLoop = true;

    static ArrayList<Card> deck;
    static ArrayList<Card> hand;
    static ArrayList<Card> played; // the hand the player is playing from their hand (not to be confused with hand objects)
    static ArrayList<Card> discard;

    static HashMap<Hand.Type, Hand> hands;

    static Scanner s;

    public static void main(String[] args) {
        // init and generate starting deck
        deck = new ArrayList<>();
        for(int i=1; i<=13; ++i){
            for(Card.Suit j: Card.Suit.values()) deck.add(new Card(i, j));
        }

        hand = new ArrayList<>();
        played = new ArrayList<>();
        discard = new ArrayList<>();

        initHands();

        s = new Scanner(System.in);

        while(gameLoop){
            blind(300);
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
        for(int i=0; i<handSize; ++i) hand.add(deck.remove(deck.size()-1));

        boolean blindLoop = true;
        int hands = numHands, discards = numDiscards, chips = 0;
        String input = "";

        System.out.printf("Ante: %d\n", ante);
        while(blindLoop){
            for(int i=0; i<handSize; ++i) System.out.printf("%d%s ", i, hand.get(i));

            // Get list of inputted numbers, sort, then move from hand to play
            System.out.printf("\nEnter card numbers separated by spaces");
            int[] ins = new int[5];
            for(int i=0; i<5; ++i){
                if(s.hasNextInt()){
                    ins[i] = s.nextInt();
                    if(ins[i] < 0 || ins[i] >= handSize) System.out.printf("Error reading hand\n");
                }
            }

            Arrays.sort(ins);
            for(int i=ins.length-1; i>=0; --i) played.add(hand.remove(i));

            // TODO: score played hand



            blindLoop = false;
        }
        return false;
    }

    public static HashMap<Hand.Type, Hand> initHands(){
        hands = new HashMap<>();
        hands.put(Hand.Type.HIGH_CARD, new Hand(5, 1));
        hands.put(Hand.Type.PAIR, new Hand(10, 2));
        hands.put(Hand.Type.TWO_PAIR, new Hand(20, 2));
        hands.put(Hand.Type.THREE_OF_A_KIND, new Hand(30, 3));
        hands.put(Hand.Type.STRAIGHT, new Hand(30, 4));
        hands.put(Hand.Type.FLUSH, new Hand(35, 4));
        hands.put(Hand.Type.FULL_HOUSE, new Hand(40, 4));
        hands.put(Hand.Type.FOUR_OF_A_KIND, new Hand(60, 7));
        hands.put(Hand.Type.STRAIGHT_FLUSH, new Hand(100, 8));
        hands.put(Hand.Type.ROYAL_FLUSH, new Hand(100, 8));
        hands.put(Hand.Type.FIVE_OF_A_KIND, new Hand(120, 12));
        hands.put(Hand.Type.FLUSH_HOUSE, new Hand(140, 14));
        hands.put(Hand.Type.FLUSH_FIVE, new Hand(160, 16));
        return hands;
    }

}
