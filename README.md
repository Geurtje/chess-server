# chess-server

This project is a simple chess API assignment. It's a small Spring Boot project that exposes a JSON REST api 
that allows users to play a game of chess.

## How to run
You can run the application with the following maven command, this will start the application on port 8080.
```
mvn clean test spring-boot:run
```

## API examples

The API has 3 main commands that allow for the interactions that make up a game.
You can explore all the API interactively with swagger at http://localhost:8080/swagger-ui/index.html, but 
some curl examples have been provided below.

Or you can import the Postman workspace `chess-server-api.postman_collection.json`, to play through various scenarios. 
This approach conveniently remembers the game identifier that you have to reuse across requests. 

The Postman collection has a subfolder with a series of requests that play a sample game which produces a check 
situation in a couple of moves moves.

### Create a game
The following command creates a new game. The response is a game object, the most important of which
is the game identifier which is needed in subsequent requests. Other information is which player's turn
it is, and the positions of all the pieces on the board.

```
curl -X POST 'http://localhost:8080/chess/game/create'
```

### Game status
The status command will fetch the status of a game. Remember the use the game identifier from the create game request.
This request will return you all the information about a game.
```
curl -X GET 'http://localhost:8080/chess/game/${GAME_ID}/state'
```
 
### Move a piece
The move command can be used by a player to move a piece and advance the game to the next turn.
The following example will move a piece (a pawn in this case) from position A2 to A3. 

The response of this request is a game object if the move was successful, or a 400 if it was invalid.
For example when attempting to move a piece to an invalid location or when moving a piece of the 
opponent.
```
curl -X POST 'http://localhost:8080/chess/game/${GAME_ID}/move' \
--header 'Content-Type: application/json' \
--data-raw '{
    "location_from": "A2",
    "location_to": "A3"
}'
```

### Visualize a game
This request merely serves as a debugging tool to gain some visual insights into the state of a game due to the
lack of a fancy frontend.
```
curl -X GET 'http://localhost:8080/chess/game/${GAME_ID}/visualize'
```

For a freshly started game the response looks as follows:
```
#######################
Game: 421fa213
Current player: WHITE
#######################

  AㅤBㅤCㅤDㅤEㅤFㅤGㅤH
8|♜|♞|♝|♛|♚|♝|♞|♜|8
7|♟|♟|♟|♟|♟|♟|♟|♟|7
6|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|6
5|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|5
4|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|4
3|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|ㅤ|3
2|♙|♙|♙|♙|♙|♙|♙|♙|2
1|♖|♘|♗|♕|♔|♗|♘|♖|1
  AㅤBㅤCㅤDㅤEㅤFㅤGㅤH
```

## Application architecture

The application is structured in several packages:
* `com.example.chess.game` is the base package which contains most of the game specific logic
* `com.example.chess.game.exception` contains application specific Exceptions
* `com.example.chess.game.http` contains code related to the http interface, such as the REST controller and various
request/response objects.
* `com.example.chess.game.model` contains mostly data holding objects or enums representing various entities within 
the game.
* `com.example.chess.game.piece` contains all code related to pieces, which ones are there and how they can move 
around the board
* `com.example.chess.game.util` some utilities, for creating a new game board and printing a game in text format.

## Functional limitations
* Not all end game situations can be detected properly, currently only the situation where the king piece is unable to
move to a safe location is covered. There are some other situations which can still be implemented, some of these
situations are listed below but there are probably more because apparently chess is pretty complicated. Some
situations listed below have (disabled) unit tests:
    * Moving a piece between the king and the opposing piece that can capture the king.
    * Capturing the opposing piece that is able to capture the king. 
* Authentication and authorization

## Metrics
Some basic metrics are exposed with Spring Boot Actuator on the endpoint http://localhost:8080/actuator. 

Some information about http requests are present on the endpoint 
http://localhost:8080/actuator/metrics/http.server.requests.
Having more fine-grained metrics on the performance of specific paths would be valuable to add.
