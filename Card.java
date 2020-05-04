public class Card{
    public final int rank;
    public final int suit;
    public final static int ACE   = 1;
    public final static int DEUCE = 2;
    public final static int THREE = 3;
    public final static int FOUR  = 4;
    public final static int FIVE  = 5;
    public final static int SIX   = 6;
    public final static int SEVEN = 7;
    public final static int EIGHT = 8;
    public final static int NINE  = 9;
    public final static int TEN   = 10;
    public final static int JACK  = 11;
    public final static int QUEEN = 12;
    public final static int KING  = 13;
    
    public final static int DIAMONDS = 1;
    public final static int CLUBS = 2;
    public final static int HEARTS = 3;
    public final static int SPADES = 4;
    
    public Card(int inputRank, int inputSuit) {
         if ((inputRank <= 13 && inputRank >= 1) && (inputSuit <= 4 && inputSuit >= 1)) {
             rank = inputRank;
             suit = inputSuit;
            }
         else {
            throw new RuntimeException();
            }
    }

    // Here is the if-then-else approach for returning the string
    // as a rank
    public static String rankToString(int rank) {
        if (rank == ACE) {
            return "Ace";
        } else if (rank == DEUCE) {
            return "Deuce";
        } else if (rank == THREE) {
            return "Three";
        } else if (rank == FOUR) {
            return "Four";
        } else if (rank == FIVE) {
            return "Five";
        } else if (rank == SIX) {
            return "Six";
        } else if (rank == SEVEN) {
            return "Seven";
        } else if (rank == EIGHT) {
            return "Eight";
        } else if (rank == NINE) {
            return "Nine";
        } else if (rank == TEN) {
            return "Ten";
        } else if (rank == JACK) {
            return "Jack";
        } else if (rank == QUEEN) {
            return "Queen";
        } else if (rank == KING) {
            return "King";
        } else {
            //Handle an illegal argument.  There are generally two
            //ways to handle invalid arguments, throwing an exception
            //(see the section on Handling Exceptions) or return null
            return null;
        }    
    }
    

    // Here is the switch implementation of rankToString
    /*
    public static String rankToString(int rank) {
        switch (rank) {
        case ACE:
            return "Ace";
        case DEUCE:
            return "Deuce";
        case THREE:
            return "Three";
        case FOUR:
            return "Four";
        case FIVE:
            return "Five";
        case SIX:
            return "Six";
        case SEVEN:
            return "Seven";
        case EIGHT:
            return "Eight";
        case NINE:
            return "Nine";
        case TEN:
            return "Ten";
        case JACK:
            return "Jack";
        case QUEEN:
            return "Queen";
        case KING:
            return "King";
        default:
            //Handle an illegal argument.  There are generally two
            //ways to handle invalid arguments, throwing an exception
            //(see the section on Handling Exceptions) or return null
            return null;
        }    
    }
    */    

    public static String suitToString(int inputSuit) {
        if (inputSuit == 1) {
            return "Diamonds";
        }
        else if (inputSuit == 2) {
            return "Clubs";
        }
        else if (inputSuit == 3) {
            return "Hearts";
        }
        else if (inputSuit == 4) {
            return "Spades";
        }
        else {
            return null;
        }
    }
    public String rankToString() {
        return rankToString(rank); 
    }
    public String suitToString() {
        return suitToString(suit); 
    }
    @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Card))
                return false;
            Card rhs = (Card) obj;
            return (suit == rhs.suit &&
                    rank == rhs.rank);
        }
    @Override
        public int hashCode() {
            int result = 0;
            return 31 * suit + rank;
        }
}
