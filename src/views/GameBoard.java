import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameBoard extends JFrame {
    private JPanel gameBoardPanel;
    private boolean isPaused = false; 
    private List<String> playerNames;

    public GameBoard(List<String> playerNames) {
        this.playerNames = playerNames;

        setTitle("Game Board");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String playerNamesDisplay = String.join(", ", playerNames);
        JLabel gameLabel = new JLabel("Game Starting with Players: " + playerNamesDisplay, SwingConstants.CENTER);
        gameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(gameLabel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem restartMenuItem = new JMenuItem("Restart");
        JMenuItem newGameMenuItem = new JMenuItem("New Game");
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem quitMenuItem = new JMenuItem("Quit");

        JMenu helpMenu = new JMenu("?");
        JMenuItem rules = new JMenuItem("Rules");



        // Add action listeners to the menu items
        restartMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        newGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        pauseMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });

        quitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitGame();
            }
        });

        rules.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e){
                Rules();
            }
        });

        // Add items to the menu
        gameMenu.add(restartMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(newGameMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(pauseMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(quitMenuItem);

        helpMenu.add(rules);

        // Add menu to the menu bar
        menuBar.add(helpMenu);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);

        // Game board panel
        gameBoardPanel = new JPanel();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void restartGame() {
        JOptionPane.showMessageDialog(this, "Game Restarted!");

    }

    private void startNewGame() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to start a new game?", 
            "New Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            dispose();
        MainMenu mainMenu = new MainMenu(); 
        mainMenu.showPlayerSelection(); 
    }
}

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            JOptionPane.showMessageDialog(this, "Game Paused!");
        } else {
            JOptionPane.showMessageDialog(this, "Game Resumed!");
        }
    }

    private void quitGame() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", 
            "Quit Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            new MainMenu();
            dispose();
        }
    }

    private void Rules(){

          String rules = "<html>" +
            "<h2>Objective:</h2>" +
            "<p>The goal of Sorry! is to be the first player to move all four of your pawns from \"Start\" to \"Home\".</p>" +

            "<h2>Gameplay:</h2>" +
            "<p><b>Turn Sequence:</b> Players take turns in clockwise order.</p>" +

            "<h2>Card Drawing:</h2>" +
            "<p>On their turn, a player draws a card from the deck and follows the instructions on the card.</p>" +

            "<h2>Card Rules:</h2>" +
            "<ul>" +
            "<li><b>1:</b> Start a pawn from \"Start\" or move a pawn forward one space.</li>" +
            "<li><b>2:</b> Move a pawn forward two spaces or start a new pawn from \"Start\". Draw again after playing this card.</li>" +
            "<li><b>3:</b> Move a pawn forward three spaces.</li>" +
            "<li><b>4:</b> Move a pawn backward four spaces.</li>" +
            "<li><b>5:</b> Move a pawn forward five spaces.</li>" +
            "<li><b>7:</b> Split seven spaces between two pawns.</li>" +
            "<li><b>8:</b> Move a pawn forward eight spaces.</li>" +
            "<li><b>10:</b> Move a pawn forward ten spaces or backward one space.</li>" +
            "<li><b>11:</b> Move a pawn forward eleven spaces or swap places with an opponent's pawn.</li>" +
            "<li><b>12:</b> Move a pawn forward twelve spaces.</li>" +
            "<li><b>Sorry!:</b> Take one of your pawns from \"Start\" and place it on any space occupied by an opponent's pawn.</li>" +
            "</ul>" +

            "<h2>Slide:</h2>" +
            "<p>Move your pawn to the start of any slide and \"slide\" ahead to the last space of the slide.</p>" +

            "<h2>Bumping:</h2>" +
            "<p>If a pawn lands on a space occupied by an opponent's pawn (excluding Safe spaces), the opponent's pawn is bumped back to \"Start\".</p>" +

            "<h2>Safety Zones:</h2>" +
            "<p>Certain spaces on the board are considered \"Safe\" and cannot be bumped.</p>" +

            "<h2>Safety Cards:</h2>" +
            "<p>If a player draws a \"Sorry!\" card but cannot legally move any pawns, the player forfeits their turn.</p>" +

            "<h2>Winning:</h2>" +
            "<p>The first player to move all four of their pawns from \"Start\" to \"Home\" wins the game.</p>" +

            "<h2>End of Game:</h2>" +
            "<p>The game ends immediately when one player successfully moves all four of their pawns \"Home\".</p>" +
            "</html>";

    JEditorPane editorPane = new JEditorPane("text/html", rules);
    editorPane.setEditable(false); 

    JScrollPane scrollPane = new JScrollPane(editorPane);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setPreferredSize(new java.awt.Dimension(500, 400)); 

    JOptionPane.showMessageDialog(null, scrollPane, "Sorry! Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard gameBoard = new GameBoard(List.of("Player 1", "Player 2","Player 3","Player 4"));
        });
    }
}
