package views;

import models.Player;
import models.Tile;
import models.Pawn;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import models.Card;
import models.Player;
import controllers.GameController;
import java.awt.*;
import java.awt.geom.*;
import models.Board;
import java.awt.image.BufferedImage;
import models.HumanPlayer;
import models.ComputerPlayer;
import java.util.ArrayList;

public class GameView extends JFrame {
    private GameBoardPanel gameBoardPanel;
    private JMenuItem restartMenuItem;
    private JMenuItem newGameMenuItem;
    private JMenuItem SaveMenuItem;
    private JMenuItem quitMenuItem;
    private JMenuItem rulesMenuItem;
    private GameController controller;
    private Dimension computerScreenSize;
    private Image gameBoardImage;

    //for renderingthe board
    private int PAWN_SIZE;
    private int gridH;
    private int gridW;
    private int grid_x;  //board image corner location
    private int grid_y;
    private int cellW;
    private int cellH;

    public GameView(GameController controller) {
        this.controller = controller;
        setTitle("Sorry Not Sorry!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //PlayerTurnText = new JLabel();
        
        computerScreenSize = getToolkit().getScreenSize();
        setSize(computerScreenSize);

        File imgFile = new File("../resources/Sorry_board.svg.png");
        if (imgFile.exists()) {
            try {
                gameBoardImage = ImageIO.read(imgFile); 
                gameBoardPanel = new GameBoardPanel(gameBoardImage); 
                gameBoardPanel.setBackground(new Color(202, 220, 224));
                add(gameBoardPanel, BorderLayout.CENTER);
            } catch (IOException e) {
                System.err.println("Failed to load game board image: " + e.getMessage());
            }
        } else {
            System.err.println("Image file not found at path: " + imgFile.getAbsolutePath());
        }

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JMenu gameMenu = new JMenu("Game");
        JMenu helpMenu = new JMenu("?");

        restartMenuItem = new JMenuItem("Restart");
        newGameMenuItem = new JMenuItem("New Game");
        SaveMenuItem = new JMenuItem("Save Game");
        quitMenuItem = new JMenuItem("Quit");
        rulesMenuItem = new JMenuItem("Rules");

        gameMenu.add(restartMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(newGameMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(SaveMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(quitMenuItem);

        helpMenu.add(rulesMenuItem);
        menuBar.add(helpMenu);
        menuBar.add(gameMenu);

        setJMenuBar(menuBar);
        setVisible(false);
      
        //this was supposed to see the cards but it removed the game board underneath it.
       /* overlayPanel = new JPanel(null); // Use null layout for absolute positioning
        overlayPanel.setOpaque(false);   // Make it transparent
        overlayPanel.setSize(getSize());
        overlayPanel.setVisible(fal);  // Hidden initially
        
        add(overlayPanel, 0); */
    }
    /*  public void showCard(Card card) {
        overlayPanel.removeAll(); // Clear previous card view

        CardView cardView = new CardView(card);
        cardView.setBounds(getWidth() / 2 - 125, getHeight() / 2 - 175, 300, 400);
        overlayPanel.add(cardView);

        overlayPanel.setVisible(true);
        repaint();
    }

    public void hideCard() {
        overlayPanel.setVisible(false);
    }
    */
    public void addRestartListener(ActionListener listener) {
        restartMenuItem.addActionListener(listener);
    }

    public void addNewGameListener(ActionListener listener) {
        newGameMenuItem.addActionListener(listener);
    }

    public void addSaveGameListener(ActionListener listener) {
        SaveMenuItem.addActionListener(listener);
    }

    public void addQuitListener(ActionListener listener) {
        quitMenuItem.addActionListener(listener);
    }

    public void addRulesListener(ActionListener listener) {
        rulesMenuItem.addActionListener(listener);
    }


    public void updateCard(Card c){
        gameBoardPanel.updateNewCard(c);
    }
        
    public void newTurnCard()
    {
        gameBoardPanel.showCard = false;
        gameBoardPanel.repaint();
    }

    public void highlightavailablePawns(ArrayList<Pawn> pawnList){
        gameBoardPanel.setAvailablePawns(pawnList);
    }

    public void showRules() {
        JEditorPane editorPane = new JEditorPane();
            try{
                System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
                File rulesHTML = new File("../resources/rules.html");
                editorPane.setPage(rulesHTML.toURI().toURL());
            }catch(Exception e){
                System.out.println("\nCould not find the file or could not convert it to a URL. Exception thrown:\n" + e);
            }
            
            editorPane.setContentType("text/html");
            editorPane.setEditable(false);
    
            JScrollPane scrollPane = new JScrollPane(editorPane);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setSize(computerScreenSize.width/2, computerScreenSize.height/2);
    
            JOptionPane.showMessageDialog(this, scrollPane, "Sorry! Game Rules", JOptionPane.INFORMATION_MESSAGE);
        }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        gameBoardPanel.repaint();
    }

private class GameBoardPanel extends JPanel {
    private Image gameBoardImage;
    private Image overlayImage;
    private Image cardImage;
    private double scale;
    private RoundRectangle2D roundedRectCard;
    private ArrayList<Pawn> availablePawns;
    public boolean showCard = false; // Track if card should be displayed 
    public boolean isHumanPlayer = false; //track if its a human players turn

    public GameBoardPanel(Image gameBoardImage) {
        availablePawns = null;
        this.gameBoardImage = gameBoardImage;
        loadOverlayImage("../resources/CardBack.png");
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                boolean cardSelected = false;
                if (isClickInsideCard(e.getPoint())) {
                    controller.drawCard();
                }
                /*if (!cardSelected && !showCard) {
                    controller.turnMessage();
                }*/
            }
        });
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                int clickX = e.getX();
                int clickY = e.getY();
                Pawn selectedPawn;
                boolean pawnSelected = false;

                for(Pawn p : controller.getPawns()){
                    if(pawnContainsPoint(p, clickX, clickY)){
                        selectedPawn = p;
                        controller.doTurn(selectedPawn);
                        pawnSelected = true;
                        break;
                    }
                }
                if (!pawnSelected) {
                    if (showCard && !isClickInsideCard(e.getPoint())) {
                        controller.ErrorMessage();
                    }
                }
            }
                
        });
    }
    
    private boolean pawnContainsPoint(Pawn p, int clickX, int clickY){
        boolean contains = false;
        int pawnY = (int)(grid_y + p.getCoords()[0] * cellH);
        int pawnX = (int)(grid_x + p.getCoords()[1] * cellW);

        //System.out.println("Scaled pawn position: (" + pawnX + ", " + pawnY + ")");
        //System.out.println("Click position: (" + clickX + ", " + clickY + ")");
        //System.out.println("Pawn size: " + PAWN_SIZE);
        //System.out.println();
        
        if(clickX >= pawnX && clickX <=  pawnX + PAWN_SIZE && clickY >= pawnY && clickY <= pawnY + PAWN_SIZE){
            contains = true;
            System.out.println("\nThis pawn was clicked\n");
        }

        return contains;
    }

    private void loadOverlayImage(String path) {
        try {
            File overlayImgFile = new File(path);
            if (overlayImgFile.exists()) {
                overlayImage = ImageIO.read(overlayImgFile);
            }
        } catch (IOException e) {
            System.err.println("Failed to load overlay image: " + e.getMessage());
        }
    }

    private boolean isClickInsideCard(Point clickPoint) {
        return roundedRectCard != null && roundedRectCard.contains(clickPoint);
    }

    public void updateNewCard(Card card) {
        String path = card.getImage();
        try {
            File cardFile = new File(path);
            if (cardFile.exists()) {
                cardImage = ImageIO.read(cardFile);
            }
        } catch (IOException e) {
            System.err.println("Failed to load card image: " + e.getMessage());
        }
        showCard = true; // Trigger card display
        repaint();
    }

    public void setAvailablePawns(ArrayList<Pawn> pawnList){
        availablePawns = pawnList;
        System.out.println("Doing the pawn highlighting");
        repaint();
        availablePawns = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameBoardImage != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imgWidth = gameBoardImage.getWidth(this);
            int imgHeight = gameBoardImage.getHeight(this);

            scale = Math.min((double) panelWidth / imgWidth, (double) panelHeight / imgHeight);

            int newImgWidth = (int) (imgWidth * scale);
            int newImgHeight = (int) (imgHeight * scale);
            int x = (panelWidth - newImgWidth) / 2;
            int y = (panelHeight - newImgHeight) / 2;

            g.drawImage(gameBoardImage, x, y, newImgWidth, newImgHeight, this);

            PAWN_SIZE = newImgHeight / 23;

            gridH = (int)(newImgHeight*.893);
            gridW = (int)(newImgWidth*.893);
            grid_x = (int)(x+(newImgWidth*0.0535));
            grid_y = (int)(y+newImgWidth*0.0535);
            cellH = (int)(gridH / 16.0);
            cellW = (int)(gridW / 16.0);

            for(Pawn pawn : controller.getPawns()){
                int pawnX = (int)(grid_x + pawn.getCoords()[1] * cellW + cellW/2);
                int pawnY = (int)(grid_y + pawn.getCoords()[0] * cellH + cellH/2);

                g.setColor(pawn.getColor());
                g.fillOval(pawnX - PAWN_SIZE/2, pawnY - PAWN_SIZE/2, PAWN_SIZE, PAWN_SIZE);
                
                Graphics2D g2d = (Graphics2D)g;
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(PAWN_SIZE/12));

                if(availablePawns != null && availablePawns.contains(pawn)){
                        g2d.setColor(Color.MAGENTA);
                        g2d.setStroke(new BasicStroke(PAWN_SIZE/10));
                }
                
                g2d.drawOval(pawnX - PAWN_SIZE/2, pawnY - PAWN_SIZE/2, PAWN_SIZE, PAWN_SIZE);
            }
            
            if (overlayImage != null) {
                drawOverlay((Graphics2D) g);
            }

            if (showCard && cardImage != null) {
                drawCardImage((Graphics2D) g);
            }
        }
    }

    private void drawOverlay(Graphics2D g2d) {
        BufferedImage overlayBufferedImage = convertToBufferedImage(overlayImage);

        int overlayWidth = overlayBufferedImage.getWidth();
        int overlayHeight = overlayBufferedImage.getHeight();
        int newOverlayWidth = (int) (overlayWidth * scale * 2);
        int newOverlayHeight = (int) (overlayHeight * scale * 2);
        double xcoord = (getWidth() - newOverlayWidth) / 2.0;
        double ycoord = (getHeight() - newOverlayHeight) / 2.0;

        roundedRectCard = new RoundRectangle2D.Double(xcoord, ycoord, newOverlayWidth, newOverlayHeight, 30, 30);
        g2d.setClip(roundedRectCard);
        g2d.drawImage(overlayBufferedImage, (int) xcoord, (int) ycoord, newOverlayWidth, newOverlayHeight, null);
        //g2d.setClip(null);
    }

    private void drawCardImage(Graphics2D g2d) {
        BufferedImage cardBufferedImage = convertToBufferedImage(cardImage);

        int cardWidth = cardBufferedImage.getWidth();
        int cardHeight = cardBufferedImage.getHeight();
        int newCardWidth = (int) (cardWidth * scale * 2);
        int newCardHeight = (int) (cardHeight * scale * 2);
        double xcoord = (getWidth() - newCardWidth) / 2.0 + (getWidth() * 0.35);
        double ycoord = (getHeight() - newCardHeight) / 2.0;
        RoundRectangle2D roundedCard = new RoundRectangle2D.Double(xcoord, ycoord, newCardWidth, newCardHeight, 30, 30);
        g2d.setClip(roundedCard);
        g2d.drawImage(cardBufferedImage, (int) xcoord, (int) ycoord, newCardWidth, newCardHeight, null);
    }

    public BufferedImage convertToBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return bufferedImage;
    }
  }
}  