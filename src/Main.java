import controllers.GameController;
import views.GameBoard;
import views.MainMenu;

public class Main{
    public static void main(String [] args){
        GameController gc = new GameController();
        MainMenu mainMenu = new MainMenu(gc);
        GameBoard board = new GameBoard(gc);

        gc.add(mainMenu);
        gc.showMainMenu();
        gc.add(board);
    }
}
