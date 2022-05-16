package com.example.chess.game.model;

import com.example.chess.game.Game;
import lombok.Builder;

import java.util.Collection;

@Builder
public class MovePieceResult {
    public final Game game;
    public final Collection<String> consequences;
}
