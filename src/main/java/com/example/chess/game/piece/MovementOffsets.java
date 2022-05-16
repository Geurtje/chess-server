package com.example.chess.game.piece;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MovementOffsets {

    static final List<MovementOffset> KNIGHT = List.of(
            new MovementOffset(-2, -1), new MovementOffset(-1, -2),
            new MovementOffset(2, -1), new MovementOffset(1, -2),
            new MovementOffset(-2, 1), new MovementOffset(-1, 2),
            new MovementOffset(2, 1), new MovementOffset(1, 2)
    );

    static final List<MovementOffset> ROOK = List.of(
            new MovementOffset(-1, 0, true), //left
            new MovementOffset(1, 0, true), //right
            new MovementOffset(0, -1, true), //down
            new MovementOffset(0, 1, true) //up
    );

    static final List<MovementOffset> BISHOP = List.of(
            new MovementOffset(-1, -1, true), //bottom left
            new MovementOffset(-1, 1, true), //top left
            new MovementOffset(1, -1, true), //bottom right
            new MovementOffset(1, 1, true) //top left
    );

    static final List<MovementOffset> QUEEN = List.of(
            new MovementOffset(-1, -1, true), //bottom left
            new MovementOffset(-1, 1, true), //top left
            new MovementOffset(1, -1, true), //bottom right
            new MovementOffset(1, 1, true), //top left
            new MovementOffset(-1, 0, true), //left
            new MovementOffset(1, 0, true), //right
            new MovementOffset(0, -1, true), //down
            new MovementOffset(0, 1, true) //up
    );

    static final List<MovementOffset> KING = List.of(
            new MovementOffset(-1, -1), //bottom left
            new MovementOffset(-1, 1), //top left
            new MovementOffset(1, -1), //bottom right
            new MovementOffset(1, 1), //top left
            new MovementOffset(-1, 0), //left
            new MovementOffset(1, 0), //right
            new MovementOffset(0, -1), //down
            new MovementOffset(0, 1) //up
    );
}