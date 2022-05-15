package com.example.chess.game.model;

import com.example.chess.game.exception.InvalidMoveException;
import com.example.chess.piece.Piece;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {

    @Test
    public void TestRotateCurrentPlayer() {
        Game game = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(TestBoardBuilder.STARTING_BOARD));
        assertThat(game.getCurrentPlayer()).as("current player before rotation").isEqualTo(Player.WHITE);
        game.rotateCurrentPlayer();
        assertThat(game.getCurrentPlayer()).as("current player after rotation").isEqualTo(Player.BLACK);
    }

    @Test
    public void TestMovePiece() {
        String boardState =
                        "________\n" +
                        "♟___♟__\n" +
                        "________\n" +
                        "_♞_♙_____\n" +
                        "________";
        Game game = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(boardState));

        Location from = Location.fromText("D2");
        Location to = Location.fromText("D4");

        assertThat(game.getPieceAtLocation(from)).isEqualTo(Piece.WHITE_PAWN);
        assertThat(game.isLocationEmpty(to)).isTrue();
        game.movePiece(from, to);
        assertThat(game.getPieceAtLocation(to)).isEqualTo(Piece.WHITE_PAWN);
        assertThat(game.isLocationEmpty(from)).isTrue();
    }

    @Test
    public void testMovePieceCantMoveOpponentsPiece() {
        String boardState =
                        "________\n" +
                        "♟___♟__\n" +
                        "_♞_______\n" +
                        "__♙_____";
        Game game = new Game("test", Player.BLACK, TestBoardBuilder.buildBoard(boardState));

        Location from = Location.fromText("C1");
        Location to = Location.fromText("B2");

        assertThat(game.getPieceAtLocation(from)).isEqualTo(Piece.WHITE_PAWN);
        Exception e = assertThrows(InvalidMoveException.class, () -> game.movePiece(from, to));
        assertThat(e.getMessage()).contains("e the owner doesn't match the current player");
    }

    @Test
    public void TestMovePieceCapture() {
        String boardState =
                        "______\n" +
                        "___♙__\n" +
                        "_♞____\n" +
                        "______";
        Game game = new Game("test", Player.BLACK, TestBoardBuilder.buildBoard(boardState));

        Location from = Location.fromText("B2");
        Location to = Location.fromText("D3");

        assertThat(game.getPieceAtLocation(from)).isEqualTo(Piece.BLACK_KNIGHT);
        assertThat(game.getPieceAtLocation(to)).isEqualTo(Piece.WHITE_PAWN);
        Collection<String> moveConsequences = game.movePiece(from, to);
        assertThat(game.getPieceAtLocation(to)).isEqualTo(Piece.BLACK_KNIGHT);
        assertThat(game.isLocationEmpty(from)).isTrue();
        assertThat(moveConsequences).containsExactly("captured opponent piece " + Piece.WHITE_PAWN + " at location " + to);
    }

}
