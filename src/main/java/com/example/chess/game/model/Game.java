package com.example.chess.game.model;

import com.example.chess.game.exception.ChessException;
import com.example.chess.game.exception.InvalidMoveException;
import com.example.chess.game.util.MoveHelper;
import com.example.chess.piece.Piece;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class Game {

    private final String id;
    private Player currentPlayer;
    private final Piece[][] board;

    public void rotateCurrentPlayer() {
        if (currentPlayer == Player.WHITE) {
            currentPlayer = Player.BLACK;
        }
        else {
            currentPlayer = Player.WHITE;
        }
    }

    public boolean hasOpponentPieceAtLocation(Location l) {
        Piece pieceAtLocation =  getPieceAtLocation(l);
        return pieceAtLocation != null && currentPlayer != pieceAtLocation.owner;
    }

    public boolean isLocationNotEmpty(Location l) {
        return getPieceAtLocation(l) != null;
    }

    public boolean isLocationEmptyOrHasOpponent(Location l) {
        Piece p = getPieceAtLocation(l);
        return p == null || p.owner != currentPlayer;
    }

    public boolean isLocationEmpty(Location l) {
        return getPieceAtLocation(l) == null;
    }

    public Piece getPieceAtLocation(Location l) {
        return board[l.y][l.x];
    }

    public boolean isLocationOnBoard(Location l) {
        return l.y >= 0 && l.y < board.length &&
                l.x >= 0 && l.x < board[0].length;
    }

    public Collection<String> movePiece(Location from, Location to)  throws ChessException {
        Piece pieceAtLocation = getPieceAtLocation(from);
        if (pieceAtLocation == null) {
            throw new InvalidMoveException("no piece found at location " + from);
        }
        if (pieceAtLocation.owner != currentPlayer) {
            throw new InvalidMoveException("unable to move piece " + pieceAtLocation + " (" + from + ") because the owner doesn't match the current player (" + getCurrentPlayer() + ")");
        }

        // figure out which positions this piece can move to, and check if the desired location is present
        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(this, pieceAtLocation, from);
        if (!possibleMoves.contains(to)) {
            if (possibleMoves.size() == 0) {
                throw new InvalidMoveException("piece " + pieceAtLocation + " (" + from + ") can't move to "
                        + to + ", this piece can't move to any locations");
            }
            else {
                String possibleLocationsStr = possibleMoves.stream()
                        .map(Location::toString)
                        .collect(Collectors.joining(", "));
                throw new InvalidMoveException("piece " + pieceAtLocation + " (" + from + ") can't move to "
                        + to + ", valid locations are [" + possibleLocationsStr + "]");
            }
        }

        /// move piece to desired location and capture any opposing pieces if there is one at that location
        Collection<String> moveConsequences = new ArrayList<>();
        if (hasOpponentPieceAtLocation(to)) {
            Piece opponentPiece = getPieceAtLocation(to);
            moveConsequences.add("captured opponent piece " + opponentPiece + " at location " + to);
        }

        board[to.y][to.x] = getBoard()[from.y][from.x];
        board[from.y][from.x] = null;

        return moveConsequences;
    }

}
