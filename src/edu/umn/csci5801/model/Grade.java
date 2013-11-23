package edu.umn.csci5801.model;

public enum Grade {
    A, B, C, D, F, S, N, _;

    public double numericValue() {
        return this.ordinal() >= 4 ? 0.0 : 4.0 - this.ordinal();
    }
}
