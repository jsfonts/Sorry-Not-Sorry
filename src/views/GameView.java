package views;

import javax.swing.*;
import java.io.File;
import controllers.GameController;
import java.awt.*;
import java.awt.event.ActionListener;
import models.Board;

public class GameView extends JFrame {
    private JLabel gameLabel;
    private JMenuItem restartMenuItem;
    private JMenuItem newGameMenuItem;
    private JMenuItem pauseMenuItem;
    private JMenuItem quitMenuItem;
    private JMenuItem rulesMenuItem;
    private GameController controller;
    private Dimension computerScreenSize;

    public GameView(GameController controller) {
        this.controller = controller;
        setTitle("Sorry Not Sorry!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        computerScreenSize = getToolkit().getScreenSize();
        setSize(computerScreenSize);    //Fullscreen!

        gameLabel = new JLabel("", SwingConstants.CENTER);
        gameLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        add(gameLabel, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenu helpMenu = new JMenu("?");

        restartMenuItem = new JMenuItem("Restart");
        newGameMenuItem = new JMenuItem("New Game");
        pauseMenuItem = new JMenuItem("Pause");
        quitMenuItem = new JMenuItem("Quit");
        rulesMenuItem = new JMenuItem("Rules");

        gameMenu.add(restartMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(newGameMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(pauseMenuItem);
        gameMenu.addSeparator();
        gameMenu.add(quitMenuItem);

        helpMenu.add(rulesMenuItem);
        menuBar.add(helpMenu);
        menuBar.add(gameMenu);

        setJMenuBar(menuBar);
        setVisible(false);
    }

    public void setGameLabel(String text) {
        gameLabel.setText(text);
    }

    public void addRestartListener(ActionListener listener) {
        restartMenuItem.addActionListener(listener);
    }

    public void addNewGameListener(ActionListener listener) {
        newGameMenuItem.addActionListener(listener);
    }

    public void addPauseListener(ActionListener listener) {
        pauseMenuItem.addActionListener(listener);
    }

    public void addQuitListener(ActionListener listener) {
        quitMenuItem.addActionListener(listener);
    }

    public void addRulesListener(ActionListener listener) {
        rulesMenuItem.addActionListener(listener);
    }

    public void showRules() {
        JEditorPane editorPane = new JEditorPane();

        try{
            System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
            File rulesHTML = new File("../resources/rules.html");
            editorPane.setPage(rulesHTML.toURI().toURL());
        }catch(Exception e){
            System.out.println("\nCould not find the file or could not convert it to a URL. Exception thrown:\n" + e);
        }
        
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setSize(computerScreenSize.width/2, computerScreenSize.height/2);

        JOptionPane.showMessageDialog(this, scrollPane, "Sorry! Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }
}
