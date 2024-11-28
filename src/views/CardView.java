package views;

import models.Deck;
import models.Card;
import javax.swing.*;
import java.awt.*;

public class CardView extends JPanel {
    private Deck deck;         
    private JLabel cardTitleLabel; 
    private JTextArea instructionsArea;
    private JPanel cardPanel; 

    public CardView(Deck deck) {
        this.deck = deck;  

        setLayout(new BorderLayout());

        cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());

        cardTitleLabel = new JLabel("Card: No Card Drawn Yet", SwingConstants.CENTER);
        cardTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        instructionsArea = new JTextArea("No instructions available yet.");
        instructionsArea.setFont(new Font("Arial", Font.PLAIN, 18));
        instructionsArea.setEditable(false); 
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        cardPanel.add(cardTitleLabel, BorderLayout.NORTH);
        cardPanel.add(instructionsArea, BorderLayout.CENTER);
        add(cardPanel, BorderLayout.CENTER);
    }

 
    public void drawCard() {

        Card drawnCard = deck.drawCard();

        cardTitleLabel.setText("Card: " + drawnCard.toString());
        
        instructionsArea.setText(drawnCard.getInstructions());

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        // Test the CardView independently (for demonstration purposes)
        JFrame testFrame = new JFrame("Card View");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(300, 200);
        testFrame.add(new CardView(deck));
        testFrame.setVisible(true);
    }
}
