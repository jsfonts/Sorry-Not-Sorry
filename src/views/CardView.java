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

}