package views;
import models.Deck;
import models.Card.CardType;
import models.Card;
import javax.swing.*;
import java.awt.*;

public class CardView extends JPanel {
    private Deck deck;         
    private JLabel cardTitleLabel; 
    private JTextArea instructionsArea;
    private JPanel cardPanel; 

    public CardView() {

    }

   /*  public static void main(String[] args) {
        // Test the CardView independently (for demonstration purposes)
        JFrame testFrame = new JFrame("Card View");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(300, 200);
        testFrame.add(new CardView());
        testFrame.setVisible(true);
    } */

}
