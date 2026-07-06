package org.example.model;

public enum GameStatusEnum {
    NON_STARTED,
    INCOMPLETE,
    COMPLETE;

    private String label;
    private String status;

    private GameStatusEnum(final String label) {
        this.label = label;
    }

    GameStatusEnum() {

    }

    public String getGameStatus() {
        return status;
    }

    public String getLabel() {
        return label;
    }
}
