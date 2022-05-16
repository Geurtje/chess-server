package com.example.chess.game;

import com.example.chess.game.exception.ChessException;
import com.example.chess.game.exception.GameEndedException;
import com.example.chess.game.exception.UnknownGameException;
import com.example.chess.game.model.*;
import com.example.chess.game.piece.MoveHelper;
import com.example.chess.game.piece.Piece;
import com.example.chess.game.util.BoardTemplate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game startNewGame() {
        String gameId = gameRepository.generateGameId();

        Game game = new Game(gameId, Player.WHITE, BoardTemplate.createNewBoard());
        gameRepository.saveGame(game);
        return game;
    }

    public Game getGameState(String gameId) throws UnknownGameException {
        return gameRepository.getGame(gameId).orElseThrow(() -> new UnknownGameException(gameId));
    }

    public Collection<String> getPossibleMovesForLocation(String gameId, String locationStr) {
        Game game = getGameState(gameId);
        Location location = Location.fromText(locationStr);
        Piece piece = game.getPieceAtLocation(location);
        if (piece == null) {
            return Collections.emptyList();
        }

        Collection<Location> possibleMoves = MoveHelper.getPossibleMoves(game, piece, location);
        return possibleMoves.stream()
                .map(Location::toString)
                .collect(Collectors.toList());
    }

    public MovePieceResult movePiece(String gameId, String locationFromStr, String locationToStr) throws ChessException {
        Game game = getGameState(gameId);

        if (game.getState() == GameState.CHECKMATE) {
            throw new GameEndedException(gameId, game.getWinner());
        }

        Location locationFrom = Location.fromText(locationFromStr);
        Location locationTo = Location.fromText(locationToStr);

        /// move piece (game, from, to)
        Collection<String> moveConsequences = game.movePiece(locationFrom, locationTo);

        // update the game state
        moveConsequences.addAll(game.checkGameEndCondition());

        game.rotateCurrentPlayer();

        gameRepository.saveGame(game);
        return MovePieceResult.builder()
                .game(game)
                .consequences(moveConsequences)
                .build();
    }
}
