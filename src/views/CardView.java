package views;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import models.Card;

public class CardView extends JPanel {
    private Card card;

    public CardView(Card card) {
        this.card = card;
        setOpaque(true); 
        setPreferredSize(new Dimension(300, 400)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = 50;
        int y = 50;
        int width = 200;
        int height = 300;
        int arcWidth = 30;
        int arcHeight = 30;

        g2d.setColor(Color.WHITE); 
        g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);

        if (card != null) {
            try {
                Image img = ImageIO.read(new File(card.getImage()));
                Shape clip = new RoundRectangle2D.Float(x, y, width, height, arcWidth, arcHeight);
                g2d.setClip(clip);
                g2d.drawImage(img, x, y, width, height, this);
            } catch (IOException e) {
                e.printStackTrace();
                g2d.setColor(Color.RED);
                g2d.drawString("Image not found", x + width / 4, y + height / 2);
            }
        }
    }
}
