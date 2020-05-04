import java.util.*;
public class Player {
    //love me some instance variables
    static boolean dealer = false;
    public final String name;
    public ArrayList<Card> hand = new ArrayList<Card>();    
    static int playercount = 1;
    private int handvalue;
    public boolean soft = false;
    //automatically first player is dealer, all other players are just players
    public Player()
    {
        Scanner keyboard = new Scanner(System.in);
        if (dealer == false)
        {
            name = "Dealer";
            dealer = true;
        }
        else
        {
            System.out.println("Please enter Player "+  playercount +
            "'s name");
            name = keyboard.nextLine();
            playercount++;
        }
        keyboard.close();
    }
    //adds a card to the player's hand
    public void takeCard(Card newCard)
    {
        hand.add(newCard);
    }
    //clears the players hand for round reset
    public void ClearHand()
    {
        hand.clear();
    }
    //returns an int of what the players hand value is
    public int returnHandValue()
    {
        int total = 0;
        boolean aces = false;
        for(Card card : hand)
        {
            if(card.rank == 1){aces = true; total++;}
            else if(card.rank >= 10){total = total + 10;}
            else if(card.rank < 10){total = total + card.rank;}
            else{System.out.println("Wtf is this card boy");}            
        }
        if (aces == true && total <= 11){total = total + 10; soft = true;}
        return total;
    }
}
