package controllers;

import views.GameView;
import views.MainMenu;
import models.*;
import javax.swing.*;
import java.awt.Color;

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
    private Player player;
    
    public GameController() {
        board = new Board();
        /*view.addRestartListener(e -> restartGame());
        view.addNewGameListener(e -> startNewGame());
        view.addSaveGameListener(e -> saveGame());
        view.addQuitListener(e -> quitGame());
        view.addRulesListener(e -> showRules());*/
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

        nextPlayer();
    }

    private void nextPlayer(){
        player = players.removeFirst();
        players.addLast(player);
    }

    public void doTurn(){
        cardAlreadyDrawn = false;
        //view.updateCurrentPlayer(player);

        if(selectedCard != null)
            player.move(selectedCard);
        System.out.println(selectedCard);
        
        if(player instanceof HumanPlayer)
        {
            String message = player.getName() + "'s turn. Click on the cards to draw a card. You are the color " + player.getColorString();
            JLabel label = new JLabel(message);
            label.setForeground(player.getColor() == Color.YELLOW ? new Color(150, 120, 12) : player.getColor());
            JOptionPane.showMessageDialog(null, label, null, JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            String message = player.getName() + "'s turn. They are the color "+ player.getColorString();
            JLabel label = new JLabel(message);
            label.setForeground(player.getColor());
            JOptionPane.showMessageDialog(null, label, null, JOptionPane.INFORMATION_MESSAGE);
        }
        //if the space they are clicking on is not their pawn tell them to click on their own pawn else 

        if (selectedCard.getType() == Card.CardType.ONE)
        {
            // if the pawn is not one of their own call: ErrorMessage(player);
            // if the pawn selected is in the Start move it out of start if its not move the pawn one space
        }
        else if (selectedCard.getType() == Card.CardType.TWO)
        {
            //if the pawn selected is in start move it once out of start else move it two spaces
        }
        else if (selectedCard.getType() == Card.CardType.THREE)
        {
            //move three forward
        }
        else if(selectedCard.getType() == Card.CardType.FOUR)
        {
            //move four backward
        }
        else if(selectedCard.getType() == Card.CardType.FIVE)
        {
            //move 5 spaces forward 
        }
        else if(selectedCard.getType() == Card.CardType.SEVEN)
        {
            //first they click on their pawn
            String [] options = new String [7];
            for (int i = 0; i < 7; i++) {
                options[i] = String.valueOf(i + 1);
            }
        
            int selectedOption = JOptionPane.showOptionDialog(
                null,
                "How many spaces would you like to move this pawn?",
                "7 card pawn selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            //selected option is 0 indexed
            if (selectedOption != 6)
            {
                // allow them to click another pawn and then move that pawn the remainder of the spaces
                int remainder = 7 - selectedOption;
                System.out.println(remainder);
            }
            else
            {
                //move the pawn 7 spaces and go to the next player
            }

        }
        else if(selectedCard.getType() == Card.CardType.EIGHT)
        {
            // move the selected pawn 8 forward
        }
        else if(selectedCard.getType() == Card.CardType.TEN)
        {
            //first they click on one of their own pawns
            String [] options = new String [2];
            options[0] = String.valueOf(1);
            options[1] = String.valueOf(10);

            int selectedOption = JOptionPane.showOptionDialog(
                null,
                "Would you like to move the Pawn 1 space or 10 spaces?",
                "10 card pawn selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            
            if (selectedOption == 0)
            {
                //move 1 space
            }
            else if (selectedOption == 1)
            {
                //move 10 spaces. if not able to move 10 spaces default to one 
            }
        }
        else if(selectedCard.getType() == Card.CardType.ELEVEN)
        {
            //wait for a click
            String [] options = new String [2];
            options[0] = String.valueOf(11);
            options[1] = "Switch";

            int selectedOption = JOptionPane.showOptionDialog(
                null,
                "Would you like to switch with another players pawn or move your pawn 11 spaces?",
                "11 card pawn selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (selectedOption == 0)
            {
                //move 11 spaces
            } 
            else if (selectedOption == 1)
            {
                //switch logic allow them to click on the opponents pawn they want to switch with or vice versa
            }
            
        }
        else if(selectedCard.getType() == Card.CardType.TWELVE)
        {
            // move 12 spaces forward
        }
        else if(selectedCard.getType() == Card.CardType.SORRY)
        {
            //switch the pawn with an opponents
        }


        //make sure they have selected a card

        //reset selected card
        selectedCard = null;
        cardAlreadyDrawn = false;
    }

    public void ErrorMessage(Player player){
        String message = "Please click on one of your own pawns. Your color is " + player.getColorString();;
        JOptionPane.showMessageDialog(null, message, null, JOptionPane.INFORMATION_MESSAGE);
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
        Pawn.reset();
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
        board = new Board();
        view = new GameView(this); 
        view.addRestartListener(e -> restartGame());
        view.addNewGameListener(e -> startNewGame());
        view.addSaveGameListener(e -> saveGame());
        view.addQuitListener(e -> quitGame());
        view.addRulesListener(e -> showRules());

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
        Pawn.reset();
    }

    private void showRules() {
        view.showRules();
    }

}
