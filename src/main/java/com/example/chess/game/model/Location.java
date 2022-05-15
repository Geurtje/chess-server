package com.example.chess.game.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Location {

    public final int x;
    public final int y;

    @Override
    public String toString() {
        return String.format("%c%s", getColumnLetter(), getRowNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x &&
                y == location.y;
    }

    private char getColumnLetter() {
        switch (x) {
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5:
                return 'F';
            case 6:
                return 'G';
            case 7:
                return 'H';
            default:
                return '?';
        }
    }

    private String getRowNumber() {
        return String.valueOf(y+1);
    }

    /**
     *
     * @param locationText A chess position such as "A1", "B5", "D8"
     * @return A location that refers to the x and y coordinates of that location on the board.
     * @throws IllegalArgumentException
     */
    public static Location fromText(String locationText) throws IllegalArgumentException {
        if (locationText.length() != 2) {
            throw new IllegalArgumentException("location text length should be 2 (e.g. B3)");
        }

        int x = parseColumnText(locationText.charAt(0));
        int y = parseRowNumber(locationText.charAt(1));

        return new Location(x, y);
    }

    private static int parseColumnText(char c) {
        switch (c) {
            case 'A': case 'a':
                return 0;
            case 'B': case 'b':
                return 1;
            case 'C': case 'c':
                return 2;
            case 'D': case 'd':
                return 3;
            case 'E': case 'e':
                return 4;
            case 'F': case 'f':
                return 5;
            case 'G': case 'g':
                return 6;
            case 'H': case 'h':
                return 7;
        }

        throw new IllegalArgumentException("Unknown row letter '" + c + "'");
    }

    private static int parseRowNumber(char c) {
        int columnNumber;
        try {
            columnNumber = Integer.parseInt(String.valueOf(c));
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Column number '" + c + "' appears to not be a number");
        }

        if (columnNumber < 1 || columnNumber > 8) {
            throw new IllegalArgumentException("Column number '" + c + "', out of bounds, value must be between 1 and 8");
        }

        // columns are indexed from 0, so deduct one
        return columnNumber-1;
    }
}
