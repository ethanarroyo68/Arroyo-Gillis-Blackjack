import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dealer {

    private final Deck deck;
    private final List<Integer> dealerCardIndices = new ArrayList<>(); 
    private final boolean[] drawn = new boolean[52];
    private final Random rand;

    //Face down card for dealer (only used by dealer keep in dealer class)
    private final String faceDownCard =
            ".───────────────.\n" +
            "│               │\n" +
            "│               │\n" +
            "│               │\n" +
            "│               │\n" +
            "│   Face Down   │\n" +
            "│               │\n" +
            "│               │\n" +
            "│               │\n" +
            "│               │\n" +
            "└───────────────┘";

    public Dealer(Deck deck) 
    {
        this.deck = deck;
        this.rand = new Random(System.currentTimeMillis());
    }

    public int dealCard() 
    {
        return drawUniqueCard();
    }

    public void dealInitial() 
    {
        dealerCardIndices.clear();
        dealerCardIndices.add(drawUniqueCard());
        dealerCardIndices.add(drawUniqueCard());
    }

    public void displayDealerWithOneFaceUp() 
    {
        System.out.println("Dealer's Hand:");
        String[] faceDownLines = faceDownCard.split("\n");
        String[] visibleLines = deck.getCard(dealerCardIndices.get(1)).split("\n");

        for (int i = 0; i < faceDownLines.length; i++) {
            System.out.println(faceDownLines[i] + "  " + visibleLines[i]);
        }
    }

    public void reset() {
        dealerCardIndices.clear();
        for (int i = 0; i < drawn.length; i++) {
            drawn[i] = false;
        }
    }
    

    public void displayDealerFullHand() 
    {
        System.out.println("Dealer's Hand:");
        String[] cards = new String[dealerCardIndices.size()];
        
        for (int i = 0; i < dealerCardIndices.size(); i++) 
        {
            cards[i] = deck.getCard(dealerCardIndices.get(i));
        }

        Deck.printCardsSideBySide(cards);

        int total = getDealerTotal();
        if (total > 21) 
        {
            System.out.println("Dealer hand total: " + total + " (BUST)");
        } 
        else 
        {
            System.out.println("Dealer hand total: " + total);
        }
    }

    public void playDealerTurn(int userTotal) 
    {
        while (getDealerTotal() < 17) //see Blackjack rules
        {
            dealerCardIndices.add(drawUniqueCard());
        }
    }

    public int getDealerTotal() 
    {
        int total = 0;
        int aces = 0;

        for (int index : dealerCardIndices) 
        {
            int value = deck.getValue(index);
            if (value == 11) aces++;
            total += value;
        }

        while (total > 21 && aces > 0) 
        {
            total -= 10;
            aces--;
        }
        return total;
    }


    public int drawUniqueCard() 
    {
        int index;

        do 
        {
            
            index = rand.nextInt(52);

        } while (drawn[index]);
        
        drawn[index] = true;
        return index;
    }

    public void clearScreen() 
    {
        System.out.println("\n".repeat(50)); //use newline to update screen (DO NOT USE CLS - BUFFER FlUSH CLEARS NON VISIBLE CARD SECTIONS)
    }

}