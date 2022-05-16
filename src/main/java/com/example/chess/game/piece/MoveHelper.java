package com.example.chess.game.piece;

import com.example.chess.game.Game;
import com.example.chess.game.model.Location;
import com.example.chess.game.model.Player;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class that is able to apply a piece's MovementOffsets on a Game board.
 */
@UtilityClass
public class MoveHelper {

    public static Collection<Location> getPossibleMoves(Game game, Piece piece, Location from) {
        // pawns have some special rules in how they can move so that's dealt with in a separate method
        if (piece.pieceType == PieceType.PAWN) {
            return getPossibleMovesPawn(game, piece, from);
        }
        return getPossibleMovesFromMovementOffset(game, piece, from);
    }

    private static Collection<Location> getPossibleMovesFromMovementOffset(Game game, Piece piece, Location from) {
        if (piece.pieceType.movementOffsets == null) {
            throw new IllegalArgumentException("piece type " + piece.pieceType + " is not supported, no movement offset was configured for this type");
        }

        List<Location> possibleMoves = new ArrayList<>();
        for (MovementOffset movementOffset : piece.pieceType.movementOffsets) {
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

}
