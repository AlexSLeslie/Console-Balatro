import java.util.Comparator;

public class CardSort implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        if (o1.rank == o2.rank)
            return o1.suit.compareTo(o2.suit);

        return Integer.compare(o1.rank, o2.rank);
    }
}
