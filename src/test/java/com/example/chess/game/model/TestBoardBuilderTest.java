package com.example.chess.game.model;

import com.example.chess.piece.Piece;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;

import static com.example.chess.piece.Piece.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TestBoardBuilderTest {

    @Test
    public void TestBoardBuilder_newBoard() {
        String input =
                "♜♞♝♛♚♝♞♜\n" +
                "♟♟♟♟♟♟♟♟\n" +
                "________\n" +
                "________\n" +
                "________\n" +
                "________\n" +
                "♙♙♙♙♙♙♙♙\n" +
                "♖♘♗♕♔♗♘♖";

        Piece[][] board = TestBoardBuilder.buildBoard(input);
        assertThat(board).hasSameDimensionsAs(new Piece[8][8]);

        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(board[0]).as("Row 1").containsExactly(Piece.WHITE_ROOK, Piece.WHITE_KNIGHT, Piece.WHITE_BISHOP, Piece.WHITE_QUEEN, WHITE_KING, Piece.WHITE_BISHOP, Piece.WHITE_KNIGHT, Piece.WHITE_ROOK);
            softly.assertThat(board[1]).as("Row 2").containsOnly(WHITE_PAWN);
            softly.assertThat(board[2]).as("Row 3").containsOnlyNulls();
            softly.assertThat(board[3]).as("Row 4").containsOnlyNulls();
            softly.assertThat(board[4]).as("Row 5").containsOnlyNulls();
            softly.assertThat(board[5]).as("Row 6").containsOnlyNulls();
            softly.assertThat(board[6]).as("Row 7").containsOnly(Piece.BLACK_PAWN);
            softly.assertThat(board[7]).as("Row 8").containsExactly(Piece.BLACK_ROOK, Piece.BLACK_KNIGHT, Piece.BLACK_BISHOP, Piece.BLACK_QUEEN, Piece.BLACK_KING, Piece.BLACK_BISHOP, Piece.BLACK_KNIGHT, Piece.BLACK_ROOK);
        }
    }

    @Test
    public void TestBoardBuilder_complicatedBoard() {
        String input =
                        "♚♜___♖__\n" +
                        "__♝♞____\n" +
                        "♜_♟_____\n" +
                        "♟_♙__♛♙_\n" +
                        "♙♗♞____♟\n" +
                        "♖__♘_♕__\n" +
                        "___♔_♙__\n" +
                        "________";

        Piece[][] board = TestBoardBuilder.buildBoard(input);
        assertThat(board).hasSameDimensionsAs(new Piece[8][8]);

        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(board[0]).as("Row 1").containsOnlyNulls();
            softly.assertThat(board[1]).as("Row 2").containsExactly(null, null, null, WHITE_KING, null, WHITE_PAWN, null, null);
            softly.assertThat(board[2]).as("Row 3").containsExactly(WHITE_ROOK, null, null, WHITE_KNIGHT, null, WHITE_QUEEN, null, null);
            softly.assertThat(board[3]).as("Row 4").containsExactly(WHITE_PAWN, WHITE_BISHOP, BLACK_KNIGHT, null, null, null, null, BLACK_PAWN);
            softly.assertThat(board[4]).as("Row 5").containsExactly(BLACK_PAWN, null, WHITE_PAWN, null, null, BLACK_QUEEN, WHITE_PAWN, null);
            softly.assertThat(board[5]).as("Row 6").containsExactly(BLACK_ROOK, null, BLACK_PAWN, null, null, null, null, null);
            softly.assertThat(board[6]).as("Row 7").containsExactly(null, null, BLACK_BISHOP, BLACK_KNIGHT, null, null, null, null);
            softly.assertThat(board[7]).as("Row 8").containsExactly(BLACK_KING, BLACK_ROOK, null, null, null, WHITE_ROOK, null, null);
        }
    }

    @Test
    public void TestBoardBuilder_smallBoard() {
        String input =
                        "_♔♞__\n" +
                        "___♝_\n" +
                        "_____\n" +
                        "♛__♗♚\n" +
                        "__♙__\n" +
                        "♟____\n";

        Piece[][] board = TestBoardBuilder.buildBoard(input);
        assertThat(board).hasSameDimensionsAs(new Piece[6][5]);

        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(board[0]).as("Row 1").containsExactly(BLACK_PAWN, null, null, null, null);
            softly.assertThat(board[1]).as("Row 2").containsExactly(null, null, WHITE_PAWN, null, null);
            softly.assertThat(board[2]).as("Row 3").containsExactly(BLACK_QUEEN, null, null, WHITE_BISHOP, BLACK_KING);
            softly.assertThat(board[3]).as("Row 4").containsOnlyNulls();
            softly.assertThat(board[4]).as("Row 5").containsExactly(null, null, null, BLACK_BISHOP, null);
            softly.assertThat(board[5]).as("Row 6").containsExactly(null, WHITE_KING, BLACK_KNIGHT, null, null);
        }
    }

}
