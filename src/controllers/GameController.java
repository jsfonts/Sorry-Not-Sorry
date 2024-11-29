package controllers;

import models.Board;
import models.ComputerPlayer;
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
    private static GameView view;       
    private static MainMenu mainMenu;
    private static GameController instance;
    private ArrayDeque<Player> players;
    private Deck deck;    //this is the board
    
    public GameController() {
        board = new Board();
        view = new GameView(this);
        view.addRestartListener(e -> restartGame());
        view.addNewGameListener(e -> startNewGame());
        view.addPauseListener(e -> togglePause());
        view.addQuitListener(e -> quitGame());
        view.addRulesListener(e -> showRules());
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
        showGameBoard();
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

    public void start(ArrayList<String> humanPlayerNames) {
        players = new ArrayDeque<Player>();
        deck = new Deck();

        for (String name : humanPlayerNames) {
            players.add(new HumanPlayer(name));
        }

        view.setGameLabel("Game Starting with Players: " + String.join(", ", getPlayerNames()));
            
        JOptionPane.showMessageDialog(null, "Game has started with " + players.size() + " players.");

        //adding computer players
        while(players.size() < 4)
            players.add(new ComputerPlayer());

        run();
    }

    public void loadSavedGame()
    {

    }

    public void showMainMenu(){
        view.setVisible(false);
        mainMenu.setVisible(true);
    }

    public void showGameBoard(){
        view.setVisible(true);
        mainMenu.setVisible(false); 
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
