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
    public String userinput;
    public JLabel instructions;
    public boolean gameIsGoing;
    boolean playerturn;
    Player currentPlayer;
    //couldnt get split to work with my images and whatnot...

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
            instructions.setText("Do you want to keep playing?");
            String check = userInput.nextLine();
            if(check.equals("yes"))
            {
                resetGame();
            }
            else
            {
                gameIsGoing =false;
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
                instructions.setText("You busted this round, " + player.name);
                try{Thread.sleep(5000);}
                catch(InterruptedException exc){}
                instructions.setText("Better luck next time!");
            }
            else if(dealer.returnHandValue() > 21)
            {
                instructions.setText("Congratulations "+player.name+"!");
                try{Thread.sleep(5000);}
                catch(InterruptedException exc){}
                instructions.setText("You won this round!");
            }
            else if (player.returnHandValue() > dealer.returnHandValue())
            {
                instructions.setText("Congratulations "+player.name+"!");
                try{Thread.sleep(5000);}
                catch(InterruptedException exc){}
                instructions.setText("You won this round!");
            }
            else if (player.returnHandValue() < dealer.returnHandValue())
            {
                instructions.setText("I'm sorry " +player.name+ ".");
                try{Thread.sleep(5000);}
                catch(InterruptedException exc){}
                instructions.setText("Better luck next time!");
            }
            else if (player.returnHandValue() == dealer.returnHandValue())
            {
                instructions.setText("That's a push for "+ player.name);
                try{Thread.sleep(5000);}
                catch(InterruptedException exc){}
                instructions.setText("At least you didn't lose!");            
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
                //System.out.println("The Dealer's cards are:");
                /*for(Card card : dealer.hand)
                {
                    System.out.println(card.rankToString());
                }
                System.out.println("For a total of " + dealer.returnHandValue());
                */
                Card temp = gameDeck.drawCard();
                dealer.takeCard(temp);
                //System.out.println("The dealer hit and got a " + temp.rankToString());
            }

        }
        if(dealer.returnHandValue() > 21)
        {
            instructions.setText("Dealer busts");
        }
        else
        {
            instructions.setText("Dealer ends with a "+ dealer.returnHandValue());
        }
    }
    //does the full turn of hitting and staying for a single player
    public void playerTurn(Player player)
    {
        if(player.returnHandValue() == 21)
        {
            instructions.setText("Blackjack!");
        }
        else{playerturn = true;}
        while(player.returnHandValue() <= 21 && playerturn == true)
        {
            currentPlayer = player;
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
        frame.setVisible(true);
        gameDeck = new Deck();
        dealer = new Player();
            //input validation is a lot harder with textfields so dont enter non numbers
            /*playerCount = Integer.parseInt(userinput);
            while(playerCount <= 0 || playerCount >= 4) 
            {
                instructions.setText("Please enter a positive integer 3 or below");

                playerCount = Integer.parseInt(userinput);
            }
            */
          int playerCount =3;
            playerArray = new ArrayList<Player>();
            for (int i = 0; i < playerCount; i++) 
            {
                playerArray.add(new Player());
            }

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
            try{if(playerArray.get(0) != null)
            {
                frame.add(playerArray.get(0).panel);
            }
            
            else{frame.add(blank);}
        }
        catch(ArrayIndexOutOfBoundsException exc){frame.add(blank);}
            frame.add(menu);
            
            try{if(playerArray.get(1) != null)
            {
                frame.add(playerArray.get(1).panel);
            }
            
            else{frame.add(blank);}
        }
        catch(ArrayIndexOutOfBoundsException exc){frame.add(blank);}
            frame.add(doubler);
            try{if(playerArray.get(2) != null)
            {
                frame.add(playerArray.get(2).panel);
            }
            
            else{frame.add(blank);}
        }
        catch(ArrayIndexOutOfBoundsException exc){frame.add(blank);}
        
        
            frame.add(split);
            gameDeck.shuffle();
            frame.setVisible(true);
            gameIsGoing = true;
    }
    //asks to hit or stay, if player types hit returns true
    //if player types stay returns false
    public void userMove(Player player) 
    {
        instructions.setText("make your move...");
        
        //System.out.println("For a total of " + player.returnHandValue());
        //System.out.println("The dealer is showing a "+showing.rankToString());
        //System.out.println("Would you like to hit or stay?");
        //String playerInput = userInput.nextLine();
        /*while (!(playerInput.equals("hit") || playerInput.equals("stay"))) {
            System.out.println("Please enter either 'hit' to draw a card or 'stay' to hold");
            playerInput= userInput.nextLine();
        }
        if (playerInput.equals("hit")) {
            return true;
        }
        else {
            return false;
        }*/
    }
     class HitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) 
        {
            if(playerturn = true)
            {
                currentPlayer.takeCard(gameDeck.drawCard());
            }
        }
    }
     class StayListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) 
        {
            if(playerturn = true){playerturn = false;}
        }
    }
     class DoubleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) 
        {
            if(playerturn = true && currentPlayer.hand.size() < 3)
            {
                currentPlayer.takeCard(gameDeck.drawCard());
                playerturn = false;
            }
        }
    }
     class SplitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) 
        {
        }
    }
    
}
