package com.example.chess.game.model;

import com.example.chess.piece.Piece;

/**
 * Utility to make it possible in text form to describe pieces on a chess board.
 * This is intended to be used in unit testing to more easily create starting states to verify behaviour.
 *
 * As input a string is expected that matches the following criteria:
 * - Ascii characters that represent various chess pieces, as per https://www.alt-codes.net/chess-symbols.php
 * - Rows are separated by newline characters
 * - Empty positions can be represented with an underscore
 *
 *
 * Example input string for a fresh board:
 *         String input =
 *                 "♜♞♝♛♚♝♞♜\n" + // row 8
 *                 "♟♟♟♟♟♟♟♟\n" +
 *                 "________\n" +
 *                 "________\n" +
 *                 "________\n" +
 *                 "________\n" +
 *                 "♙♙♙♙♙♙♙♙\n" +
 *                 "♖♘♗♕♔♗♘♖"; // row 1
 *
 * See the tests in {@link TestBoardBuilderTest} for more example boards.
 *
 * Passing an input of lines with varying lenghts is not (intentionally) supported and will probably lead to errors.
 */
public class TestBoardBuilder {

    public static final String STARTING_BOARD =
            "♜♞♝♛♚♝♞♜\n" +
            "♟♟♟♟♟♟♟♟\n" +
            "________\n" +
            "________\n" +
            "________\n" +
            "________\n" +
            "♙♙♙♙♙♙♙♙\n" +
            "♖♘♗♕♔♗♘♖";

    public static Piece[][] buildBoard(String input) {
        String[] inputRows = input.split("\n");


        int rowCount = inputRows.length;
        Piece[][] board = new Piece[rowCount][];

        for (int i = 0; i < board.length; i++) {
            // because i like complicated stuff the way boards are visualized the y axis is counting up,
            // so the first row we interpret is actually black's starting row (row 8), so we have to do some offsetting here

            Piece[] pieceRow = inputRows[i].chars()
                    .mapToObj(c -> (char) c)
                    .map(TestBoardBuilder::interpretCharacter)
                    .toArray(Piece[]::new);
            board[rowCount-1-i] = pieceRow;
        }

        return board;
    }

    private static Piece interpretCharacter(char c) {
        switch (c) {
            case '♙':
                return Piece.WHITE_PAWN;
            case '♟':
                return Piece.BLACK_PAWN;
            case '♖':
                return Piece.WHITE_ROOK;
            case '♜':
                return Piece.BLACK_ROOK;
            case '♘':
                return Piece.WHITE_KNIGHT;
            case '♞':
                return Piece.BLACK_KNIGHT;
            case '♗':
                return Piece.WHITE_BISHOP;
            case '♝':
                return Piece.BLACK_BISHOP;
            case '♕':
                return Piece.WHITE_QUEEN;
            case '♛':
                return Piece.BLACK_QUEEN;
            case '♔':
                return Piece.WHITE_KING;
            case '♚':
                return Piece.BLACK_KING;
            case '_': default:
                return null;
        }
    }

}
