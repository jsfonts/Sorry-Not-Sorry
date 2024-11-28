package models;

import java.util.List;

public class Board{
    private boolean isPaused = false;
    private List<String> playerNames;
    private Tile greenStart;
    private Tile redStart;
    private Tile blueStart;
    private Tile yellowStart;

    public class Tile{
        enum BoardSpace{
            HOME, EMPTY, ENDZONE, SLIDE_START, SLIDE_END;
        }

        private Tile* prev;
        private Tile* next;

    }

    public Board(){
        //initialize all the tiles
    }

    public Board(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public void restartGame() {
       
    }

    public void startNewGame() {
 
    }

    public void quitGame() {
    
    }

    

    private ArrayList<>
}