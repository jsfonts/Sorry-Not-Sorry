import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Sorry!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

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
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPlayerSelection();
            }
        });

        continueGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSavedGame();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Player selection screen
    public void showPlayerSelection() {
        JFrame playerSelectionFrame = new JFrame("Select Players");
        playerSelectionFrame.setSize(400, 300);
        playerSelectionFrame.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel instruction = new JLabel("Select number of players:", SwingConstants.CENTER);
        instruction.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        instruction.setForeground(Color.WHITE);
        instruction.setOpaque(true);
        instruction.setBackground(new Color(227, 198, 73)); 
        instruction.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        playerSelectionFrame.add(instruction);

        JButton twoPlayersButton = new JButton("2 Players");
        JButton threePlayersButton = new JButton("3 Players");
        JButton fourPlayersButton = new JButton("4 Players");

        Font buttonFont = new Font("Verdana", Font.PLAIN, 18);
        twoPlayersButton.setFont(buttonFont);
        threePlayersButton.setFont(buttonFont);
        fourPlayersButton.setFont(buttonFont);

        // Add action listeners
        twoPlayersButton.addActionListener(e -> {showNameInputDialog(2); });
        threePlayersButton.addActionListener(e -> {showNameInputDialog(3);});
        fourPlayersButton.addActionListener(e -> {showNameInputDialog(4);});

        playerSelectionFrame.add(twoPlayersButton);
        playerSelectionFrame.add(threePlayersButton);
        playerSelectionFrame.add(fourPlayersButton);

        playerSelectionFrame.setLocationRelativeTo(this);
        playerSelectionFrame.setVisible(true);
    }

    // Name input dialog
    private void showNameInputDialog(int numPlayers) {
       // JOptionPane.showMessageDialog(null, "Please enter names for " + numPlayers + " players.");

        List<String> playerNames = new ArrayList<>();

        for (int i = 1; i <= numPlayers; i++) {
            String playerName = JOptionPane.showInputDialog(null, 
                    "Enter name for Player " + i + ":", 
                    "Player " + i + " Name", JOptionPane.QUESTION_MESSAGE);
                  
                    if (playerName == null) {
                        break;
                    }            

            if (playerName != null && !playerName.trim().isEmpty()) {
                playerNames.add(playerName.trim());
            } else {
                JOptionPane.showMessageDialog(null, "Name cannot be empty. Please enter a valid name for Player " + i + ".");
                i--; 
            }
        }

        if (!playerNames.isEmpty()) {
            startGame(playerNames);
        } else {
            JOptionPane.showMessageDialog(null, "Exiting game setup.");
        }
    }

    private void startGame(List<String> playerNames) {
        int numPlayers = playerNames.size();
        JOptionPane.showMessageDialog(null, "Starting new game with " + numPlayers + " players: " + playerNames);
        dispose();

        GameBoard gameBoard = new GameBoard(playerNames); 
        gameBoard.setVisible(true);
        }
 
    

    // Placeholder for loading a saved game
    private void loadSavedGame() {
        JOptionPane.showMessageDialog(null, "Loading saved game...");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu(); 
        });
    }}



