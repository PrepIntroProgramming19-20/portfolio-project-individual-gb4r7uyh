import java.util.*;
public class Game {
    //these variables need to be used in several methods and
    //it is a hassle to pass them every time
    public static Deck gameDeck;
    public static Scanner userInput = new Scanner(System.in);
    public static Player dealer;
    public static ArrayList<Player> playerArray;
    public static Card showing;
    //main is a simple loop system meant to just handle the highest level
    public static void main(String[] args) 
    {
        startGame();
        boolean gameIsGoing = true;
        while (gameIsGoing == true) {
            dealAll();
            for (Player player: playerArray) {
                playerTurn(player);
            }
            dealerTurn();
            scoreCompare();
            System.out.println("Do you want to keep playing?");
            String check = userInput.nextLine();
            if(check.equals("yes"))
            {
                resetGame();
            }
            else
            {
                gameIsGoing =false;
                userInput.close();
            }
        }
        //this is just because bluej wouldnt stop running the program even tho 
        //it was over
        System.exit(0);
    }
    //this reshuffles the deck and clears all hands
    public static void resetGame()
    {
        for(Player player : playerArray)
        {
            player.ClearHand();
        }
        dealer.ClearHand();
        gameDeck = new Deck();
        gameDeck.shuffle();
    }
    //compares all scores to the dealer, accounts that the player loses
    //if both they and the dealer bust
    public static void scoreCompare()
    {
        for(Player player : playerArray)
        {
            if (player.returnHandValue()>21)
            {
                System.out.println("You busted this round, " + player.name);
                System.out.println("Better luck next time!");
            }
            else if(dealer.returnHandValue() > 21)
            {
                System.out.println("Congratulations "+player.name+"!");
                System.out.println("You won this round!");
            }
            else if (player.returnHandValue() > dealer.returnHandValue())
            {
                System.out.println("Congratulations "+player.name+"!");
                System.out.println("You won this round!");
            }
            else if (player.returnHandValue() < dealer.returnHandValue())
            {
                System.out.println("I'm sorry " +player.name+ ".");
                System.out.println("Better luck next time!");
            }
            else if (player.returnHandValue() == dealer.returnHandValue())
            {
                System.out.println("That's a push for "+ player.name);
                System.out.println("At least you didn't lose!");            
            }
        }
    }
    //the dealers turn
    // accounts for hit on soft 17
    public static void dealerTurn()
    {
        if(dealer.returnHandValue() == 21)
        {
            System.out.println("Dealer Blackjack!");
        }
        while(dealer.returnHandValue() <= 17)
        {
            if (dealer.returnHandValue() != 17 || dealer.soft == true)
            {
                System.out.println("The Dealer's cards are:");
                for(Card card : dealer.hand)
                {
                    System.out.println(card.rankToString());
                }
                System.out.println("For a total of " + dealer.returnHandValue());
                Card temp = gameDeck.drawCard();
                dealer.takeCard(temp);
                System.out.println("The dealer hit and got a " + temp.rankToString());
            }

        }
        if(dealer.returnHandValue() > 21)
        {
            System.out.println("Dealer busts");
        }
        else
        {
            System.out.println("Dealer ends with a "+ dealer.returnHandValue());
        }
    }
    //does the full turn of hitting and staying for a single player
    public static void playerTurn(Player player)
    {
        if(player.returnHandValue() == 21)
        {
            System.out.println("Blackjack!");
        }
        while(player.returnHandValue() <= 21)
        {
            System.out.println("What do you want to do, "+ player.name);
            boolean hit = userMove(player);
            if(hit == true)
            {
                Card temp = gameDeck.drawCard();
                player.takeCard(temp);
                System.out.println("You hit and got a " + temp.rankToString());
            }
            else
            {
                System.out.println("Good choice to stay on a "+player.returnHandValue());
                break;
            }
        }
        if (player.returnHandValue() > 21)
        {
            System.out.println("Busted!");
        }

    }
    //deals to cards to each player, in 1-1-1-1-1-dealer,1-1-1-1-1-dealer 
    //order
    //this doesnt use a loop so we can save the second card dealt to the
    //dealer thats the one he is showing
    public static void dealAll() 
    {
        for (Player player: playerArray) {
            player.takeCard(gameDeck.drawCard());
        }
        dealer.takeCard(gameDeck.drawCard());
        for (Player player: playerArray) {
            player.takeCard(gameDeck.drawCard());
        }
        showing = gameDeck.drawCard();
        dealer.takeCard(showing);

    }
    //initializes all static variables, creates the player array
    //creates the dealer, and shuffles the deck
    //only called once, resetgame is used to play multiple rounds
    public static void startGame() 
    {
        gameDeck = new Deck();
        dealer = new Player();
        System.out.println("Enter how many players in the game: ");
        int playerCount = -1;
        if(userInput.hasNextInt() == true)
        {
            playerCount = userInput.nextInt();
        }
        else
        {
            userInput.next();
        }
        while(playerCount <= 0) 
        {
            System.out.println("Please enter a positive integer");
            if(userInput.hasNextInt() == false)
            {
                userInput.next(); 
                continue;
            }
            playerCount = userInput.nextInt();
        }
        playerArray = new ArrayList<Player>();
        for (int i = 0; i < playerCount; i++) 
        {
            playerArray.add(new Player());
        }
        gameDeck.shuffle();
    }
    //asks to hit or stay, if player types hit returns true
    //if player types stay returns false
    public static boolean userMove(Player player) 
    {
        System.out.println("Your cards are:");
        for(Card card : player.hand)
        {
            System.out.println(card.rankToString());
        }
        System.out.println("For a total of " + player.returnHandValue());
        System.out.println("The dealer is showing a "+showing.rankToString());
        System.out.println("Would you like to hit or stay?");
        String playerInput = userInput.nextLine();
        while (!(playerInput.equals("hit") || playerInput.equals("stay"))) {
            System.out.println("Please enter either 'hit' to draw a card or 'stay' to hold");
            playerInput= userInput.nextLine();
        }
        if (playerInput.equals("hit")) {
            return true;
        }
        else {
            return false;
        }
    }
}
