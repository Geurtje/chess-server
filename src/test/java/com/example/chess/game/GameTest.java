package com.example.chess.game;

import com.example.chess.game.Game;
import com.example.chess.game.exception.InvalidMoveException;
import com.example.chess.game.model.*;
import com.example.chess.game.piece.Piece;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static com.example.chess.game.piece.Piece.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
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

        assertThat(game.getPieceAtLocation(from)).isEqualTo(WHITE_PAWN);
        assertThat(game.isLocationEmpty(to)).isTrue();
        game.movePiece(from, to);
        assertThat(game.getPieceAtLocation(to)).isEqualTo(WHITE_PAWN);
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

        assertThat(game.getPieceAtLocation(from)).isEqualTo(WHITE_PAWN);
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
        assertThat(game.getPieceAtLocation(to)).isEqualTo(WHITE_PAWN);
        Collection<String> moveConsequences = game.movePiece(from, to);
        assertThat(game.getPieceAtLocation(to)).isEqualTo(Piece.BLACK_KNIGHT);
        assertThat(game.isLocationEmpty(from)).isTrue();
        assertThat(moveConsequences).containsExactly("captured opponent piece " + WHITE_PAWN + " at location " + to);
    }

    @Test
    public void TestFindOpponentKingBlack() {
        String boardState =
                        "♚♜___♖__\n" +
                        "__♝♞____\n" +
                        "♜_♟_____\n" +
                        "♟_♙__♛♙_\n" +
                        "♙♗♞____♟\n" +
                        "♖__♘_♕__\n" +
                        "___♔_♙__";

        Game game = new Game("test", Player.BLACK, TestBoardBuilder.buildBoard(boardState));
        LocationPiece lp = game.findOpponentKing();
        assertThat(lp.location.toString()).isEqualTo("D1");
        assertThat(lp.piece).isEqualTo(Piece.WHITE_KING);
    }

    @Test
    public void TestFindOpponentKingWhite() {
        String boardState =
                        "♚♜___♖__\n" +
                        "__♝♞____\n" +
                        "♜_♟_____\n" +
                        "♟_♙__♛♙_\n" +
                        "♙♗♞____♟\n" +
                        "♖__♘_♕__\n" +
                        "___♔_♙__";

        Game game = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(boardState));
        LocationPiece lp = game.findOpponentKing();
        assertThat(lp.location.toString()).isEqualTo("A7");
        assertThat(lp.piece).isEqualTo(Piece.BLACK_KING);
    }

    @Test
    public void TestGetAllPlayerPiecesForWhite() {
        String boardState =
                        "♚♜___♖__\n" +
                        "__♝♞____\n" +
                        "♖__♘_♕__\n" +
                        "___♔_♙__";

        Game game = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(boardState));
        Collection<LocationPiece> allPlayerPieces = game.getAllPlayerPieces();

        assertThat(allPlayerPieces)
                .extracting(lp -> lp.location.toString(), lp -> lp.piece)
                .containsExactlyInAnyOrder(
                        tuple("A2", WHITE_ROOK),
                        tuple("D1", WHITE_KING),
                        tuple("F1", WHITE_PAWN),
                        tuple("D2", WHITE_KNIGHT),
                        tuple("F2", WHITE_QUEEN),
                        tuple("F4", WHITE_ROOK)
                );
    }

    @Test
    public void TestGetAllPlayerPiecesForBlack() {
        String boardState =
                        "♚♜___♖__\n" +
                        "__♝♞____\n" +
                        "♖__♘_♕__\n" +
                        "___♔_♙__";

        Game game = new Game("test", Player.BLACK, TestBoardBuilder.buildBoard(boardState));
        Collection<LocationPiece> allPlayerPieces = game.getAllPlayerPieces();

        assertThat(allPlayerPieces)
                .extracting(lp -> lp.location.toString(), lp -> lp.piece)
                .containsExactlyInAnyOrder(
                        tuple("A4", BLACK_KING),
                        tuple("B4", BLACK_ROOK),
                        tuple("C3", BLACK_BISHOP),
                        tuple("D3", BLACK_KNIGHT)
                );
    }


    @Test
    public void TestValidateState_AtGameStart() {
        String board =
                        "♜♞♝♛♚♝♞♜\n" +
                        "♟♟♟♟♟♟♟♟\n" +
                        "________\n" +
                        "________\n" +
                        "_♙______\n" +
                        "________\n" +
                        "♙_♙♙♙♙♙♙\n" +
                        "♖♘♗♕♔♗♘♖";
        Game game = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(board));
        assertThat(game.validateState()).isEqualTo(GameState.UNDECIDED);
    }

    @Test
    public void TestValidateState_OpenKing() {
        String board =
                        "♜♞♝♛_♝♞♜\n" +
                        "♟♟_____♟\n" +
                        "____♚___\n" +
                        "__♘_____\n" +
                        "________\n" +
                        "________\n" +
                        "♙_♙♙♙♙♙♙\n" +
                        "♖_♗♕♔♗♘♖";

        Game game = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(board));
        assertThat(game.validateState()).isEqualTo(GameState.CHECK);
    }

    @Test
    public void TestValidateState_SurroundedKing() {
        String board =
                        "♜_♝_♚♝__\n" +
                        "♟♟____♞♟\n" +
                        "___♛__♜_\n" +
                        "__♞_____\n" +
                        "_____♔__\n" +
                        "____♙♙♙_\n" +
                        "♙_♙♙___♙\n" +
                        "♖_♗♕_♗♘♖";

        Game game = new Game("test", Player.BLACK, TestBoardBuilder.buildBoard(board));
        assertThat(game.validateState()).isEqualTo(GameState.CHECKMATE);
    }

    //fixme, moving the bishop to A3, should prevent checkmate
    @Disabled
    @Test
    public void TestValidateState_SacrificialMove() {
        String board =
                        "♚♝__\n" +
                        "_♟__\n" +
                        "____\n" +
                        "♖___";

        Game game = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(board));
        assertThat(game.validateState()).isEqualTo(GameState.CHECK);
    }

    //fixme, capturing the rook with the knight should prevent checkmate
    @Disabled
    @Test
    public void TestValidateState_PreventiveCapture() {
        String board =
                        "♚♝__\n" +
                        "_♞__\n" +
                        "____\n" +
                        "♖___";

        Game game = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(board));
        assertThat(game.validateState()).isEqualTo(GameState.CHECK);
    }

    // another edge case is using the king to capture a threatening piece
    // WHICH MIGHT NOT WORK because a piece behind the one you just captured might then be able to capture the king
}
