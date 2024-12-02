package views;
import models.Deck;
import models.Card.CardType;
import models.Card;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CardView extends JPanel {
    private Deck deck;         
    private JLabel cardTitleLabel; 
    private JTextArea instructionsArea;
    private JPanel cardPanel; 
    private int rectWidth = 200;
    private int rectHeight = 100;
    private boolean clicked = false;
    
    public CardView() {

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (isInsideRect(e.getX(), e.getY())) {
                    clicked = !clicked;  
                    repaint();  
                }
            }
        });
    }

    private boolean isInsideRect(int x, int y) {
        return x >= 50 && x <= 50 + rectWidth && y >= 50 && y <= 50 + rectHeight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (clicked) {
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        g.setColor(Color.BLUE);
        g.fillRoundRect(50, 50, rectWidth, rectHeight, 20, 20);

        g.setColor(Color.WHITE);
        FontMetrics metrics = g.getFontMetrics();
        String text = "Click Me!";
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();
        g.drawString(text, 50 + (rectWidth - textWidth) / 2, 50 + (rectHeight + textHeight) / 2);

        if (clicked) {
            rectWidth = 250;
            rectHeight = 150;
        } else {
            rectWidth = 200;
            rectHeight = 100;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Card View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new CardView());
        frame.setVisible(true);
    }
}

   /*  public static void main(String[] args) {
        // Test the CardView independently (for demonstration purposes)
        JFrame testFrame = new JFrame("Card View");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(300, 200);
        testFrame.add(new CardView());
        testFrame.setVisible(true);
    } */
