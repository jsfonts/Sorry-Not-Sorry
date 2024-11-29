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
        List<String> playerNames = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            String playerName = JOptionPane.showInputDialog(this, "Enter name for Player " + i + ":");
            if (playerName == null || playerName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid name.");
                i--; 
            } else {
                playerNames.add(playerName.trim());
            }
        }
        //controller.start(playerNames);
    }
}
