package controllers;

import views.GameView;
import views.MainMenu;
import models.*;
import javax.swing.*;
import java.awt.*;

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
    private boolean turnDone;
    private boolean secondTurn; //mainly for drawing twos
    
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
        turnDone = false;
        cardAlreadyDrawn = false;
    }

    public void doTurn(Pawn p){
        selectedPawn = p;
        //view.updateCurrentPlayer(player);
        
        if(player instanceof HumanPlayer)
        {
            String message = player.getName() + "'s turn. Click on the cards to draw a card. You are the color " + player.getColorString();
            JLabel label = new JLabel(message);
            label.setFont(new Font("Times New Roman",Font.PLAIN, 20));
            label.setBackground(player.getColor());
            label.setOpaque(true);
            if (player.getColor() != Color.YELLOW)
            label.setForeground(Color.WHITE);   
            JOptionPane.showMessageDialog(null, label, null, JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            String message = player.getName() + "'s turn. They are the color "+ player.getColorString();
            JLabel label = new JLabel(message);
            label.setFont(new Font("Times New Roman",Font.PLAIN, 20));
            label.setBackground(player.getColor());
            label.setOpaque(true);
            if (player.getColor() != Color.YELLOW)
                label.setForeground(Color.WHITE);
            JOptionPane.showMessageDialog(null, label, null, JOptionPane.INFORMATION_MESSAGE);
        }
        //if the space they are clicking on is not their pawn tell them to click on their own pawn else 

        if (selectedCard.getType() == Card.CardType.ONE)
        {
            // if the pawn is not one of their own call: ErrorMessage();
            // if the pawn selected is in the Start move it out of start if its not move the pawn one space
                System.out.println("Card type is one");
                if(board.isValidMove(selectedPawn, 1)){
                    board.movePawn(selectedPawn, 1);
                    turnDone = true;
                }
                else{
                    invalidMoveMessage();
                    System.out.println("Cant move with one card bc something is there");
                }

        }
        else if (selectedCard.getType() == Card.CardType.TWO)
        {
            //if the pawn selected is in start move it once out of start else move it two spaces
            if(selectedPawn.getTile().getType() == Tile.TType.START && board.isValidMove(selectedPawn, 1)){
                board.movePawn(selectedPawn, 1);
                secondTurn = true;
            }
            else if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, 2)){
                board.movePawn(selectedPawn, 2);
                secondTurn = true;
            }
            else 
                invalidMoveMessage();

            //draw one more time
            if(secondTurn){
                turnDone = true;
                secondTurn = false;
            }

        }
        else if (selectedCard.getType() == Card.CardType.THREE)
        {
            if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, 3)){
                board.movePawn(selectedPawn, 3);
                turnDone = true;
            }
            else 
                invalidMoveMessage();
            //move three forward
        }
        else if(selectedCard.getType() == Card.CardType.FOUR)
        {
            if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, -4)){
                board.movePawn(selectedPawn, -4);
                turnDone = true;
            }
            else
                ErrorMessage();
        }
        else if(selectedCard.getType() == Card.CardType.FIVE)
        {
            //move 5 spaces forward 
            if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, 5)){
                board.movePawn(selectedPawn, 5);
                turnDone = true;
            }
            else
                ErrorMessage();
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
                // if the pawn is not one of their own call: ErrorMessage(player);
                // if the pawn selected is in the Start move it out of start if its not move the pawn one space
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
            else if(selectedCard.getType() == Card.CardType.TWELVE)
            {
                // move 12 spaces forward
            }

      //  else 
       // {
            if(selectedCard.getType() == Card.CardType.ELEVEN)
            {
                //wait for a click
              //  if //(pawn is their own )
               // {
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
                //else if //(pawn clicked is someone else)

            else if(selectedCard.getType() == Card.CardType.SORRY)
            {
                //switch the pawn with an opponents
            }
        //}
            //make sure they have selected a card

        if(turnDone){       //if valid move was selected
            //reset selected card
            selectedCard = null;
            cardAlreadyDrawn = false;
            view.repaint();
            nextPlayer();
        }
    }

    public void ErrorMessage(){
        String message = "Please click on one of your own pawns. Your color is " + player.getColorString();;
        JOptionPane.showMessageDialog(null, message, null, JOptionPane.INFORMATION_MESSAGE);
    }

    public void invalidMoveMessage(){
        String message = "That pawn cannot be moved with the " + selectedCard.toString() + " Card. Select a valid pawn.";;
        JOptionPane.showMessageDialog(null, message, "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
    }

    public void drawCard(){
        if (cardAlreadyDrawn == false){
            cardAlreadyDrawn = true;
            selectedCard = deck.drawCard();
            view.updateCard(selectedCard);

           // if(!hasValidMoves()) 
            //    turnDone = true;
        }
    }
/*
    private boolean hasValidMoves(){
        boolean valid = false;
        Tile original = selectedPawn.getTile();
        Card.CardType type = selectedCard.getType();
        for(Pawn p : player.getPawns()){
            if(type == Card.CardType.ONE){
                if()
                //if it can move by actually moving it
                //reset the move
            }
            else if(type == Card.CardType.TWO){
                if()
            }
            else if(type == Card.CardType.THREE){

            }
            else if(type == Card.CardType.FOUR)
                
            //if pawn can move there
        }
            

        return valid;
    } */

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
        cardAlreadyDrawn = false;
    }

    public void start(ArrayList<String> humanPlayerNames, int numComputerPlayers) {
        players = new ArrayDeque<Player>();
        deck = new Deck();
        winner = null;
        selectedCard = null;

        System.out.println(numComputerPlayers);

        for (String name : humanPlayerNames) {
            players.add(new HumanPlayer(name, this));
        }
        
        for (int i = 1; i <= numComputerPlayers; i++) {
            players.add(new ComputerPlayer(this));
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
        cardAlreadyDrawn = false;
    }

    private void showRules() {
        view.showRules();
    }

}