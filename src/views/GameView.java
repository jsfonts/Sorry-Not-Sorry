package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import controllers.GameController;
import java.awt.*;
import java.awt.event.ActionListener;
import models.Board;

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
    }
    
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

    private static class GameBoardPanel extends JPanel {
        private Image gameBoardImage;
        private Rectangle logicalSquare;
    
        public GameBoardPanel(Image image) {
            this.gameBoardImage = image;
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
    
            if (gameBoardImage != null) {
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
    
                int squareSize = Math.min(newImgWidth / 2, newImgHeight / 2);
                int squareX = x + (newImgWidth - squareSize) / 2;
                int squareY = y + (newImgHeight - squareSize) / 2;
    
                logicalSquare = new Rectangle(squareX, squareY, squareSize, squareSize);
            }
        }
    }
}    