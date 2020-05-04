import java.util.*;
import java.lang.*;
public class Deck {
    //you can directly access this arraylist for access to 
    //useful methods like size and others, but do not directly add or subtract cards
    public ArrayList<Card> deck = new ArrayList<Card>();
    //initializes a standard deck of 52 cards, no jokers
    public Deck() {
       deck.ensureCapacity(52);
       for (int i = 1; i<=4; i++) {
            for(int n = 1; n<=13; n++) {
                deck.add(new Card(n, i));
            }
       }
    }
    //returns current deck size
    public int count() {
        return deck.size(); 
    }
    //returns a card and removes it from the deck. use returncard to make sure you
    //dont play with a short deck
    public Card drawCard() {
        Card temp = deck.get(deck.size()-1);
        deck.remove(deck.size() - 1);
        return temp;
    }
    //shuffles the deck
    //works by making temp list, assigning cards to it in a random order
    public void shuffle() {
        int size = deck.size();
        ArrayList<Card> temp = new ArrayList<Card>();
        temp.ensureCapacity(size);
        for(int i = 0; i < size; i++)
        {
            int random = (int)(Math.random() * (deck.size()));
            temp.add(deck.get(random));
            deck.remove(random);
        }
        deck = temp;
    }
    //this method is for adding a card back to the deck
    //takes a card, checks if it exists in the deck, adds if not
    public void returnCard(Card newCard) {
        boolean check = false;
        for (int i = 0; i < 52; i++) {
            if (newCard.equals(i)) {
                check = true;
            }
        }
        if  (check != true) {
            deck.add(0, newCard);
        }
    }
}
