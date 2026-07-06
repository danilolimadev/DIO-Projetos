package org.example.model;

public class Space {
    private Integer actual;
    private final int expected;
    private final boolean fixed;

    public Space(int expectedValue, boolean fixed) {
        this.expected = expectedValue;
        this.fixed = fixed;
        if (fixed) {
            this.actual = expectedValue;
        }
    }

    public Integer getActual(int i) {
        return actual;
    }

    public void setActual(final Integer actual) {
        if(fixed) return;
        this.actual = actual;
    }

    public void clearSpace() {
        setActual(null);
    }

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }


}
