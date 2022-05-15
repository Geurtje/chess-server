package com.example.chess.game.util;

import com.example.chess.game.model.Game;
import com.example.chess.game.model.Location;
import com.example.chess.game.model.Player;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PieceType;
import lombok.AllArgsConstructor;

import java.util.*;

public class MoveHelper {

    private static final Map<PieceType, List<MovementOffset>> movementOffsetPerPieceType = new HashMap<>();
    static {
        movementOffsetPerPieceType.put(PieceType.KNIGHT, List.of(
            new MovementOffset(-2, -1), new MovementOffset(-1, -2),
            new MovementOffset(2, -1), new MovementOffset(1, -2),
            new MovementOffset(-2, 1), new MovementOffset(-1, 2),
            new MovementOffset(2, 1), new MovementOffset(1, 2)
        ));

        movementOffsetPerPieceType.put(PieceType.ROOK, List.of(
                new MovementOffset(-1, 0, true), //left
                new MovementOffset(1, 0, true), //right
                new MovementOffset(0, -1, true), //down
                new MovementOffset(0, 1, true) //up
        ));

        movementOffsetPerPieceType.put(PieceType.BISHOP, List.of(
                new MovementOffset(-1, -1, true), //bottom left
                new MovementOffset(-1, 1, true), //top left
                new MovementOffset(1, -1, true), //bottom right
                new MovementOffset(1, 1, true) //top left
        ));

        movementOffsetPerPieceType.put(PieceType.QUEEN, List.of(
                new MovementOffset(-1, -1, true), //bottom left
                new MovementOffset(-1, 1, true), //top left
                new MovementOffset(1, -1, true), //bottom right
                new MovementOffset(1, 1, true), //top left
                new MovementOffset(-1, 0, true), //left
                new MovementOffset(1, 0, true), //right
                new MovementOffset(0, -1, true), //down
                new MovementOffset(0, 1, true) //up
        ));

        movementOffsetPerPieceType.put(PieceType.KING, List.of(
                new MovementOffset(-1, -1), //bottom left
                new MovementOffset(-1, 1), //top left
                new MovementOffset(1, -1), //bottom right
                new MovementOffset(1, 1), //top left
                new MovementOffset(-1, 0), //left
                new MovementOffset(1, 0), //right
                new MovementOffset(0, -1), //down
                new MovementOffset(0, 1) //up
        ));
    }

    public static Collection<Location> getPossibleMoves(Game game, Piece piece, Location from) {
        // pawns have some special rules in how they can move so that's dealt with in a separate method
        if (piece.pieceType == PieceType.PAWN) {
            return getPossibleMovesPawn(game, piece, from);
        }
        return getPossibleMovesFromMovementOffset(game, piece, from);
    }

    private static Collection<Location> getPossibleMovesFromMovementOffset(Game game, Piece piece, Location from) {
        if (!movementOffsetPerPieceType.containsKey(piece.pieceType)) {
            throw new IllegalArgumentException("piece type " + piece.pieceType + " is not yet supported, no movement offset was configured for this type");
        }

        List<MovementOffset> movementOffsets = movementOffsetPerPieceType.get(piece.pieceType);
        List<Location> possibleMoves = new ArrayList<>();

        for (MovementOffset movementOffset : movementOffsets) {
            Location possibleLocation = movementOffset.applyTo(from);
            if (game.isLocationOnBoard(possibleLocation) && game.isLocationEmptyOrHasOpponent(possibleLocation, piece.owner)) {
                possibleMoves.add(possibleLocation);

                // if this movement can be repeated we'll apply it until we encounter a piece
                while (movementOffset.repeating && game.isLocationEmpty(possibleLocation)) {
                    possibleLocation = movementOffset.applyTo(possibleLocation);
                    if (game.isLocationOnBoard(possibleLocation) && game.isLocationEmptyOrHasOpponent(possibleLocation, piece.owner)) {
                        possibleMoves.add(possibleLocation);
                    } else {
                        break;
                    }
                }
            }
        }

        return possibleMoves;
    }

    private static Collection<Location> getPossibleMovesPawn(Game game, Piece piece, Location from) {
        Collection<Location> possibleLocations = new ArrayList<>();
        boolean yDirectionUp = piece.owner == Player.WHITE;
        // multiply the mutation on y by the direction to move up or down depending on the players playing direction
        int yDirection = yDirectionUp ? 1 : -1;

        // possible moves
        Location l = new Location(from.x, from.y+(1*yDirection));
        if (game.isLocationOnBoard(l) && game.isLocationEmpty(l)) {
            possibleLocations.add(l);

            int pawnStartRow = piece.owner == Player.WHITE ? 1 : 6;
            if (from.y == pawnStartRow) {
                l = new Location(from.x, from.y+2*yDirection);
                if (game.isLocationOnBoard(l) && game.isLocationEmpty(l)) {
                    possibleLocations.add(l);
                }
            }
        }

        // capturing diagonally
        l = new Location(from.x-1, from.y+1*yDirection);
        if (game.isLocationOnBoard(l) && game.hasOpponentPieceAtLocation(l)) {
            possibleLocations.add(l);
        }
        l = new Location(from.x+1, from.y+1*yDirection);
        if (game.isLocationOnBoard(l) && game.hasOpponentPieceAtLocation(l)) {
            possibleLocations.add(l);
        }
        return possibleLocations;
    }

    @AllArgsConstructor
    private static class MovementOffset {
        public final int x;
        public final int y;
        public final boolean repeating;

        MovementOffset(int x, int y) {
            this.x = x;
            this.y = y;
            repeating = false;
        }

        Location applyTo(Location l) {
            return new Location(l.x + x, l.y + y);
        }
    }

}
