package controllers;

import views.GameView;
import views.MainMenu;
import models.*;
import javax.swing.*;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class GameController {
    private Board board;                //model
    private static GameView view;       
    private static MainMenu mainMenu;
    private static GameController instance;
    private ArrayDeque<Player> players;
    private Player winner;
    private Deck deck;    //this is the board
    private Card selectedCard;
    private boolean cardAlreadyDrawn;
    private Pawn selectedPawn;
    
    public GameController() {
        board = new Board();
        view = new GameView(this);
        view.addRestartListener(e -> restartGame());
        view.addNewGameListener(e -> startNewGame());
        view.addSaveGameListener(e -> saveGame());
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

        Player nextPlayer;
        while(winner == null)
        {
            nextPlayer = players.removeFirst();
            players.addLast(nextPlayer);
            doTurn(nextPlayer);
        }
    }

    private void doTurn(Player player){
        cardAlreadyDrawn = false;
        //view.updateCurrentPlayer(player);

        if(selectedCard != null)
            player.move(selectedCard);
        
        if(player instanceof HumanPlayer)
        {
            String message = player.getName() + "'s turn. Click on the cards to draw a card.";
            JLabel label = new JLabel(message);
            label.setForeground(player.getColor());
            JOptionPane.showMessageDialog(null, label, null, JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            String message = player.getName() + "'s turn.";
            JLabel label = new JLabel(message);
            label.setForeground(player.getColor());
            JOptionPane.showMessageDialog(null, label, null, JOptionPane.INFORMATION_MESSAGE);
        }

        //make sure they have selected a card

        //reset selected card
        selectedCard = null;
        cardAlreadyDrawn = false;
    }

    public void drawCard(){
        if (cardAlreadyDrawn == false){
            cardAlreadyDrawn = true;
            selectedCard = deck.drawCard();
            view.updateCard(selectedCard);
        }
    }

    public void cardSelected( ){

    }

    public void updateWinners(Player winner){
        this.winner = winner;
    }

    private void restartGame() {
        board.restartGame();
        JOptionPane.showMessageDialog(view, "Game Restarted!");
    }

    public void saveGame()
    {

    }
    //not working for some reason?
    public void startNewGame() {
        int result = JOptionPane.showConfirmDialog(view, "Are you sure you want to start a new game?",
                "New Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            view.dispose();
            mainMenu = new MainMenu(this); 
            mainMenu.showPlayerSelection();

        }
    }

    public void start(ArrayList<String> humanPlayerNames, int numComputerPlayers) {
        players = new ArrayDeque<Player>();
        deck = new Deck();
        winner = null;
        selectedCard = null;

        System.out.println(numComputerPlayers);

        for (String name : humanPlayerNames) {
            players.add(new HumanPlayer(name));
        }
        
        for (int i = 1; i <= numComputerPlayers; i++) {
            players.add(new ComputerPlayer("Computer " + i));
        }

        // need help here bc unsure
       /* String playerList = String.join(", ", getPlayerNames());
        view.setGameLabel("Game Starting with Players: " + playerList); */

       // view.setGameLabel("Game Starting with Players: ");
            
        JOptionPane.showMessageDialog(null, "Game has started with " + players.size() + " players.");

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

    public ArrayDeque<Player> getPlayers(){
        return players;
    }

    public void setSelectedPawn(Pawn p){
        selectedPawn = p;
    }

    public ArrayList<String> getPlayerNames(){
        ArrayList<String> names = new ArrayList<String>();

        for(Player p : players){
            names.add(p.getName());
        }
        
        return names;
    }

    public ArrayList<Pawn> getPawns(){
        ArrayList<Pawn> pawns = new ArrayList<Pawn>();
        
        for(Player p : players){
            pawns.addAll(p.getPawns());
        }
        
        return pawns;
    }

    private void quitGame() {
        int result = JOptionPane.showConfirmDialog(view, "Are you sure you want to quit?",
                "Quit Game", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            //view.dispose();
            mainMenu = new MainMenu(this);      //reset to main menu screen
            showMainMenu();
        }
    }

    private void showRules() {
        view.showRules();
    }

}
