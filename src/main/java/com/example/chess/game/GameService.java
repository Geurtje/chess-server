package com.example.chess.game;

import com.example.chess.game.exception.ChessException;
import com.example.chess.game.exception.UnknownGameException;
import com.example.chess.game.model.Game;
import com.example.chess.game.model.Location;
import com.example.chess.game.model.MovePieceResult;
import com.example.chess.game.model.Player;
import com.example.chess.game.util.BoardTemplate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

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

    public MovePieceResult movePiece(String gameId, String locationFromStr, String locationToStr)  throws ChessException {
        Game game = getGameState(gameId);

        Location locationFrom = Location.fromText(locationFromStr);
        Location locationTo = Location.fromText(locationToStr);

        /// move piece (game, from, to)
        Collection<String> moveConsequences = game.movePiece(locationFrom, locationTo);

        // verify game state, check/checkmate?
        moveConsequences.addAll(checkGameEndCondition(game));

        game.rotateCurrentPlayer();

        gameRepository.saveGame(game);
        return MovePieceResult.builder()
                .game(game)
                .consequences(moveConsequences)
                .build();
    }

    private Collection<String> checkGameEndCondition(Game game) {

        return Collections.emptyList();
    }
}
