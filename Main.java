import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Deck deck = new Deck();
            Dealer dealer = new Dealer(deck);
            Player player1 = new Player();
            Player player2 = new Player();
            Player player3 = new Player();

            dealer.dealInitial();

            // Deal to each player
            int[] cards = new int[] {
                    dealer.dealCard(), dealer.dealCard(),
                    dealer.dealCard(), dealer.dealCard(),
                    dealer.dealCard(), dealer.dealCard()
            };

            int val1 = deck.getValue(cards[0]);
            int val2 = deck.getValue(cards[1]);

            if (val1 == val2) {
                System.out.println("Player 1 drew two cards of the same value. Do you want to split? (Y/N)");
                String response = scanner.nextLine().trim().toUpperCase();

                if (response.equals("Y")) {
                    Player splitHand1 = new Player();
                    Player splitHand2 = new Player();

                    // Deal one new card to each hand
                    int extra1 = dealer.dealCard();
                    int extra2 = dealer.dealCard();

                    splitHand1.addCard(deck.getCard(cards[0]), val1, () -> scanner.nextLine());
                    splitHand1.addCard(deck.getCard(extra1), deck.getValue(extra1), () -> scanner.nextLine());

                    splitHand2.addCard(deck.getCard(cards[1]), val2, () -> scanner.nextLine());
                    splitHand2.addCard(deck.getCard(extra2), deck.getValue(extra2), () -> scanner.nextLine());

                    System.out.println("----- Player 1 — Hand 1 -----");
                    splitHand1.displayHand();
                    if (splitHand1.getTotalValue() != 21)
                        splitHand1.takeTurnWithDoubleDown(dealer, deck, () -> scanner.nextLine());

                    System.out.println("----- Player 1 — Hand 2 -----");
                    splitHand2.displayHand();
                    if (splitHand2.getTotalValue() != 21)
                        splitHand2.takeTurnWithDoubleDown(dealer, deck, () -> scanner.nextLine());

                    // Store splitHand1 and splitHand2 somewhere (e.g. in a List) for final result
                    // comparison later
                    // OR repeat result logic immediately below if keeping code simple

                } else {
                    player1.addCard(deck.getCard(cards[0]), val1, () -> scanner.nextLine());
                    player1.addCard(deck.getCard(cards[1]), val2, () -> scanner.nextLine());
                }
            } else {
                player1.addCard(deck.getCard(cards[0]), val1, () -> scanner.nextLine());
                player1.addCard(deck.getCard(cards[1]), val2, () -> scanner.nextLine());
            }

            player2.addCard(deck.getCard(cards[2]), deck.getValue(cards[2]), () -> scanner.nextLine());
            player2.addCard(deck.getCard(cards[3]), deck.getValue(cards[3]), () -> scanner.nextLine());

            player3.addCard(deck.getCard(cards[4]), deck.getValue(cards[4]), () -> scanner.nextLine());
            player3.addCard(deck.getCard(cards[5]), deck.getValue(cards[5]), () -> scanner.nextLine());

            dealer.clearScreen();
            dealer.displayDealerWithOneFaceUp();

            System.out.println("----- Player 1 -----");
            player1.displayHand();
            if (player1.getTotalValue() != 21)
                player1.takeTurnWithDoubleDown(dealer, deck, () -> scanner.nextLine());

            System.out.println("----- Player 2 -----");
            player2.displayHand();
            if (player2.getTotalValue() != 21)
                player2.takeTurnWithDoubleDown(dealer, deck, () -> scanner.nextLine());

            System.out.println("----- Player 3 -----");
            player3.displayHand();
            if (player3.getTotalValue() != 21)
                player3.takeTurnWithDoubleDown(dealer, deck, () -> scanner.nextLine());

            dealer.clearScreen();
            System.out.println("Dealer's Turn...");
            if (!player1.isBusted() || !player2.isBusted() || !player3.isBusted()) {
                dealer.playDealerTurn(0); // any number, ignored in logic
            }

            dealer.displayDealerFullHand();

            // Result for each player
            int dealerTotal = dealer.getDealerTotal();

            Player[] allPlayers = { player1, player2, player3 };

            for (int i = 0; i < allPlayers.length; i++) {
                Player p = allPlayers[i];
                System.out.println("\n----- Player " + (i + 1) + " -----");
                p.displayHand();

                if (p.isBusted()) {
                    System.out.println("You busted. Dealer wins.");
                } else if (dealerTotal > 21) {
                    System.out.println("Dealer busted. You win!");
                } else if (p.getTotalValue() > dealerTotal) {
                    System.out.println("You win with " + p.getTotalValue() + " over dealer's " + dealerTotal + "!");
                } else if (p.getTotalValue() < dealerTotal) {
                    System.out.println("Dealer wins with " + dealerTotal + " over your " + p.getTotalValue() + ".");
                } else {
                    System.out.println("It's a tie! Push.");
                }
            }

            System.out.print("\nPlay again? (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (!input.equals("Y")) {
                System.out.println("Thanks for playing!");
                break;
            }

            dealer.reset();
            player1.reset();
            player2.reset();
            player3.reset();
        }
    }

}
