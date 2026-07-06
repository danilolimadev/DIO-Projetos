package org.example.service;

import org.example.board.Board;
import org.example.model.GameStatusEnum;
import org.example.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardService {

    private static final int BOARD_LIMIT = 9 ;
    private Board board;

    public List<List<Space>> getBoardSpaces() {
        return board.getSpaces();
    }

    public String getGameStatus() {
        return board.getStatus().name();
    }

    public BoardService(final Map<String, String> gameConfig) {
        board = new Board(initBoard(gameConfig));
    }

    public void reset(final Map<String, String> gameConfig) {
        board.reset();
    }
    public void reset() {
        board.reset();
    }

    public boolean hasErrors() {
        return board.hasErrors();
    }

    public GameStatusEnum getStatus() {
        return board.getStatus();
    }

    public boolean gameIsFinished() {

        return board.gameIsFinished();
    }

    private List<List<Space>> initBoard(Map<String, String> gameConfig) {

        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = gameConfig.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        return spaces;
    }

    public void finishGame() {
    }
}
