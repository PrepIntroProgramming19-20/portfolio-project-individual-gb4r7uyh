import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Player {
    //love me some instance variables
    static boolean dealer = false;
    public final String name;
    public ArrayList<Card> hand = new ArrayList<Card>();    
    static int playercount = 1;
    private int handvalue;
    public boolean soft = false;
    public JPanel panel;
    public ImagePanel cardpanel;
    public JLabel namedisplay;
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
            name = "Player "+ playercount;
            playercount++;
        }
        keyboard.close();
        panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        cardpanel = new ImagePanel();
        panel.add(cardpanel);
        namedisplay = new JLabel();
        namedisplay.setText(name);
        panel.add(namedisplay);
        panel.setSize(300,300);
    }
    //adds a card to the player's hand
    public void takeCard(Card newCard)
    {
        hand.add(newCard);
        String filename = newCard.rankToString() + newCard.suitToString() +".jpg";
        cardpanel.addImage(filename);
    }
    //clears the players hand for round reset
    public void ClearHand()
    {
        hand.clear();
        cardpanel.images.clear();
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
