import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Game {
    //these variables need to be used in several methods and
    //it is a hassle to pass them every time
    public  Deck gameDeck;
    public  Scanner userInput = new Scanner(System.in);
    public  Player dealer;
    public  ArrayList<Player> playerArray;
    public  Card showing;
    public  JFrame frame;
    public  JPanel menu;
    public JTextField textbox;
    public String userinput;
    public JLabel instructions;
    public boolean gameIsGoing;
    //main is a simple loop system meant to just handle the highest level
    public static void main(String[] args)
    {
        Game game = new Game();
        game.main();
    }

    public void main() 
    {
        startGame();
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
    }
    //this reshuffles the deck and clears all hands
    public void resetGame()
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
    public void scoreCompare()
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
    public void dealerTurn()
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
    public void playerTurn(Player player)
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
    public void dealAll() 
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
    public void startGame() 
    {
        frame = new JFrame();
        frame.setSize(900,900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instructions = new JLabel();
        menu = new JPanel();
        menu.add(instructions);
        frame.add(menu);
        textbox = new JTextField("Enter how many players in the game: ");
        textbox.addActionListener(new TextListener());
        menu.add(textbox);
        frame.setVisible(true);
        gameDeck = new Deck();
        dealer = new Player();

    }
    //asks to hit or stay, if player types hit returns true
    //if player types stay returns false
    public boolean userMove(Player player) 
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
     class HitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) 
        {
        }
    }
     class StayListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) 
        {
        }
    }
     class DoubleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) 
        {
        }
    }
     class SplitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) 
        {
        }
    }
    class TextListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) 
        {

            userinput = textbox.getText();
            int playerCount = -1;
            userinput.trim();
            //input validation is a lot harder with textfields so dont enter non numbers
            playerCount = Integer.parseInt(userinput);
            while(playerCount <= 0 || playerCount >= 4) 
            {
                instructions.setText("Please enter a positive integer 3 or below");

                playerCount = Integer.parseInt(userinput);
            }
            playerArray = new ArrayList<Player>();
            for (int i = 0; i < playerCount; i++) 
            {
                playerArray.add(new Player());
            }
            frame.remove(menu);
            frame.setLayout(new GridLayout(3,3));
            JPanel blank = new JPanel();
            JButton hit = new JButton("Hit!");
            hit.addActionListener(new HitListener());
            JButton stay = new JButton("Stay!");
            hit.addActionListener(new StayListener());
            JButton split = new JButton("Split!");
            hit.addActionListener(new SplitListener());
            JButton doubler = new JButton("Double!");
            hit.addActionListener(new DoubleListener());
            hit.setSize(300,300);
            frame.add(hit);
            frame.add(dealer.panel);
            frame.add(stay);
            if(playerArray.get(0) != null)
            {
                frame.add(playerArray.get(0).panel);
            }
            else{frame.add(blank);}
            frame.add(menu);
            if(playerArray.get(1) != null)
            {
                frame.add(playerArray.get(1).panel);
            }
            else{frame.add(blank);}
            frame.add(doubler);
            if(playerArray.get(2) != null)
            {
                frame.add(playerArray.get(2).panel);
            }
            else{frame.add(blank);}
            frame.add(split);
            gameDeck.shuffle();
            frame.setVisible(true);
            gameIsGoing = true;
        }
    }
}
