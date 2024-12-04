package views;

import models.Player;
import models.Tile;
import models.Pawn;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import models.Card;
import controllers.GameController;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class GameView extends JFrame {
    private GameBoardPanel gameBoardPanel;
    private JPanel overlayPanel;
    private JMenuItem restartMenuItem;
    private JMenuItem newGameMenuItem;
    private JMenuItem SaveMenuItem;
    private JMenuItem quitMenuItem;
    private JMenuItem rulesMenuItem;
    private GameController controller;
    private Dimension computerScreenSize;

    private Image gameBoardImage;

    //for renderingthe board
    private static int PAWN_SIZE;
    private static int BORDER;
    private static int PAWN_OFFSET; 
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

        addMouseListener(new ClickHandler());
      
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

    private class GameBoardPanel extends JPanel {
        private Image gameBoardImage;
    
        public GameBoardPanel(Image image) {
            this.gameBoardImage = image;
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
    
            if (gameBoardImage == null)
                return;
            
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int imgWidth = gameBoardImage.getWidth(this);
            int imgHeight = gameBoardImage.getHeight(this);

            double scale = Math.min((double) panelWidth / imgWidth, (double) panelHeight / imgHeight);

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

            Graphics2D g2d = (Graphics2D)g;
            g2d.drawRect(grid_x, grid_y, gridW, gridH);

            for(Pawn pawn : controller.getPawns()){
                int pawnX = (int)(grid_x + pawn.getCoords()[1] * cellW + cellW/2);
                int pawnY = (int)(grid_y + pawn.getCoords()[0] * cellH + cellH/2);

                g.setColor(pawn.getColor());
                g.fillOval(pawnX - PAWN_SIZE/2, pawnY - PAWN_SIZE/2, PAWN_SIZE, PAWN_SIZE);
                
                //Graphics2D g2d = (Graphics2D)g;
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(PAWN_SIZE/12));
                g2d.drawOval(pawnX - PAWN_SIZE/2, pawnY - PAWN_SIZE/2, PAWN_SIZE, PAWN_SIZE);
            }
        }
    }   //end GameBoardPanel

    private class ClickHandler extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e){
            ArrayList<Player> players = new ArrayList<Player>(controller.getPlayers());
            int clickX = e.getX();
            int clickY = e.getY();
            Pawn selectedPawn;

            for(Pawn p : controller.getPawns()){
                if(containsPoint(p, clickX, clickY)){
                    selectedPawn = p;
                    
                    break;
                }
            }
            
            //controller.setSelectedPawn(selectedPawn);
        }

        private boolean containsPoint(Pawn p, int clickX, int clickY){
            boolean contains = false;
    
            int pawnY = (int)(grid_y + p.getCoords()[0] * cellH + cellH*3/2);
            int pawnX = (int)(grid_x + p.getCoords()[1] * cellW);

            System.out.println("Scaled pawn position: (" + pawnX + ", " + pawnY + ")");
            System.out.println("Click position: (" + clickX + ", " + clickY + ")");
            System.out.println("Pawn size: " + PAWN_SIZE);
            System.out.println();
            
            if(clickX >= pawnX && clickX <=  pawnX + PAWN_SIZE && clickY >= pawnY && clickY <= pawnY + PAWN_SIZE){
                contains = true;
                System.out.println("\nThis pawn was clicked\n");
            }

            return contains;
        }
 
    }
}