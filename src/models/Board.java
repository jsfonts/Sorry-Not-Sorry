package models;
import java.util.List;
public class Board{
    private boolean isPaused = false;
    private List<String> playerNames;

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

}