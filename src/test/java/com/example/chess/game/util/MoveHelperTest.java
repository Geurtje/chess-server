package com.example.chess.game.util;

import com.example.chess.game.model.Game;
import com.example.chess.game.model.Location;
import com.example.chess.game.model.Player;
import com.example.chess.game.model.TestBoardBuilder;
import com.example.chess.piece.Piece;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class MoveHelperTest {

    @Test
    public void TestWhitePawnMovementFromStartWithCapture() {
        String boardState =
                "♜♞♝♛♚♝♞♜\n" +
                "♟♟_♟♟♟♟♟\n" +
                "________\n" +
                "________\n" +
                "________\n" +
                "__♟_____\n" +
                "♙♙♙♙♙♙♙♙\n" +
                "♖♘♗♕♔♗♘♖";
        Game g = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(boardState));

        Location b2 = Location.fromText("B2");
        Piece pawnB2 = g.getPieceAtLocation(b2);

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(g, pawnB2, b2);
        assertThat(possibleMoves).containsExactlyInAnyOrder(Location.fromText("B3"), Location.fromText("B4"), Location.fromText("C3"));
    }

    @Test
    public void TestWhitePawnMovementFromStartWithCaptureAndObstructedPosition() {
        String boardState =
                "________\n" +
                "_♘______\n" +
                "__♟_____\n" +
                "♙♙♙♙♙♙♙♙\n" +
                "♖♘♗♕♔♗♘♖";
        Game g = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(boardState));

        Location b2 = Location.fromText("B2");
        Piece pawnB2 = g.getPieceAtLocation(b2);

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(g, pawnB2, b2);
        assertThat(possibleMoves).containsExactlyInAnyOrder(Location.fromText("B3"), Location.fromText("C3"));
    }


    @Test
    public void TestWhitePawnMovementFromStartOnlyCapture() {
        String boardState =
                "________\n" +
                "_______\n" +
                "♟♘_____\n" +
                "♙♙♙♙♙♙♙♙\n" +
                "♖♘♗♕♔♗♘♖";
        Game g = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(boardState));

        Location b2 = Location.fromText("B2");
        Piece pawnB2 = g.getPieceAtLocation(b2);

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(g, pawnB2, b2);
        assertThat(possibleMoves).containsExactlyInAnyOrder(Location.fromText("A3"));
    }

    @Test
    public void TestBlackPawnMovementFromStart() {
        Game g = new Game("test", Player.BLACK, TestBoardBuilder.buildBoard(TestBoardBuilder.STARTING_BOARD));

        Location l = Location.fromText("E7");
        Piece pawn = g.getPieceAtLocation(l);

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(g, pawn, l);
        assertThat(possibleMoves).containsExactlyInAnyOrder(Location.fromText("E6"), Location.fromText("E5"));
    }


    @Test
    public void TestKnightMovement() {
        String boardState =
                "________\n" +
                "________\n" +
                "________\n" +
                "________\n" +
                "___♘____\n" +
                "________\n" +
                "________\n" +
                "________\n";
        Game g = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(boardState));
        System.out.println(GameStateVisualizer.visualizeGameBoard(g));

        Location l = Location.fromText("D4");
        Piece knight = g.getPieceAtLocation(l);

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(g, knight, l);
        assertThat(possibleMoves).extracting(Location::toString).containsExactlyInAnyOrder("B5", "C6", "E6", "F5", "F3", "E2", "C2", "B3");
    }


    @Test
    public void TestKnightMovementAtCorner() {
        String boardState =
                "________\n" +
                "________\n" +
                "________\n" +
                "_♘______";
        Game g = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(boardState));
        System.out.println(GameStateVisualizer.visualizeGameBoard(g));

        Location l = Location.fromText("B1");
        Piece knight = g.getPieceAtLocation(l);

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(g, knight, l);
        assertThat(possibleMoves).extracting(Location::toString).containsExactlyInAnyOrder("A3", "C3", "D2");
    }

    @Test
    public void TestKnightMovementWithFriendlyAndEnemyPawns() {
        String boardState =
                "________\n" +
                "♟_♙_____\n" +
                "________\n" +
                "_♞______\n" +
                "___♟____";
        Game g = new Game("test", Player.BLACK, TestBoardBuilder.buildBoard(boardState));
        System.out.println(GameStateVisualizer.visualizeGameBoard(g));

        Location l = Location.fromText("B2");
        Piece knight = g.getPieceAtLocation(l);

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(g, knight, l);
        assertThat(possibleMoves).extracting(Location::toString).containsExactlyInAnyOrder("C4", "D3");
    }

    @Test
    public void TestRookMovement() {
        String boardState =
                        "____\n" +
                        "_♖__\n" +
                        "____\n";
        Game g = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(boardState));
        System.out.println(GameStateVisualizer.visualizeGameBoard(g));

        Location l = Location.fromText("B2");
        Piece rook = g.getPieceAtLocation(l);

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(g, rook, l);
        assertThat(possibleMoves).extracting(Location::toString).containsExactlyInAnyOrder("A2", "B3", "B1", "C2", "D2");
    }

    @Test
    public void TestRookMovementFromCorner() {
        String boardState =
                "♟___\n" +
                "____\n" +
                "♜_♖_\n";
        Game g = new Game("test", Player.BLACK, TestBoardBuilder.buildBoard(boardState));
        System.out.println(GameStateVisualizer.visualizeGameBoard(g));

        Location l = Location.fromText("A1");
        Piece rook = g.getPieceAtLocation(l);

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(g, rook, l);
        assertThat(possibleMoves).extracting(Location::toString).containsExactlyInAnyOrder("A2", "B1", "C1");
    }


    @Test
    public void TestBishopMovement() {
        String boardState =
                        "_____\n" +
                        "_____\n" +
                        "_____\n" +
                        "_♝___\n" +
                        "_____\n";
        Game g = new Game("test", Player.BLACK, TestBoardBuilder.buildBoard(boardState));
        System.out.println(GameStateVisualizer.visualizeGameBoard(g));

        Location l = Location.fromText("B2");
        Piece bishop = g.getPieceAtLocation(l);

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(g, bishop, l);
        assertThat(possibleMoves).extracting(Location::toString).containsExactlyInAnyOrder("A1", "A3", "C1", "C3", "D4", "E5");
    }

    @Test
    public void TestQueenMovement() {
        String boardState =
                        "_____\n" +
                        "___♕_\n" +
                        "_____\n" +
                        "_____\n" +
                        "_____\n";
        Game g = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(boardState));
        System.out.println(GameStateVisualizer.visualizeGameBoard(g));

        Location l = Location.fromText("D4");
        Piece queen = g.getPieceAtLocation(l);

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(g, queen, l);
        assertThat(possibleMoves).extracting(Location::toString).containsExactlyInAnyOrder(
                "C5", "D5", "E5",
                "A4", "B4", "C4", "E4",
                "C3", "D3", "E3",
                "B2", "D2",
                "A1", "D1"
                );
    }

    @Test
    public void TestKingMovement() {
        String boardState =
                        "____\n" +
                        "♔___\n" +
                        "____\n" +
                        "____\n";
        Game g = new Game("test", Player.WHITE, TestBoardBuilder.buildBoard(boardState));
        System.out.println(GameStateVisualizer.visualizeGameBoard(g));

        Location l = Location.fromText("A3");
        Piece king = g.getPieceAtLocation(l);

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(g, king, l);
        assertThat(possibleMoves).extracting(Location::toString).containsExactlyInAnyOrder("A4", "B4", "B3", "B2", "A2");
    }

}
