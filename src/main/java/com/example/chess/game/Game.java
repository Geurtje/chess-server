package com.example.chess.game;

import com.example.chess.game.exception.ChessException;
import com.example.chess.game.exception.InvalidMoveException;
import com.example.chess.game.model.GameState;
import com.example.chess.game.model.Location;
import com.example.chess.game.model.LocationPiece;
import com.example.chess.game.model.Player;
import com.example.chess.game.piece.MoveHelper;
import com.example.chess.game.piece.Piece;
import com.example.chess.game.piece.PieceType;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Game {

    private final String id;
    private Player currentPlayer;
    private Player winner;
    private final Piece[][] board;
    private GameState state = GameState.UNDECIDED;

    public Game(String id, Player currentPlayer, Piece[][] board) {
        this.id = id;
        this.currentPlayer = currentPlayer;
        this.board = board;
    }

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

    public boolean isLocationEmptyOrHasOpponent(Location l, Player player) {
        Piece p = getPieceAtLocation(l);
        return p == null || p.owner != player;
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

    public Collection<String> checkGameEndCondition() {
        state = validateState();

        if (state == GameState.CHECK) {
            return Collections.singleton("check");
        }
        else if (state == GameState.CHECKMATE) {
            winner = currentPlayer;
            return List.of("checkmate", "game has ended, " + winner + " is the winner");
        }

        return Collections.emptyList();
    }

    LocationPiece findOpponentKing() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                Piece p = board[y][x];
                if (p != null && p.pieceType == PieceType.KING && p.owner != currentPlayer) {
                    return new LocationPiece(new Location(x, y), p);
                }
            }
        }

        Player opponent = currentPlayer == Player.WHITE ? Player.BLACK : Player.WHITE;
        throw new IllegalStateException("unable to find king for player " + opponent + " the game is in an invalid state");
    }

    Collection<LocationPiece> getAllPlayerPieces() {
        Collection<LocationPiece> playerPieces = new ArrayList<>();

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                Piece p = board[y][x];
                if (p != null && p.owner == currentPlayer) {
                    playerPieces.add(new LocationPiece(new Location(x, y), p));
                }
            }
        }

        return playerPieces;
    }

    /**
     * Get the current state of the game, intended to be called after a player has just made a move.
     * @return
     */
    public GameState validateState() {
        // find the location of the opponent's king
        LocationPiece opponentKing = findOpponentKing();

        // get all possible moves for the current player
        Collection<LocationPiece> playerPieces = getAllPlayerPieces();
        Set<Location> possibleNextMoveLocations = playerPieces.stream()
                .map(lp -> MoveHelper.getPossibleMoves(this, lp.piece, lp.location))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        // and check if any of the moves for the
        if (possibleNextMoveLocations.contains(opponentKing.location)) {
            ///  check! might be checkmate if the king can't move anywhere safe
            // other edge conditions:
            // - capturing an opposing piece to remove an attack on the king
            // - placing a piece in the way of the king to prevent an opponent from capturing it


            // get opponent king possible locations
            Collection<Location> possibleKingMoves = MoveHelper.getPossibleMoves(this, opponentKing.piece, opponentKing.location);
            if (possibleNextMoveLocations.containsAll(possibleKingMoves)) {
                return GameState.CHECKMATE;
            }
            else {
                return GameState.CHECK;
            }
        }

        return GameState.UNDECIDED;
    }
}
