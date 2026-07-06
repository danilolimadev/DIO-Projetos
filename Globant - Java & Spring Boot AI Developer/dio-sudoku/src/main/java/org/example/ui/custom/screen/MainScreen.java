package org.example.ui.custom.screen;

import org.example.service.BoardService;
import org.example.ui.custom.button.CheckGameStatusButton;
import org.example.ui.custom.button.FinishGameButton;
import org.example.ui.custom.button.ResetButton;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static javax.swing.JOptionPane.*;
import static org.example.model.GameStatusEnum.*;

public class MainScreen {
    private static final Dimension DIMENSION = new Dimension(600, 600);

    private final BoardService boardService;

    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetButton;

    public MainScreen(Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen() {
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(DIMENSION);

        JFrame mainFrame = new JFrame("Sudoku 9x9");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);

        addResetButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);

        mainFrame.setVisible(true);
    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if (boardService.gameIsFinished()) {
                showMessageDialog(
                        mainPanel,
                        "The game is already finished.",
                        "Game Finished",
                        INFORMATION_MESSAGE
                );
                return;
            }

            int result = showConfirmDialog(
                    mainPanel,
                    "Are you sure you want to finish the game?",
                    "Finish Game",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
            );

            if (result == YES_OPTION) {
                boardService.finishGame();
            }
        });

        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(JPanel mainPanel) {
        checkGameStatusButton = new CheckGameStatusButton(e -> {
            var status = boardService.getStatus();

            var message = switch (status) {
                case NON_STARTED -> "The game has not started yet.";
                case INCOMPLETE -> "The game is incomplete or has errors.";
                case COMPLETE -> "Congratulations! You completed the game!";
            };

            showMessageDialog(
                    mainPanel,
                    message,
                    "Game Status",
                    INFORMATION_MESSAGE
            );
        });

        mainPanel.add(checkGameStatusButton);
    }

    private void addResetButton(JPanel mainPanel) {
        resetButton = new ResetButton(e -> {
            int result = showConfirmDialog(
                    mainPanel,
                    "Are you sure you want to reset the game?",
                    "Reset Game",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
            );

            if (result == YES_OPTION) {
                boardService.reset();
            }
        });

        mainPanel.add(resetButton);
    }
}
