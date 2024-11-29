import controllers.GameController;
import views.MainMenu;

public class Main{
    public static void main(String [] args){
        GameController gc = new GameController();
        MainMenu mainMenu = new MainMenu(gc);
        mainMenu.setLocationRelativeTo(null);

        gc.add(mainMenu);
    }
}
