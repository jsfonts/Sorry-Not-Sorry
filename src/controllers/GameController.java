package controllers;

import models.Board;
import views.GameView;
import views.MainMenu;
import models.Player;
import models.Deck;
import views.GameBoard;
import java.awt.Color;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class GameController {
    private Board model;
    private GameView view;
    private static GameController instance;
    private ArrayDeque<Player> players;
    private Deck deck;
    public static GameBoard gameboard;
    private List<Color> colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};

    private GameController(Board model, GameView view) {
        this.model = model;
        this.view = view;

        view.setGameLabel("Game Starting with Players: " + String.join(", ", model.getPlayerNames()));
        view.addRestartListener(e -> restartGame());
        view.addNewGameListener(e -> startNewGame());
        view.addPauseListener(e -> togglePause());
        view.addQuitListener(e -> quitGame());
        view.addRulesListener(e -> showRules());
    }

    public static GameController getInstance(Board model, GameView view) {
        if (instance == null) {
            instance = new GameController(model, view);
        }
        return instance;
    }

    public void run() {
        if (model == null) {
            JOptionPane.showMessageDialog(null, "No game initialized. Please start a new game from the Main Menu.");
            return;
        }
        view.setVisible(true);
    }

    private void restartGame() {
        model.restartGame();
        JOptionPane.showMessageDialog(view, "Game Restarted!");
    }

    public void startNewGame() {
        int result = JOptionPane.showConfirmDialog(view, "Are you sure you want to start a new game?",
                "New Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            view.dispose();
            new MainMenu(this).showPlayerSelection();
        }
    }

    public void start(List<String> playerNames) {
        if (playerNames == null || playerNames.size() < 2) {
            JOptionPane.showMessageDialog(null, "At least 2 players are required to start the game.");
            return; 
        }

        List<Player> players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
        deck = new Deck(); 
        deck.reshuffle();

        GameView gameView = new GameView(players);
        gameView.setVisible(true);
            
        JOptionPane.showMessageDialog(null, "Game has started with " + playerNames.size() + " players.");
    }

    public void loadSavedGame()
    {

    }


    private void togglePause() {
        model.togglePause();
        String message = model.isPaused() ? "Game Paused!" : "Game Resumed!";
        JOptionPane.showMessageDialog(view, message);
    }

    private void quitGame() {
        int result = JOptionPane.showConfirmDialog(view, "Are you sure you want to quit?",
                "Quit Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            view.dispose();
            new MainMenu(this);
        }
    }

    private void showRules() {
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
            "<li><b>7:</b> Move a pawn forward seven spaces or split seven spaces between two pawns.</li>" +
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
        view.showRules(rules);
    }

    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        Board model = new Board(players);
        GameView view = new GameView(model);
        GameController controller = GameController.getInstance(model, view);

        SwingUtilities.invokeLater(() -> new MainMenu(controller));
    }

    public void startGame(List<Player> selectedPlayers) {
        model = new Board(selectedPlayers);
        view = new GameView(model);
        run();
    }
}
