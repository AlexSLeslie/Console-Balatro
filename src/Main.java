import java.util.*;

/** TODO:
 *      - blind() bugtesting
 *      - what to do after won/lost round
 */
public class Main {
    static int handSize = 8;
    static int numHands = 4;
    static int numDiscards = 3;

    static boolean gameLoop = true;

    static ArrayList<Card> deck;
    static ArrayList<Card> hand;
    static ArrayList<Card> played; // the hand the player is playing from their hand (not to be confused with hand objects)
    static ArrayList<Card> discard;

    static HashMap<Hand.Type, Hand> handMap;

    static Scanner scanner;

    static CardSort cardSort;

    public static void main(String[] args) {
        // init and generate starting deck
        deck = new ArrayList<>();
        for(int i=1; i<=13; ++i){
            for(Card.Suit j: Card.Suit.values()) deck.add(new Card(i, j));
        }

        hand = new ArrayList<>();
        played = new ArrayList<>();
        discard = new ArrayList<>();

        cardSort = new CardSort();

        initHands();

        scanner = new Scanner(System.in);

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


        boolean blindLoop = true, discarding = false;
        int hands = numHands, discards = numDiscards, chips = 0, handChips = 0;
        double mult;

        Hand scoredHand;

        System.out.printf("Ante: %d\n", ante);
        while(blindLoop){
            System.out.printf("Chips: %d\n", chips);
//            for(int i=0; i<handSize; ++i) hand.add(deck.remove(deck.size()-1));
            while(hand.size() < handSize) hand.add(deck.remove(deck.size()-1));
            hand.sort(cardSort);
            for(int i=0; i<handSize; ++i) System.out.printf("%d%s ", i, hand.get(i));

            // Determine if player is playing or discarding cards
            boolean validInput = false;
            while(!validInput){
                System.out.printf("\nHands: %d | Discards: %d\nEnter <p> to play, or <d> to discard: ", hands, discards);
                switch(scanner.nextLine().charAt(0)){
                    case 'p':
                    case 'P':
                        discarding = false;
                        validInput = true;
                        break;
                    case 'd':
                    case 'D':
                        if(discards > 0) {
                            discarding = true;
                            validInput = true;
                        }
                        else System.out.println("No discards remaining");
                        break;
                    default:
                        System.out.println("Invalid input\n");

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
                System.out.println("Error: Invalid input");
                continue;
            }

            hand.removeAll(Collections.singleton(null));

            // Move cards to played list even if discarded, in case checks on discarded cards necessary
            if(discarding){
                --discards;

            }
            else{
                --hands;
                chips = handMap.get(handType(played)).chips;
                mult = handMap.get(handType(played)).mult;

                for(Card c: played){
                    handChips += c.chips;
                    if(c.multX) mult *= c.mult;
                    else mult += c.mult;

                    System.out.printf("%s -> %d/%.2f | ", c, chips, mult);
                }
                chips = (int)(handChips * mult);
                System.out.println();
            }
            discard.addAll(played);
            played.clear();

            if (hands <= 0) blindLoop = false;


        }
        return false;
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

}
