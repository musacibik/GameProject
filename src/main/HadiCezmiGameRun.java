package main;

import javax.swing.JFrame;

import main.ui.GameWindow;

/**
 *
 * @author JusticeLeague
 */

public final class HadiCezmiGameRun {
    public static final void main(String[] args) {
        GameWindow window = GameWindow.getInstance();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
    }
}
