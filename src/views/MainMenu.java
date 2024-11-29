package views;

import controllers.GameController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends JFrame {
    private GameController controller;

    public MainMenu(GameController controller) {
        this.controller = controller;
        setTitle("Sorry!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true); 

        Dimension screenSize = getToolkit().getScreenSize();
        setSize((int)(screenSize.width/1.5), (int)(screenSize.height/1.5));

        // Title
        JLabel title = new JLabel("Sorry Not Sorry!", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD + Font.ITALIC, 45));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        title.setOpaque(true);
        title.setBackground(new Color(219, 86, 83));
        add(title, BorderLayout.NORTH);

        // Buttons
        JButton newGameButton = new JButton("Start New Game");
        JButton continueGameButton = new JButton("Continue Saved Game");

        Font buttonFont = new Font("Verdana", Font.PLAIN, 18);
        newGameButton.setFont(buttonFont);
        continueGameButton.setFont(buttonFont);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        buttonPanel.setBackground(new Color(219, 86, 83));
        buttonPanel.add(newGameButton);
        buttonPanel.add(continueGameButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Button actions
        newGameButton.addActionListener(e -> showPlayerSelection());
        continueGameButton.addActionListener(e -> controller.loadSavedGame());

    }

    public void showPlayerSelection() {
        getContentPane().removeAll();
        repaint();
    
        JLabel title = new JLabel("How Many Human Players?", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD + Font.ITALIC, 45));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        title.setOpaque(true);
        title.setBackground(new Color(252, 173, 3));
        add(title, BorderLayout.NORTH);


        JButton onePlayerButton = new JButton ("1 Human Players");
        JButton twoPlayersButton = new JButton("2 Human Players");
        JButton threePlayersButton = new JButton("3 Human Players");
        JButton fourPlayersButton = new JButton("4 Human Players");
    
        Font buttonFont = new Font("Verdana", Font.PLAIN, 18);
        onePlayerButton.setFont(buttonFont);
        twoPlayersButton.setFont(buttonFont);
        threePlayersButton.setFont(buttonFont);
        fourPlayersButton.setFont(buttonFont);
    
        onePlayerButton.addActionListener(e -> showNameInputDialog(1));
        twoPlayersButton.addActionListener(e -> showNameInputDialog(2));
        threePlayersButton.addActionListener(e -> showNameInputDialog(3));
        fourPlayersButton.addActionListener(e -> showNameInputDialog(4));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20,100, 20, 100));
        buttonPanel.setBackground(new Color(252, 173, 3));
        buttonPanel.add(onePlayerButton);
        buttonPanel.add(twoPlayersButton);
        buttonPanel.add(threePlayersButton);
        buttonPanel.add(fourPlayersButton);
    
        add(buttonPanel, BorderLayout.CENTER);
    
        revalidate();
        repaint();
    }
    
    // Name input dialog
    private void showNameInputDialog(int numPlayers) {
        ArrayList<String> playerNames = new ArrayList<>();
    
        for (int i = 1; i <= numPlayers; i++) {
            while (true) {
                String playerName = JOptionPane.showInputDialog(
                    null, 
                    "Enter name for Player " + i + ":", 
                    "Player " + i + " Name", 
                    JOptionPane.QUESTION_MESSAGE
                );
    
                if (playerName == null) {  
                    return;
                }
    
                if (!playerName.trim().isEmpty()) {
                    playerNames.add(playerName.trim());
                    break; 
                } else {
                    JOptionPane.showMessageDialog(
                        null, 
                        "Invalid name. Please try again.", 
                        "Input Error", 
                        JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        }
            if (numPlayers < 4) {
                getContentPane().removeAll();
                repaint();
        
                JLabel title = new JLabel("Would You Like to Add Computer Players?", SwingConstants.CENTER);
                title.setFont(new Font("Times New Roman", Font.BOLD + Font.ITALIC, 45));
                title.setForeground(Color.WHITE);
                title.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
                title.setOpaque(true);
                title.setBackground(new Color(68, 137, 194));
                add(title, BorderLayout.NORTH);
        
                JRadioButton yesButton = new JRadioButton("Yes");
                JRadioButton noButton = new JRadioButton("No");
                Font buttonFont = new Font("Verdana", Font.PLAIN, 18);
                yesButton.setFont(buttonFont);
                noButton.setFont(buttonFont);
        
                yesButton.addActionListener(e -> showComputerNumPlayers(numPlayers, playerNames));
        
                if (numPlayers == 1) {
                    noButton.addActionListener(e -> {
                        JOptionPane.showMessageDialog(
                            null,
                            "You must select at least one computer player. Two players are required for the game.",
                            "Player Requirement",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                            
                        showComputerNumPlayers(numPlayers, playerNames);
                        noButton.setEnabled(false);
                        });
                    
                } else {
                    noButton.addActionListener(e -> controller.start(playerNames, 0));
                }
        
                JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 300, 20, 300));
                buttonPanel.setBackground(new Color(68, 137, 194));
                buttonPanel.add(yesButton);
                buttonPanel.add(noButton);
                add(buttonPanel, BorderLayout.CENTER);
        
                revalidate();
                repaint();
            } else if (numPlayers == 4) {
                controller.start(playerNames, 0);
            }
        }
        
        private void showComputerNumPlayers(int numPlayers, ArrayList<String> playerNames) {
            int maxComputerPlayers = 4 - numPlayers;
            String[] options = new String[maxComputerPlayers];
            for (int i = 0; i < maxComputerPlayers; i++) {
                options[i] = String.valueOf(i + 1);
            }
        
            int selectedOption = JOptionPane.showOptionDialog(
                null,
                "How many computer players would you like?",
                "Computer Player Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
        
            if (selectedOption == -1) {
                System.out.println("Selection canceled.");
        
                if (numPlayers == 1) {
                    JOptionPane.showMessageDialog(
                        null,
                        "You must select at least one computer player. Two players are required for the game.",
                        "Player Requirement",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    showComputerNumPlayers(numPlayers, playerNames);
                } else {
                    controller.start(playerNames, 0);
                }
            } else {
                int numComputerPlayers = selectedOption + 1;
                System.out.println("You selected " + numComputerPlayers + " computer players.");
                controller.start(playerNames, numComputerPlayers);
            }
        }
}
        