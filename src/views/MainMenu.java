package views;

import controllers.GameController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends JFrame {
    private GameController controller;

    public MainMenu(GameController controller) {
        this.controller = controller;
        setTitle("Sorry!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());
        setVisible(true);
        setLocationRelativeTo(null);

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
        // Clear existing components from the frame
        getContentPane().removeAll();
        repaint();
    
        // Title
        JLabel title = new JLabel("How Many Players?", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD + Font.ITALIC, 45));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        title.setOpaque(true);
        title.setBackground(new Color(252, 173, 3));
        add(title, BorderLayout.NORTH);
    
        // Buttons for player selection
        JButton twoPlayersButton = new JButton("2 Players");
        JButton threePlayersButton = new JButton("3 Players");
        JButton fourPlayersButton = new JButton("4 Players");
    
        Font buttonFont = new Font("Verdana", Font.PLAIN, 18);
        twoPlayersButton.setFont(buttonFont);
        threePlayersButton.setFont(buttonFont);
        fourPlayersButton.setFont(buttonFont);
    
        // Add action listeners
        twoPlayersButton.addActionListener(e -> showNameInputDialog(2));
        threePlayersButton.addActionListener(e -> showNameInputDialog(3));
        fourPlayersButton.addActionListener(e -> showNameInputDialog(4));
    
        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        buttonPanel.setBackground(new Color(252, 173, 3));
        buttonPanel.add(twoPlayersButton);
        buttonPanel.add(threePlayersButton);
        buttonPanel.add(fourPlayersButton);
    
        add(buttonPanel, BorderLayout.CENTER);
    
        // Refresh frame to show updated content
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
          // Start game with collected player names
          controller.start(playerNames);

    }
}

    