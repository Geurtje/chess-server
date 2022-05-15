# chess-server

This project is a simple chess API assignment.

## API

The API has 3 main commands. They can be explored interactively with swagger at http://localhost:8080/swagger/...

The create command will start a new game.
```
POST /chess/game/create 
```
--> return a game id


The status command will fetch the status of a game.
```
GET /chess/game/${GAME_ID}/status
```
--> Will return an overview of the game status, the information that is returned:
- if the game is still in progress or not, if ended the winner is mentioned
- which player's turn it is
- 
 
The move command can be used by a player to move a piece and advance the game.
```
PUT /chess/game/${GAME_ID}/move
{
 piece location x, y 
 piece target location x, y
}
```

Returns ok if the move has been accepted, or an error if the move was invalid for any reason
ok message: moved piece $name to x, y, [destroyed $PLAYER_COLOUR's $piece_name] [check] [checkmate] 
invalid messages: 
- wrong color piece, can't move your opponents pieces
- can't move to the desired location because a piece is in the way
- illegal movement for piece $name, valid locations are ...


### Future API additions

- An API to fetch possible coordinates for a piece to move to

## Application architecture

????


## Considerations
A rest API has been chosen because of the simplicity of getting it up and running. For a real world
use case something more suitable for an interactive application such as websockets would be a better alternative.





