import javax.swing.JFrame;

import controllers.GameController;
import views.CardView;
import views.GameView;
import views.MainMenu;

public class Main{
    public static void main(String [] args){
        GameController gc = new GameController();
        MainMenu mainMenu = new MainMenu(gc);
        GameView board = new GameView(gc);

        gc.add(mainMenu);
        gc.showMainMenu();
        gc.add(board);
    }
}
