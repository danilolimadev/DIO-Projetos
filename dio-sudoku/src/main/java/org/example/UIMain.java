package org.example;

import org.example.ui.custom.frame.MainFrame;
import org.example.ui.custom.panel.MainPanel;
import org.example.ui.custom.screen.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UIMain {


    public static void main(String[] args) {
        System.out.println("Welcome to Sudoku!");
        final var gameConfig = Stream.of(args)
                .collect(Collectors.toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));
        var mainScreen = new MainScreen(gameConfig);


        var dimension = new Dimension(600, 600);
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
