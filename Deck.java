public class Deck {

    private final String[] deck = new String[52]; // deck array
    private int[] values = new int[52]; // deck value array
    
    //make suits and ranks acessable to dealer
    public static final String[] suits = { "\u2660", "\u2665", "\u2666", "\u2663" }; // {♠, ♥, ♦, ♣} - RUN THIS IN TERMINAL IF SUITS NOT SHOWING: chcp 65001
    public static final String[] ranks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
    
    public Deck() 
    {
    
            int idx = 0;
    
            for (String suit : suits) 
            {
    
                for (String rank : ranks) 
                {
     
                    int value;
                    switch (rank) //switch statment assigning value to cards based on rank
                    {
                        case "A": value = 11; break;  //default to 11 - impliment 1 case later (if val == 11 then ask if 11 or 21)
                        case "K": value = 10; break;
                        case "Q": value = 10; break;
                        case "J": value = 10; break;  
                        default: value = Integer.parseInt(rank); break; //set number card to corosponding index vals 
                    }
    
                    values[idx] = value;
    
                    //CARD BUILDER LOGIC, DO NOT TOUCH
                    StringBuilder cardBuild = new StringBuilder();
                    cardBuild.append(".───────────────.").append("\n");
    
                    //top line
                    cardBuild.append("│").append(rank).append(" ").append(suit);
    
                    int padRight = 15 - (rank.length() + 2);
                    for (int i = 0; i < padRight; i++) cardBuild.append(" ");
    
                    cardBuild.append("│\n");
    
                    String blank = "│               │\n";
                    cardBuild.append(blank).append(blank).append(blank);
                    cardBuild.append("│     ( ").append(suit).append(" )     │\n");
                    cardBuild.append(blank).append(blank).append(blank);
    
                    cardBuild.append("│");
    
                    int lead = 12 - rank.length();
                    for (int i = 0; i < lead; i++) cardBuild.append(" ");
    
                    cardBuild.append(suit).append(" ").append(rank).append(" │\n");
    
                    cardBuild.append("└───────────────┘");
    
                    deck[idx++] = cardBuild.toString();
                }
            }
        }
    

    //#################################################### Helper Methods #################################################
    
    
    //test method to print card and its value from certain slot using index
    public void printCard(int index) 
    {
        System.out.println(deck[index]);
        System.out.println("Value: " + values[index]);
    }
    
    //return value of card based on index
    public int getValue(int index) 
    {
        return values[index];
    }
    
    //test method to print full deck
    public void printDeck() 
    {
    
        for (String card : deck) 
        {
            System.out.println(card);
        }
    
    }
    
    //return a card
    public String getCard(int index) 
    {
        return deck[index];
    }
    
    //method to print multiple cards side by side
    public static void printCardsSideBySide(String[] cards)
    {
        if (cards == null || cards.length == 0) return;
    
        String[][] splitCards = new String[cards.length][];
        for (int i = 0; i < cards.length; i++) 
        {
            splitCards[i] = cards[i].split("\n");
        }
    
        int numLines = splitCards[0].length;
    
        for (int line = 0; line < numLines; line++) 
        {
            for (String[] card : splitCards) 
            {
                System.out.print(card[line] + "  ");
            }
            System.out.println();
        }
    }
    
}