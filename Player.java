import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Supplier;

public class Player {

    private final ArrayList<String> hand = new ArrayList<>();
    private final ArrayList<Integer> values = new ArrayList<>();
    private int totalValue = 0;

    public void addCard(String cardStr, int cardVal)
    {
        Scanner scanner = new Scanner(System.in);

        if (cardVal == 11)
        {
            System.out.println("You drew an Ace. Do you want it to count as 1 or 11?\n");
            String val = scanner.nextLine();
            cardVal = Integer.parseInt(val);
            while (cardVal != 1 && cardVal != 11)
            {
                System.out.println("You must enter either 1 or 11:\n");
                val = scanner.nextLine();
                cardVal = Integer.parseInt(val);
            }
        }

        hand.add(cardStr);
        values.add(cardVal);
        totalValue += cardVal;
    }

    public void addCard(String cardStr, int cardVal, Supplier<String> aceChoiceProvider) {
        if (cardVal == 11) {
            int chosenVal = -1;
            while (chosenVal != 1 && chosenVal != 11) {
                System.out.println("You drew an Ace. Do you want it to count as 1 or 11?");
                try {
                    chosenVal = Integer.parseInt(aceChoiceProvider.get().trim());
                    if (chosenVal != 1 && chosenVal != 11) {
                        System.out.println("Invalid input. Please enter 1 or 11.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }
            cardVal = chosenVal;
        }
    
        hand.add(cardStr);
        values.add(cardVal);
        totalValue += cardVal;
    }    

    public int getTotalValue() 
    {
        return totalValue;
    }

    public boolean isBusted() 
    {
        return totalValue > 21;
    }

    public void reset() {
        hand.clear();
        values.clear();
        totalValue = 0;
    }
    
    public void displayHand() 
    {
        System.out.println("Your Hand:");
        String[] cardArray = hand.toArray(new String[0]);
        Deck.printCardsSideBySide(cardArray);
        System.out.println("Your total value: " + totalValue + "\n");
    }

    public void takeTurn(Dealer dealer, Deck deck, Supplier<String> inputProvider) {
        while (true) {
            System.out.print("Do you want to (H)it or (S)tay? ");
            String input = inputProvider.get().trim().toUpperCase();

            if (input.equals("H")) {
                int newCard = dealer.dealCard();
                addCard(deck.getCard(newCard), deck.getValue(newCard));
                dealer.clearScreen();
                dealer.displayDealerWithOneFaceUp();
                displayHand();

                if (isBusted()) {
                    System.out.println("BUST! You went over 21.");
                    break;
                }

            } else if (input.equals("S")) {
                break;
            } else {
                System.out.println("Invalid input, enter H or S.");
            }
        }
    }

    public void takeTurnWithDoubleDown(Dealer dealer, Deck deck, Supplier<String> inputProvider) {
        boolean firstTurn = true;
    
        while (true) {
            if (firstTurn) {
                System.out.print("Do you want to (H)it, (S)tay, or (D)ouble down? ");
            } else {
                System.out.print("Do you want to (H)it or (S)tay? ");
            }
    
            String input = inputProvider.get().trim().toUpperCase();
    
            if (input.equals("H")) {
                int newCard = dealer.dealCard();
                addCard(deck.getCard(newCard), deck.getValue(newCard));
                dealer.clearScreen();
                dealer.displayDealerWithOneFaceUp();
                displayHand();
    
                if (isBusted()) {
                    System.out.println("BUST! You went over 21.");
                    break;
                }
            } else if (input.equals("S")) {
                break;
            } else if (input.equals("D") && firstTurn) {
                System.out.println("You chose to DOUBLE DOWN. You will receive one card and end your turn.");
                int newCard = dealer.dealCard();
                addCard(deck.getCard(newCard), deck.getValue(newCard));
                dealer.clearScreen();
                dealer.displayDealerWithOneFaceUp();
                displayHand();
    
                if (isBusted()) {
                    System.out.println("BUST! You went over 21.");
                }
                break;
            } else {
                System.out.println("Invalid input.");
            }
    
            firstTurn = false;
        }
    }

}