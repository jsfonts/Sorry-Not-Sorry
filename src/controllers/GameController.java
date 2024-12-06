package controllers;

import views.GameView;
import views.MainMenu;
import models.*;
import javax.swing.*;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GameController implements Serializable{
    private static Board board;  
    private transient GameView view;
    private transient MainMenu mainMenu;             
    private static GameController instance;
    public ArrayDeque<Player> players;
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
    private boolean seven11;
    private boolean justloaded = false;

    
    public GameController() {
        board = new Board(this);
        instance = this;
        /*view.addRestartListener(e -> restartGame());
        view.addNewGameListener(e -> startNewGame());
        view.addSaveGameListener(e -> saveGame());
        view.addQuitListener(e -> quitGame());
        view.addRulesListener(e -> showRules());*/
    }

    public static GameController getInstance(){     //was going to implement singleton pattern with this but ended up doing it by 
        if(instance == null)                        //manually passing controllers into everything
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

        if(justloaded)
        {
            turnDone = false;
            cardAlreadyDrawn = false;
            pickSecondPawn = false;
            secondSelectedPawn = null;
            turnMessage();
        }
        else
            nextPlayer();
        justloaded = false;
    }

    public boolean movePawn(Pawn selectedPawn, int spaces)
    {
        return board.movePawn(selectedPawn, spaces);
    }
    
    public boolean isValidMove(Pawn selectedPawn, int spaces)
    {
        return board.isValidMove(selectedPawn, spaces);
    }

    public void setValidPawns(ArrayList<Pawn> pawnsThatCanMove){
        if(!pawnsThatCanMove.isEmpty())
            view.highlightavailablePawns(pawnsThatCanMove);
    }

    public void pawnReachedHome(Pawn done){
        done.getTile().setPawnAt(null);
        for(Player p : players){
            if(p.getColor() == done.getColor()){
                p.removePawn(done);
                System.out.println("A \nPawn has reached HOME\n");
                if(p.pawnsLeft() == 0)
                    playerWon(p);
            }
        }
    }

    public void playerWon(Player p){
        if(p instanceof HumanPlayer)
            JOptionPane.showMessageDialog(null, " Congrats, " + p.getName() + ". you have won!", null, JOptionPane.INFORMATION_MESSAGE); 
        else
            JOptionPane.showMessageDialog(null, p.getName() + " has won! Better luck next time!", null, JOptionPane.INFORMATION_MESSAGE);  

       // board.restartGame();
        view.dispose();
        mainMenu = new MainMenu(this);
        showMainMenu();
    }

    public void swapPawns(Pawn p1, Pawn p2)
    {
        board.swapPawns(p1, p2);
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
            view.text((player.getName() + "'s turn. Click on the cards to draw a card. You are the color " + player.getColorString()), player.getColor());
          /* String message = player.getName() + "'s turn. Click on the cards to draw a card. You are the color " + player.getColorString();
            JLabel label = new JLabel(message);
            label.setOpaque(true);
            label.setBackground(player.getColor());
            if (player.getColor() != Color.YELLOW && player.getColor() != Color.GREEN) {
                label.setForeground(Color.WHITE);
            } else {
                label.setForeground(Color.BLACK);
            }
            JOptionPane.showMessageDialog(null, label, null, JOptionPane.INFORMATION_MESSAGE); */
        }
        else
        {
            view.text((player.getName() + "'s turn. They are the color "+ player.getColorString()), player.getColor());
            //String message = player.getName() + "'s turn. They are the color "+ player.getColorString();
            //JLabel label = new JLabel(message);
            //label.setOpaque(true);
            //label.setBackground(player.getColor());
           /*  if (player.getColor() != Color.YELLOW && player.getColor() != Color.GREEN) {
                label.setForeground(Color.WHITE);
            } else {
                label.setForeground(Color.BLACK);
            }
            JOptionPane.showMessageDialog(null, label, null, JOptionPane.INFORMATION_MESSAGE); */
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
        Card.CardType cardType = selectedCard.getType();
        System.out.println(cardType);

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

        else if (selectedPawn.getColor() != player.getColor() && (cardType != Card.CardType.ELEVEN || cardType != Card.CardType.SORRY))
        {
            ErrorMessageColor();
            System.out.println("Wrong color pawn");
            return;
        }
        else if (cardType == Card.CardType.ONE)
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
                    invalidMoveSelected = true;
                }

        }
        else if (cardType == Card.CardType.TWO)
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
            if(secondTurn)
                turnDone = true;
            else{
                secondTurn = true;
                view.text((player.getName() + ", draw again") , player.getColor());
            }

        }
        else if (cardType == Card.CardType.THREE)
        {
            if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, 3)){
                board.movePawn(selectedPawn, 3);
                turnDone = true;
            }
            else{
                invalidMoveSelected = true;
            }
        }
        else if(cardType == Card.CardType.FOUR)
        {
            if(selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, -4)){
                board.movePawn(selectedPawn, -4);
                turnDone = true;
            }
            else{
                invalidMoveSelected = true;
            }
        }
        else if(cardType == Card.CardType.FIVE)
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
        else if(cardType == Card.CardType.SEVEN)
        {
            Tile original = selectedPawn.getTile(); //to use if they pick an invalid split 
            Pawn pawnAtOriginal;
            seven11 = true;

            if(pawnsOutOfStart() == 1 && selectedPawn.getTile().getType() != Tile.TType.START){
                board.movePawn(selectedPawn, 7);
                turnDone = true;
            }
            else if(pickSecondPawn){    //they already selected the second pawn to move
                board.movePawn(selectedPawn, 7 - remainder);
                view.repaint();
                if(board.isValidMove(secondSelectedPawn, remainder)){
                    board.movePawn(secondSelectedPawn, remainder);
                    turnDone = true;
                    view.repaint();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Second selected pawn cannot move " + remainder + "spaces", null, JOptionPane.INFORMATION_MESSAGE);
                    selectedPawn.setLocation(original, remainder);
                    pickSecondPawn = false;
                    view.repaint();
                }
            }
            else{

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

                if(selectedOption == 6)
                {
                    board.movePawn(selectedPawn, 7);
                    turnDone = true;
                }
                else // add check to see if the split is valid
                {
                    // allow them to click another pawn and then move that pawn the remainder of the spaces
                    if(!board.isValidMove(p, selectedOption+1)){
                        JOptionPane.showMessageDialog(null, "That pawn cannot move " + (selectedOption+1) + " spaces", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        remainder = 6 - selectedOption;
                        JOptionPane.showMessageDialog(null, "Select another pawn of your own color to move " + remainder + " spaces");
                        pickSecondPawn = true;
                    }
                }
            }

        }
        else if(cardType == Card.CardType.EIGHT)
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
        else if(cardType == Card.CardType.TEN)
        {
            if(selectedPawn.getTile().getType() != Tile.TType.START && isValidMove(selectedPawn, 10)){
                board.movePawn(selectedPawn, -1);
                turnDone = true;
            }
            else{
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
        }
        else if(cardType == Card.CardType.ELEVEN)
        {
            seven11 = true;
            if(pawnsOutOfStart() == 1 && selectedPawn.getTile().getType() != Tile.TType.START && board.isValidMove(selectedPawn, 11)){
                board.movePawn(selectedPawn, 11);
                turnDone = true;
            }
            else if(pickSecondPawn == true && secondSelectedPawn != null){ 
                System.out.println("Second pawn was picked");
                Tile.TType sP = secondSelectedPawn.getTile().getType();
                
                if(secondSelectedPawn.getColor() == selectedPawn.getColor()){
                    invalidMoveSelected = true;
                    JOptionPane.showMessageDialog(null, "Cannot swap with your own pawn", "Invalid Pawn for Swap", JOptionPane.INFORMATION_MESSAGE);
                }
                else if(sP == Tile.TType.ENDZONE || sP == Tile.TType.START || sP == Tile.TType.ENDZONE_FIRST){
                    invalidMoveSelected = true;
                    JOptionPane.showMessageDialog(null, "That pawn is in a safezone", "Invalid Pawn for Swap", JOptionPane.INFORMATION_MESSAGE);
                }
                else{   //valid move
                    board.swapPawns(selectedPawn, secondSelectedPawn);
                    turnDone = true;
                }
            }
            else{

                //wait for a click
                String [] options = new String [2];
                options[0] = "11";
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
                    if(selectedPawn.getTile().getType() == Tile.TType.START || selectedPawn.getTile().getType() == Tile.TType.ENDZONE || selectedPawn.getTile().getType() == Tile.TType.ENDZONE_FIRST)
                        invalidMoveSelected = true;
                    else {
                        pickSecondPawn = true;
                        JOptionPane.showMessageDialog(null, player.getName() + ", choose opponent's pawn to swap with");
                    }
                }
            }
        }
        else if(cardType == Card.CardType.TWELVE)
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
        else if(cardType == Card.CardType.SORRY)
        {
            //can only use it to switch if you have a pawn in the start zone
            //switch the pawn with an opponents
            if(!pickSecondPawn){
                if(selectedPawn.getTile().getType() == Tile.TType.START){
                    pickSecondPawn = true;
                    JOptionPane.showMessageDialog(null, "Pick opponent to swap with", null, JOptionPane.INFORMATION_MESSAGE);
                }   
                else 
                    invalidMoveSelected = true;
            }
            else{ 
                Tile.TType sP = secondSelectedPawn.getTile().getType();
                if(sP == Tile.TType.ENDZONE || sP == Tile.TType.HOME || sP == Tile.TType.ENDZONE_FIRST || secondSelectedPawn.getColor() == selectedPawn.getColor()){
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
        if(invalidMoveSelected && !seven11)
            invalidMoveMessage();

        if(turnDone){       //if valid move was selected
            selectedCard = null;
            cardAlreadyDrawn = false;
            selectedPawn = null;
            secondSelectedPawn = null;
            secondTurn = false;
            seven11 = false;
            view.newTurnCard();
            nextPlayer();
        }
        else if(secondTurn && !seven11){
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
        String message = "That pawn cannot be moved with the " + selectedCard.toString() + " card";
        JOptionPane.showMessageDialog(null, message, "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
    }

    public void drawCard(){
        if (cardAlreadyDrawn == false){
            cardAlreadyDrawn = true;
            selectedCard = deck.drawCard();
            view.updateCard(selectedCard);
            if(!player.hasValidMoves(selectedCard) && !(player instanceof ComputerPlayer)) 
            {
                JOptionPane.showMessageDialog(null, "No available moves for this card", null, JOptionPane.INFORMATION_MESSAGE);
                view.newTurnCard();
                nextPlayer();
                return;
            }
        }
        
    }

    public boolean getComputer()
    {
        if(player instanceof ComputerPlayer)
            return true;
        return false;
    }

    public int pawnsOutOfStart(){
        int i = 0;
        for(Pawn p : player.getPawns()){
            if(p.getColor() == player.getColor() && p.getTile().getType() != Tile.TType.START)
                ++i;
        }

        System.out.println(i + " pawns were out of start");

        return i;
    }

    public void updateWinners(Player winner){
        this.winner = winner;
    }

    private void restartGame() {
        board.restartGame();
        JOptionPane.showMessageDialog(view, "Game Restarted!");
        view.repaint();
        player = players.getFirst(); 
        turnDone = false;
        cardAlreadyDrawn = false;
        pickSecondPawn = false;
        secondSelectedPawn = null;
        turnMessage();
    }

    private Map<String, Object> collectGameData() {
        Map<String, Object> gameData = new HashMap<>();
        gameData.put("players", players);
        gameData.put("deck", deck);
        gameData.put("board", board);
        gameData.put("currentPlayer", player);
        gameData.put("selectedCard", selectedCard);
        return gameData;
    }

    public void saveGame() {
        Map<String, Object> gameData = collectGameData();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame.dat"))) {
            oos.writeObject(gameData);
            JOptionPane.showMessageDialog(view, "Game saved successfully!");
            System.out.println("Saved Game Data: " + gameData);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to save the game.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public Map<String, Object> loadSavedGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            return (Map<String, Object>) ois.readObject();  
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void loadGame() {
        Map<String, Object> gameData = loadSavedGame();
        if (gameData != null) {
    
            Object playersObj = gameData.get("players");
            if (playersObj instanceof ArrayDeque<?>) {
                players = (ArrayDeque<Player>) playersObj;
            } else {
                JOptionPane.showMessageDialog(view, "Error loading players.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            board = (Board) gameData.get("board");
            deck = (Deck) gameData.get("deck");
            player = (Player) gameData.get("currentPlayer");
            selectedCard = (Card) gameData.get("selectedCard");
    
            if (players == null || board == null || deck == null || player == null) {
                JOptionPane.showMessageDialog(view, "Error loading game data.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            if (instance == null) {
                instance = new GameController(); 
            }
            
            Player.setController(instance);
    
            // Reset player colors
            resetPlayerColors(Player.getOriginalPlayers());
    
            JOptionPane.showMessageDialog(view, "Game loaded successfully!");
            System.out.print(player.getColor());
            justloaded = true;
            run();

        } else {
            JOptionPane.showMessageDialog(view, "No saved game found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetPlayerColors(ArrayList<Player> playersList) {
        ArrayList<Color> fixedColors = new ArrayList<>(Arrays.asList(Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN));
        
        for (int i = 0; i < playersList.size(); i++) {
            Player player = playersList.get(i);
            Color newColor = fixedColors.get(i % fixedColors.size());
            for(Player p : players){
                if(p.getName().equals(player.getName()))
                    p.setColor(newColor);
            }
        }
    }


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
        board = new Board(this);
        deck = new Deck();
        winner = null;
        selectedCard = null;
        Pawn.reset();

        System.out.println(numComputerPlayers);

        for (String name : humanPlayerNames) {
            Player in = new HumanPlayer(name, this);
            players.add(in);
            Player.setPlayers(in);
        }
        
        for (int i = 1; i <= numComputerPlayers; i++) {
            Player in = new ComputerPlayer(this);
            players.add(in);
            Player.setPlayers(in);
        }

        // need help here bc unsure
       /* String playerList = String.join(", ", getPlayerNames());
        view.setGameLabel("Game Starting with Players: " + playerList); */

       // view.setGameLabel("Game Starting with Players: ");
            
        JOptionPane.showMessageDialog(null, "Game has started with " + players.size() + " players.");

        run();
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