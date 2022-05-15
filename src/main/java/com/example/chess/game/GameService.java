package com.example.chess.game;

import com.example.chess.game.exception.ChessException;
import com.example.chess.game.exception.InvalidMoveException;
import com.example.chess.game.exception.UnknownGameException;
import com.example.chess.game.model.Game;
import com.example.chess.game.model.Location;
import com.example.chess.game.model.MovePieceResult;
import com.example.chess.game.model.Player;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PieceType;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game startNewGame() {
        String gameId = gameRepository.generateGameId();

        Game game = new Game(gameId);
        gameRepository.saveGame(game);
        return game;
    }

    public Game getGameState(String gameId) throws UnknownGameException {
        return gameRepository.getGame(gameId).orElseThrow(() -> new UnknownGameException(gameId));
    }

    public MovePieceResult movePiece(String gameId, String locationFromStr, String locationToStr)  throws ChessException {
        Game game = getGameState(gameId);

        Location locationFrom = Location.fromText(locationFromStr);
        Location locationTo = Location.fromText(locationToStr);

        Collection<String> moveConsequences = new ArrayList<>();

        /// move piece (game, from, to)
        moveConsequences.addAll(movePiece(game, locationFrom, locationTo));

        // verify game state, check/checkmate?
        moveConsequences.addAll(checkGameEndCondition(game));

        game.rotateCurrentPlayer();

        gameRepository.saveGame(game);
        return MovePieceResult.builder()
                .game(game)
                .consequences(moveConsequences)
                .build();
    }

    private Collection<String> movePiece(Game game, Location from, Location to) throws InvalidMoveException {
        Piece pieceAtLocation = getPieceAtLocation(game, from);
        if (pieceAtLocation == null) {
            throw new InvalidMoveException("no piece found at location " + from.getText());
        }
        if (pieceAtLocation.owner != game.getCurrentPlayer()) {
            throw new InvalidMoveException("unable to move piece " + pieceAtLocation.owner + " " + pieceAtLocation.pieceType + " (" + from.getText() + ") because the owner doesn't match the current player (" + game.getCurrentPlayer() + ")");
        }

        // can piece move to new location

        Collection<Location> possibleLocations = getPossibleMoves(game, pieceAtLocation, from);
        if (!possibleLocations.contains(to)) {

            String possibleLocationsStr = possibleLocations.stream()
                    .map(Location::getText)
                    .collect(Collectors.joining(", "));

            if (possibleLocations.size() == 0) {
                throw new InvalidMoveException("piece " + pieceAtLocation.owner + " " + pieceAtLocation.pieceType + " (" + from.getText() + ") can't move to "
                        + to.getText() + ", this piece can't move to any locations");
            }
            else {
                throw new InvalidMoveException("piece " + pieceAtLocation.owner + " " + pieceAtLocation.pieceType + " (" + from.getText() + ") can't move to "
                        + to.getText() + ", valid locations are [" + possibleLocationsStr + "]");
            }
        }

        // move to the target location is valid
        Collection<String> consequences = new ArrayList<>();

        if (hasOpponentPieceAtLocation(game, to)) {
            Piece opponentPiece = getPieceAtLocation(game, to);
            consequences.add("captured opponent piece " + opponentPiece + " at location " + to.getText());
        }

        game.getBoard()[to.y][to.x] = game.getBoard()[from.y][from.x];
        game.getBoard()[from.y][from.x] = null;

        return consequences;
    }


    private Collection<String> checkGameEndCondition(Game game) {

        return Collections.emptyList();
    }

    private Collection<Location> getPossibleMoves(Game game, Piece piece, Location from) {
        Collection<Location> possibleLocations = new ArrayList<>();

        if (piece.pieceType == PieceType.PAWN) {
            boolean yDirectionUp = piece.owner == Player.WHITE;
            // multiple the mutation on y by the direction to move up or down depending on the players playing direction
            int yDirection = yDirectionUp ? 1 : -1;
            int startingRow = piece.owner == Player.WHITE ? 1 : 6;

            // possible moves
            Location l = new Location(from.x, from.y+(1*yDirection));
            if (l.isInBounds() && isLocationEmpty(game, l)) {
                possibleLocations.add(l);
            }

            if (from.y == startingRow) {
                l = new Location(from.x, from.y+2*yDirection);
                if (l.isInBounds() && isLocationEmpty(game, l)) {
                    possibleLocations.add(l);
                }
            }

            // capturing
            l = new Location(from.x-1, from.y+1*yDirection);
            if (l.isInBounds() && hasOpponentPieceAtLocation(game, l)) {
                possibleLocations.add(l);
            }
            l = new Location(from.x+1, from.y+1*yDirection);
            if (l.isInBounds() && hasOpponentPieceAtLocation(game, l)) {
                possibleLocations.add(l);
            }
        }

        return possibleLocations;
    }

    private boolean hasOpponentPieceAtLocation(Game game, Location l) {
        Piece pieceAtLocation =  getPieceAtLocation(game, l);
        return pieceAtLocation != null && game.getCurrentPlayer() != pieceAtLocation.owner;
    }

    private boolean hasPieceAtLocation(Game game, Location l) {
        return getPieceAtLocation(game, l) != null;
    }

    private boolean isLocationEmpty(Game game, Location l) {
        return getPieceAtLocation(game, l) == null;
    }

    private Piece getPieceAtLocation(Game game, Location l) {
        return game.getBoard()[l.y][l.x];
    }
}
