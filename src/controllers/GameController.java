package controllers;

import views.GameView;
import views.MainMenu;
import models.*;
import javax.swing.*;
import java.awt.Color;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class GameController {
    private static Board board;                //model
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
    private boolean invalidMoveSelected;
    private int remainder;      //for the 7 Card
    private Pawn secondSelectedPawn;
    private boolean pickSecondPawn;

    
    public GameController() {
        board = new Board();
        instance = this;
        /*view.addRestartListener(e -> restartGame());
        view.addNewGameListener(e -> startNewGame());
        view.addSaveGameListener(e -> saveGame());
        view.addQuitListener(e -> quitGame());
        view.addRulesListener(e -> showRules());*/
    }

    public static GameController getInstance(){
        if(instance == null)
            instance = new GameController();
        
        return instance;
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

    public boolean movePawn(Pawn selectedPawn, int spaces)
    {
        return board.movePawn(selectedPawn, spaces);
    }
    
    public boolean isValidMove(Pawn selectedPawn, int spaces)
    {
        return board.isValidMove(selectedPawn, spaces);
    }
    public void movePawn(Pawn p1, int spaces1, Pawn p2, int spaces2)
    {
        board.movePawn(p1, spaces1, p2, spaces2);
    }

    private void nextPlayer(){
        player = players.removeFirst();
        players.addLast(player);
        turnDone = false;
        cardAlreadyDrawn = false;
        pickSecondPawn = false;
        secondSelectedPawn = null;
        turnMessage();

        //if the space they are clicking on is not their pawn tell them to click on their own pawn else 
    }

    public void turnMessage()
    {
        if(player instanceof HumanPlayer)
        {
            String message = player.getName() + "'s turn. Click on the cards to draw a card. You are the color " + player.getColorString();
            JLabel label = new JLabel(message);
            label.setOpaque(true);
            label.setBackground(player.getColor());
            if (player.getColor() != Color.YELLOW) {
                label.setForeground(Color.WHITE);
            } else {
                label.setForeground(Color.BLACK);
            }
            JOptionPane.showMessageDialog(null, label, null, JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            String message = player.getName() + "'s turn. They are the color "+ player.getColorString();
            JLabel label = new JLabel(message);
            label.setOpaque(true);
            label.setBackground(player.getColor());
            if (player.getColor() != Color.YELLOW) {
                label.setForeground(Color.WHITE);
            } else {
                label.setForeground(Color.BLACK);
            }
            JOptionPane.showMessageDialog(null, label, null, JOptionPane.INFORMATION_MESSAGE);
            drawCard();
            Timer timer = new Timer(2000, e -> { 
                player.move(selectedCard);
                turnDone = true;  
                if (turnDone) { 
                    selectedCard = null;
                    cardAlreadyDrawn = false;
                    view.newTurnCard();
                    nextPlayer();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
    public void doTurn(Pawn p){
        if(pickSecondPawn){
            secondSelectedPawn = p;
        }
        else 
            selectedPawn = p;

        invalidMoveSelected = false;

    
        if(cardAlreadyDrawn == false){
            return;
        }
        //view.updateCurrentPlayer(player);
    
        if(player instanceof ComputerPlayer)
        {
            return;
        }
        else if (selectedPawn.getColor() != player.getColor() && (selectedCard.getType() != Card.CardType.ELEVEN || selectedCard.getType() != Card.CardType.SORRY))
        {
            ErrorMessageColor();
            System.out.println("Wrong color pawn");
            return;
        }
        else if (selectedCard.getType() == Card.CardType.ONE)
        {
            // if the pawn is not one of their own call: ErrorMessage(player);
            // if the pawn selected is in the Start move it out of start if its not move the pawn one space
                System.out.println("Card type is one");
                if(board.isValidMove(selectedPawn, 1)){
                    board.movePawn(selectedPawn, 1);
                    turnDone = true;
                }
                else{
                    System.out.println("Cant move with one card bc something is there");
                }

        }
        else if (selectedCard.getType() == Card.CardType.TWO)
        {
            //if the pawn selected is in start move it once out of start else move it two spaces
            if(selectedPawn.getTile().getType() == Tile.TType.START && board.isValidMove(selectedPawn, 1)){
                board.movePawn(selectedPawn, 1);
            }
            else if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, 2)){
                board.movePawn(selectedPawn, 2);
            }
            else {
                invalidMoveSelected = true;
            }

            //draw one more time
            if(secondTurn){
                turnDone = true;
            }
            else
                secondTurn = true;

        }
        else if (selectedCard.getType() == Card.CardType.THREE)
        {
            if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, 3)){
                board.movePawn(selectedPawn, 3);
                turnDone = true;
            }
            else{
                invalidMoveSelected = true;
            }
        }
        else if(selectedCard.getType() == Card.CardType.FOUR)
        {
            if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, -4)){
                board.movePawn(selectedPawn, -4);
                turnDone = true;
            }
            else{
                invalidMoveSelected = true;
            }
        }
        else if(selectedCard.getType() == Card.CardType.FIVE)
        {
            //move 5 spaces forward 
            if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, 5)){
                board.movePawn(selectedPawn, 5);
                turnDone = true;
            }
            else{
                invalidMoveSelected = true;
            }
        }
        else if(selectedCard.getType() == Card.CardType.SEVEN)
        {
            if(pickSecondPawn){ //they already selected the second pawn to move
                board.movePawn(secondSelectedPawn, remainder);
                turnDone = true;
                pickSecondPawn = false;
            }

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

            if (selectedOption != 6)
            {
                // allow them to click another pawn and then move that pawn the remainder of the spaces
                int remainder = (6 - selectedOption) + 1;
                System.out.println(remainder);
            }
            else
            {
                board.movePawn(selectedPawn, 7);
                turnDone = true;
            }

        }
        else if(selectedCard.getType() == Card.CardType.EIGHT)
        {
            // move the selected pawn 8 forward
            if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, 8)){
                board.movePawn(selectedPawn, 8);
                turnDone = true;
            }
            else{
                invalidMoveSelected = true;
            }
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

            boolean cantMove10 = false;
            
            if (selectedOption == 1)
            {
                //move 10 spaces. if not able to move 10 spaces default to one 
                if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, 10)){
                    board.movePawn(selectedPawn, 10);
                    turnDone = true;
                }
                else{
                    cantMove10 = true;
                }
            }
            else if (selectedOption == 0 || cantMove10)
            {
                //move 1 space
                if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, -1)){
                    board.movePawn(selectedPawn, -1);
                    turnDone = true;
                }
                else{
                    invalidMoveSelected = true;
                }
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
                //move forward 11 spaces
                if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, 11)){
                    board.movePawn(selectedPawn, 11);
                    turnDone = true;
                }
                else{
                    invalidMoveSelected = true;
                }
            } 
            else if (selectedOption == 1)
            {
                //switch logic allow them to click on the opponents pawn they want to switch with or vice versa
                if(selectedPawn.getTile().getType() == Tile.TType.START)
                pickSecondPawn = true;
            else 
                invalidMoveSelected = true;
                if(pickSecondPawn == true && secondSelectedPawn != null){ 
                Tile.TType sP = secondSelectedPawn.getTile().getType();
                if(sP == Tile.TType.ENDZONE || sP == Tile.TType.ENDZONE || sP == Tile.TType.ENDZONE_FIRST){
                    invalidMoveSelected = true;
                }
                else{   //valid move
                    Tile temp = selectedPawn.getTile();
                    selectedPawn.setLocation(secondSelectedPawn.getTile(), 0);
                    secondSelectedPawn.setLocation(temp, 0);
                    turnDone = true;
                }
            }
            }
            
        }
        else if(selectedCard.getType() == Card.CardType.TWELVE)
        {
            // move 12 spaces forward
            if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, 12)){
                board.movePawn(selectedPawn, 12);
                turnDone = true;
            }
            else{
                invalidMoveSelected = true;
            }
        }
        else if(selectedCard.getType() == Card.CardType.SORRY)
        {
            //switch the pawn with an opponents
            if(selectedPawn.getTile().getType() == Tile.TType.START)
                pickSecondPawn = true;
            else 
                invalidMoveSelected = true;
            
                if(pickSecondPawn == true && secondSelectedPawn != null){ 
                Tile.TType sP = secondSelectedPawn.getTile().getType();
                if(sP == Tile.TType.ENDZONE || sP == Tile.TType.ENDZONE || sP == Tile.TType.ENDZONE_FIRST){
                    invalidMoveSelected = true;
                }
                else{   //valid move
                    Tile temp = selectedPawn.getTile();
                    selectedPawn.setLocation(secondSelectedPawn.getTile(), 0);
                    secondSelectedPawn.setLocation(temp, 0);
                    turnDone = true;
                }
            }
        }

        //make sure they have selected a card
        if(invalidMoveSelected)
            invalidMoveMessage();

        if(invalidMoveSelected)
            invalidMoveMessage();

        if(turnDone){       //if valid move was selected
            //reset selected card
            System.out.println("turn is over, view should be updated    ");
            selectedCard = null;
            cardAlreadyDrawn = false;
            secondTurn = false;
            view.newTurnCard();
            nextPlayer();
        }
        else if(secondTurn == true){
            System.out.println("second turn is now. view update");
            selectedCard = null;
            cardAlreadyDrawn = false;
            view.newTurnCard();
        }
    }

    public void ErrorMessageColor(){
        String message = "Please click on one of your OWN pawns. Your color is " + player.getColorString();;
        JOptionPane.showMessageDialog(null, message, null, JOptionPane.INFORMATION_MESSAGE);
    }

    public void ErrorMessage(){
        String message = "Please click on the pawn you would like to move. Your color is " + player.getColorString();;
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

        if(!player.hasValidMoves(selectedCard)) 
            turnDone = true;
            System.out.println("Player has no valid moves");
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
    }

    private void showRules() {
        view.showRules();
    }

}