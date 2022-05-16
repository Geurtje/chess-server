package com.example.chess.game.piece;

import com.example.chess.game.model.Location;
import lombok.AllArgsConstructor;

/**
 * MovementOffset represents a movement that a piece can make on a board.
 * It has an X and Y component that indicate the number of steps in a given direction.
 *
 * For example a movement offset of +1 y and +1 x would move a piece one space up and one space to the right.
 *
 * A MovementOffset can be repeating, this indicates that this movement can be repeated until a piece is found,
 * for example think of how a rook can move one step at a time.
 */
@AllArgsConstructor
public class MovementOffset {
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