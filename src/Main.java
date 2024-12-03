import controllers.GameController;
import views.MainMenu;
import models.BackgroundMusicPlayer;

public class Main {
    public static void main(String[] args) {
        
        GameController gc = new GameController();
        MainMenu mainMenu = new MainMenu(gc);
        mainMenu.setLocationRelativeTo(null);

        gc.add(mainMenu);
        
        String musicFilePath = "../resources/BackgroundMusic.wav";
        BackgroundMusicPlayer musicPlayer = new BackgroundMusicPlayer();
        musicPlayer.play(musicFilePath); 
    }
}
