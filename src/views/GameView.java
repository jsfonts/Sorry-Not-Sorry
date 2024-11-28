package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class GameView extends JFrame {
    private JLabel gameLabel;
    private JMenuItem restartMenuItem;
    private JMenuItem newGameMenuItem;
    private JMenuItem pauseMenuItem;
    private JMenuItem quitMenuItem;
    private JMenuItem rulesMenuItem;

    public GameView() {
        setTitle("Game Board");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gameLabel = new JLabel("", SwingConstants.CENTER);
        gameLabel.setFont(new Font("Arial", Font.BOLD, 24));
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
        setLocationRelativeTo(null);
        setVisible(true);
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

    public void showRules(String rules) {
        JEditorPane editorPane = new JEditorPane("text/html", rules);
        editorPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Sorry! Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }
}
