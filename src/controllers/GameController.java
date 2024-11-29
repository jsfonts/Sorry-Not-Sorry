package controllers;

import models.Board;
import views.GameView;
import views.MainMenu;
import models.Player;
import models.HumanPlayer;
import models.Deck;


import javax.swing.*;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class GameController {
    private Board board;                //model
    private static GameView view;       //this is the rules page
    private static MainMenu mainMenu;
    private static GameController instance;
    private ArrayDeque<Player> players;
    private Deck deck;    //this is the board
    
    public GameController() {
        view = new GameView(this);
                //view.setGameLabel("Game Starting with Players: " + String.join(", ", board.getPlayerNames()));
        view.addRestartListener(e -> restartGame());
        view.addNewGameListener(e -> startNewGame());
        view.addPauseListener(e -> togglePause());
        view.addQuitListener(e -> quitGame());
        view.addRulesListener(e -> showRules());
        view.setVisible(false);
    }

    public void add(GameView b){
        view = b;
    }

    public void add(MainMenu mm){
        mainMenu = mm;
    }

    public void add(Board b){
        board = b;
    }

    public void run() {
        if (board == null) {
            JOptionPane.showMessageDialog(null, "No game initialized. Please start a new game from the Main Menu.");
            return;
        }
        view.setVisible(true);
    }

    private void restartGame() {
        board.restartGame();
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

    public void start(ArrayList<String> playerNames) {
        for (String name : playerNames) {
            players.add(new HumanPlayer(name));
        }
        deck = new Deck(); 
            
        JOptionPane.showMessageDialog(null, "Game has started with " + playerNames.size() + " players.");
        run();
    }

    public void loadSavedGame()
    {

    }

    public void showMainMenu(){
        view.setVisible(false);
        mainMenu.setVisible(true);
    }

    public void showPlayerSelection(){
        mainMenu.showPlayerSelection();
    }

    public ArrayList<String> getPlayerNames(){
        ArrayList<String> names = new ArrayList<String>();

        for(Player p : players){
            names.add(p.getName());
        }
        
        return names;
    }

    private void togglePause() {
        board.togglePause();
        String message = board.isPaused() ? "Game Paused!" : "Game Resumed!";
        JOptionPane.showMessageDialog(view, message);
    }

    private void quitGame() {
        int result = JOptionPane.showConfirmDialog(view, "Are you sure you want to quit?",
                "Quit Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            //view.dispose();
            showMainMenu();
        }
    }

    private void showRules() {
        view.showRules();
    }
/*
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        Board model = new Board(players);
        GameView view = new GameView(model);
        GameController controller = GameController.getInstance(model, view);

        SwingUtilities.invokeLater(() -> new MainMenu(controller));
    }
    */
}
