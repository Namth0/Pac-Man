package Logic;

import Components.GhostStates;
import Components.PacmanStates;
import Components.TileVariant;
import Entities.Ghost;
import Entities.Pacman;
import Scenes.SceneGenerator;
import Utilities.Configs;
import Utilities.Tuples.Couple;
import Utilities.Utils;
import Utilities.Tuples.Triplet;

import java.util.*;
import java.util.List;


/**
 * The game class.
 */
public class GameManager
{
    /**
     * Event timer keeping track of any event duration.
     */
    private int eventTimer = 0;


    /**
     * The player's score.
     */
    private int score = 0;


    /**
     * The player's lost lives.
     */
    private int playerLostLives = 0;


    /**
     * The remaining tokens on the board.
     */
    private int tokensRemaining;


    /**
     * Flag notifying if the board has been shuffled.
     */
    private Boolean isBoardShuffled = false;


    /**
     * The board.
     */
    private Logic.Tile[][] board;


    /**
     * A temporary board used when the main one has been shuffled.
     */
    private Logic.Tile[][] tempBoard;


    /**
     * Used to generate boards.
     */
    private final SceneGenerator sceneGenerator = new SceneGenerator();


    /**
     * The player's last coordinates on the board.
     */
    private Couple<Integer, Integer> lastPlayerPosition = null;


    /**
     * The class' constructor.
     * <pre>{@code
     * GameManager game = new GameManager();
     * }</pre>
     */
    public GameManager()
    {
        ArrayList<Ghost> ghosts = Utils.generateGhosts();
        Pacman p = new Pacman();
        this.board = Logic.Tile.fromGeneratedBoard(sceneGenerator.generateRandomBoard(), ghosts, p);
        this.setInitialPacmanPosition();
        this.countTokens();
    }


    /**
     * Shuffles the board. A new board is created and tokens are removed from it util the total count
     * matches the current board's amount.
     * <pre>{@code
     * this.shuffleBoard();
     * }</pre>
     */
    private void shuffleBoard()
    {
        ArrayList<Ghost> ghosts = Utils.generateGhosts();
        Pacman p = new Pacman();
        this.tempBoard = Logic.Tile.fromGeneratedBoard(sceneGenerator.generateRandomBoard(), ghosts, p);
        int tokens = 0;
        for (Tile[] row : this.tempBoard) for (Tile tile : row) if (tile.getToken() != null) tokens++;
        Random rd = new Random();
        int x, y;
        while (tokens > this.tokensRemaining)
        {
            try
            {
                x = rd.nextInt(this.tempBoard.length - 1);
                y = rd.nextInt(this.tempBoard[0].length - 1);
            }
            catch (Exception e) { continue; }
            if (this.tempBoard[x][y].getToken() != null)
            {
                this.tempBoard[x][y].setToken(null);
                tokens--;
            }
        }
        this.setInitialPacmanPosition();
        this.setBoardShuffled(true);
    }


    /**
     * Reset the last player position var to the origin.
     * <pre>{@code
     * setInitialPacmanPosition();
     * }</pre>
     */
    private void setInitialPacmanPosition()
    {
        for (int row = 0; row < this.board.length; row++)
        {
            for (int col = 0; col < this.board[row].length; col++)
            {
                if (this.board[row][col].getTile().getTileType() == TileVariant.SPAWN)
                {
                    this.lastPlayerPosition = new Couple<>(row, col);
                    return;
                }
            }
        }
    }


    /**
     * Switch the current board with the temporary one.
     * <pre>{@code
     * this.toggleBoard();
     * }</pre>
     */
    public void toggleBoard()
    {
        this.board = tempBoard;
    }


    /**
     * Counts the tokens remaining on the board.
     * <pre>{@code
     * this.countTokens();
     * }</pre>
     */
    private void countTokens()
    {
        this.tokensRemaining = 0;
        for(Logic.Tile[] row : this.board)
            for (Logic.Tile tile : row)
                if (tile.getToken() != null) this.tokensRemaining++;
    }


    /**
     * Moves the player on the board according the given direction.
     * @param direction the player's direction.
     * <pre>{@code
     * GameManager game = new GameManager();
     * game.movePlayer(Utils.Moving.UP);
     * }</pre>
     */
    public void movePlayer(Utils.Moving direction)
    {
        if (direction == Utils.Moving.STILL) return;
        Triplet<Pacman, Integer, Integer> pacman = null;
        outer: for (int r = 0; r < this.board.length; r++)
        {
            for (int c = 0; c < this.board[r].length; c++)
            {
                if (this.board[r][c].getPlayer() != null)
                {
                    pacman = new Triplet<>(this.board[r][c].getPlayer(), r, c);
                    this.board[r][c].setPlayer(null);
                    break outer;
                }
            }
        }
        if (pacman == null) return;
        if (!this.isPlayerDirectionValid(pacman.item2, pacman.item3, direction))
        {
            pacman.item1.changeDirection(Utils.Moving.STILL);
            this.board[pacman.item2][pacman.item3].setPlayer(pacman.item1);
            return;
        }
        moveOnBoard(pacman, direction);
    }


    /**
     * Moves the player on the board according to the validated direction.
     * @param pacman the player and it's coordinates.
     * @param direction The direction.
     * <pre>{@code
     * Triplet<Pacman, Integer, Integer> pacman = ...;
     * Utils.Moving direction = Utils.Moving.UP;
     * moveOnBoard(pacman, direction);
     * }</pre>
     */
    private void moveOnBoard(Triplet<Pacman, Integer, Integer> pacman, Utils.Moving direction)
    {
        switch (direction)
        {
            case UP -> this.applyChanges(
                pacman.item2,
                (pacman.item3 - 1 < 0) ? (this.board[pacman.item2].length - 1) : (pacman.item3 - 1),
                pacman.item1
            );
            case DOWN -> this.applyChanges(
                pacman.item2,
                (pacman.item3 + 1 >= this.board[pacman.item2].length) ? 0 : (pacman.item3 + 1),
                pacman.item1
            );
            case LEFT -> this.applyChanges(
                (pacman.item2 - 1) < 0 ? (this.board.length - 1) : (pacman.item2 - 1),
                pacman.item3,
                pacman.item1
            );
            case RIGHT -> this.applyChanges(
                (pacman.item2 + 1) >= this.board.length ? 0 : (pacman.item2 + 1),
                pacman.item3,
                pacman.item1
            );
        }
    }


    /**
     * Checks if the player can move following the given direction.
     * @param row The player's row index.
     * @param col The player's column index
     * @param direction The direction.
     * @return true if the player can move following the given direction.
     * <pre>{@code
     * int row = 0;
     * int col = 0;
     * Utils.Moving direction = Utils.Moving.UP;
     * Boolean bool = isPlayerDirectionValid(row, col, direction);
     * }</pre>
     */
    public Boolean isPlayerDirectionValid(int row, int col, Utils.Moving direction)
    {
        if (row < 0 || row >= this.board.length) return false;
        if (col < 0 || col >= this.board[row].length) return false;
        return switch (direction)
        {
            case STILL -> true;
            case LEFT -> (row - 1 < 0)
                ? this.board[this.board.length - 1][col].getTile().getTileType() != TileVariant.WALL
                : this.board[row - 1][col].getTile().getTileType() != TileVariant.WALL;
            case RIGHT -> (row + 1 >= this.board.length)
                ? this.board[0][col].getTile().getTileType() != TileVariant.WALL
                : this.board[row + 1][col].getTile().getTileType() != TileVariant.WALL;
            case UP -> (col - 1 < 0)
                ? this.board[row][this.board[row].length - 1].getTile().getTileType() != TileVariant.WALL
                : this.board[row][col].getTile().getTileType() != TileVariant.WALL;
            case DOWN -> (col + 1 >= this.board[row].length)
                ? this.board[row][this.board[row].length - 1].getTile().getTileType() != TileVariant.WALL
                : this.board[row][col].getTile().getTileType() != TileVariant.WALL;
        };
    }


    /**
     * Applies all changes on the board with the player's and ghosts' new positions.
     * @param r The row index of the player's position.
     * @param c The column index of the player's position.
     * @param player The player.
     * <pre>{@code
     * Pacman player = new Pacman();
     * this.applyChanges(10, 20, player);
     * }</pre>
     */
    private void applyChanges(int r, int c, Pacman player)
    {
        if (this.eventTimer > 0) this.eventTimer--;
        if (this.board[r][c].getTile().getTileType() == TileVariant.WALL)
            this.board[this.lastPlayerPosition.item1][this.lastPlayerPosition.item2].setPlayer(player);
        else
        {
            this.board[r][c].setPlayer(player);
            this.lastPlayerPosition = new Couple<Integer, Integer>(r, c);
        }
        if (this.board[r][c].getToken() != null)
        {
            this.score += Utils.getTokenScore(this.board[r][c].getToken().getToken());
            switch (this.board[r][c].getToken().getToken())
            {
                case GREEN ->
                {
                    changePlayerState(PacmanStates.Normal);
                    changeGhostsStates(GhostStates.Normal);
                    shuffleBoard();
                    return;
                }
                case VIOLET ->
                {
                    this.eventTimer = Configs.EVENT_TIMER;
                    changePlayerState(PacmanStates.Invisible);
                    changeGhostsStates(GhostStates.Normal);
                }
                case ORANGE ->
                {
                    this.eventTimer = Configs.EVENT_TIMER;
                    changePlayerState(PacmanStates.Super);
                    changeGhostsStates(GhostStates.Afraid);
                }
            }
            this.tokensRemaining--;
        }
        Ghost ghost;
        if (this.board[r][c].getGhost() != null)
            if (player.getState() == PacmanStates.Normal) this.playerLostLives++;
            else if (player.getState() == PacmanStates.Super)
                while((ghost = this.board[r][c].popGhost()) != null)
                    this.board[this.board.length / 2][this.board[0].length / 2].addGhost(ghost);

        if (this.eventTimer <= 0)
        {
            changePlayerState(PacmanStates.Normal);
            changeGhostsStates(GhostStates.Normal);
        }
    }


    /**
     * Changes all ghosts' states.
     * @param state The new state.
     * <pre>{@code
     * this.changeGhostsStates(GhostStates.Afraid);
     * }</pre>
     */
    private void changeGhostsStates(GhostStates state)
    {
        for (Logic.Tile[] row : this.board)
            for (Logic.Tile tile : row)
                for (Ghost ghost : tile.getGhosts()) ghost.changeState(state);
    }


    /**
     * Changes the player's state.
     * @param state The new state.
     * <pre>{@code
     * this.changePlayerState(PacmanStates.Super);
     * }</pre>
     */
    private void changePlayerState(PacmanStates state)
    {
        outer: for (Logic.Tile[] row : this.board)
        for (Logic.Tile tile : row)
        if (tile.getPlayer() != null)
        {
            tile.getPlayer().changeState(state);
            break outer;
        }
    }


    /**
     * Moves ghost on the board. Each ghost's current direction is prioritised. Each ghost can randomly change
     * direction.
     * <pre>{@code
     * GameManager game = new GameManager();
     * game.moveGhosts();
     * }</pre>
     */
    public void moveGhosts()
    {
        ArrayList<Triplet<Ghost, Integer, Integer>> ghosts = new ArrayList<>();
        Ghost ghost;
        for (int r = 0; r < this.board.length; r++)
            for (int c = 0; c < this.board[r].length; c++)
                while((ghost = this.board[r][c].popGhost()) != null)
                    ghosts.add(new Triplet<>(ghost, r, c));

        for (Triplet<Ghost, Integer, Integer> tuple: ghosts)
        {
            if (tuple.item1.getIsFrozen())
            {
                tuple.item1.toggleIsFrozen();
                this.board[tuple.item2][tuple.item3].addGhost(tuple.item1);
                continue;
            }
            if (!this.canContinueMoving(tuple.item2, tuple.item3, tuple.item1) || Utils.changeDirection())
                tuple.item1.setDirection(this.newDirection(tuple.item2, tuple.item3, tuple.item1.getDirection()));
            this.moveGhost(tuple.item2, tuple.item3, tuple.item1);
            if (tuple.item1.getState() == GhostStates.Afraid) tuple.item1.toggleIsFrozen();
        }
    }


    /**
     * Checks if the player can move following the given direction.
     * @param direction The direction to check.
     * @return true if the player can move following the given direction.
     * <pre>{@code
     * GameManager game = new GameManager();
     * Boolean bool = game.canPlayerMove(Utils.Moving.LEFT);
     * }</pre>
     */
    public Boolean canPlayerMove(Utils.Moving direction)
    {
        int r = -1, c = -1;
        outer: for(int row = 0; row < this.board.length; row++)
        for (int col = 0; col < this.board[row].length; col++)
        if (this.board[row][col].getPlayer() != null)
        {
            r = row;
            c = col;
            break outer;
        }
        return isDirectionValid(r, c, direction);
    }


    /**
     * Checks if the ghost given can continue moving following its current direction from the tile specified.
     * @param r The specified tile's row index.
     * @param c The specified tile's row index.
     * @param ghost The ghost.
     * @return true if the ghost given can continue moving following its current direction from the tile specified.
     * <pre>{@code
     * Ghost ghost = new Ghost();
     * Boolean bool = this.canContinueMoving(10, 5, ghost);
     * }</pre>
     */
    private Boolean canContinueMoving(int r, int c, Ghost ghost)
    {
        return isDirectionValid(r, c, ghost.getDirection());
    }


    /**
     * Extracts the player form the board and returns it.
     * @return The extracted player.
     * <pre>{@code
     * GameManager game = new GameManager();
     * Pacman player = game.getPlayer();
     * }</pre>
     */
    public Pacman getPlayer()
    {
        for (Tile[] row : this.board)
            for (Tile tile : row)
                if (tile.getPlayer() != null) return tile.getPlayer();
        return null;
    }


    /**
     * Checks if the given direction is valid from the specified tile.
     * @param r The specified tile's row index.
     * @param c The specified tile's column index.
     * @param direction The given direction.
     * @return true if the given direction is valid from the specified tile.
     * <pre>{@code
     * Boolean bool = this.isDirectionValid(10, 5, Utils.Moving.RIGHT);
     * }</pre>
     */
    private Boolean isDirectionValid(int r, int c, Utils.Moving direction)
    {
        int row = r, col = c;
        switch (direction)
        {
            case STILL -> { return false; }
            case LEFT -> row = (r - 1 < 0) ? (board.length - 1) : (r - 1);
            case RIGHT -> row = (r + 1 >= board.length) ? 0 : (r + 1);
            case UP -> col = (c - 1 < 0) ? (board[r].length - 1) : (c - 1);
            case DOWN -> col = (c + 1 >= board[r].length) ? 0 : (c + 1);
        };
        return board[row][col].getTile().getTileType() != TileVariant.WALL;
    }


    /**
     * Moves the given ghost from the specified tile following its direction.
     * @param r The specified tile's row index.
     * @param c The specified tile's column index.
     * @param ghost The ghost.
     * <pre>{@code
     * Ghost ghost = new Ghost();
     * this.moveGhost(10, 5, ghost);
     * }</pre>
     */
    private void moveGhost(int r, int c, Ghost ghost)
    {
        switch (ghost.getDirection())
        {
            case LEFT -> this.board[(r - 1 < 0) ? (this.board.length - 1) : (r - 1)][c].addGhost(ghost);
            case RIGHT -> this.board[(r + 1 >= this.board.length) ? 0 : (r + 1)][c].addGhost(ghost);
            case UP -> this.board[r][(c - 1 < 0) ? (this.board[r].length - 1) : (c - 1)].addGhost(ghost);
            case DOWN -> this.board[r][(c + 1 >= this.board[r].length) ? 0 : (c + 1)].addGhost(ghost);
            case STILL ->
            {
                r = (r < 0) ? (this.board.length - 1) : (r >= this.board.length) ? 0 : r;
                c = (c < 0) ? (this.board[r].length - 1) : (c >= this.board[r].length) ? 0 : c;
                this.board[r][c].addGhost(ghost);
            }
        }
    }


    /**
     * Selects a new direction for a ghost.
     * @param r The specified tile's row index.
     * @param c The specified tile's column index.
     * @param lastDirection The last direction.
     * @return a new direction.
     * <pre>{@code
     * Utils.Moving direction = this.newDirection(10, 5, Utils.Moving.DOWN);
     * }</pre>
     */
    private Utils.Moving newDirection(int r, int c, Utils.Moving lastDirection)
    {
        if (lastDirection == Utils.Moving.STILL) return lastDirection;
        Utils.Moving[] directions = new Utils.Moving[]
        {
            Utils.Moving.UP, Utils.Moving.RIGHT, Utils.Moving.DOWN, Utils.Moving.LEFT
        };
        List<Utils.Moving> temp = Arrays.asList(directions);
        Collections.shuffle(temp);
        temp.toArray(directions);

        for (Utils.Moving direction: directions)
        {
            if (direction == lastDirection) continue;
            if (direction == Utils.Moving.RIGHT && this.tileValidForMovement(r + 1, c))
                return Utils.Moving.RIGHT;
            if (direction == Utils.Moving.LEFT && this.tileValidForMovement(r - 1, c))
                return Utils.Moving.LEFT;
            if (direction == Utils.Moving.UP && this.tileValidForMovement(r, c - 1))
                return Utils.Moving.UP;
            if (direction == Utils.Moving.DOWN && this.tileValidForMovement(r, c + 1))
                return Utils.Moving.DOWN;
        }
        return Utils.Moving.STILL;
    }


    /**
     * Checks if the specified tile is valid.
     * @param r The specified tile's row index.
     * @param c The specified tile's column index.
     * @return true if the specified tile is valid.
     * <pre>{@code
     * Boolean bool = this.tileValidForMovement(10, 5);
     * }</pre>
     */
    private Boolean tileValidForMovement(int r, int c)
    {
        if (r < 0 || r >= this.board.length) return false;
        return c >= 0 && c < this.board[r].length &&
            this.board[r][c].getTile().getTileType() != TileVariant.WALL;
    }


    /**
     * Returns the board.
     * @return the board.
     * <pre>{@code
     * GameManager game = new GameManager();
     * Tile[][] board = game.getBoard();
     * }</pre>
     */
    public Tile[][] getBoard()
    {
        return this.board;
    }


    /**
     * Returns the player's score.
     * @return the player's score.
     * <pre>{@code
     * GameManager game = new GameManager();
     * int score = game.getScore();
     * }</pre>
     */
    public int getScore()
    {
        return this.score;
    }


    /**
     * Returns the player's lives.
     * @return the player's lives.
     * <pre>{@code
     * GameManager game = new GameManager();
     * int lives = game.getLives();
     * }</pre>
     */
    public int getLives()
    {
        return Configs.PLAYER_LIVES + this.score / 5000 - this.playerLostLives;
    }


    /**
     * Returns the remaining tokens.
     * @return the remaining tokens.
     * <pre>{@code
     * GameManager game = new GameManager();
     * int remainingTokens = game.getRemainingTokens();
     * }</pre>
     */
    public int getRemainingTokens()
    {
        return tokensRemaining;
    }


    /**
     * Indicates if the board has been shuffled.
     * @return true if the board has been shuffled.
     * <pre>{@code
     * GameManager game = new GameManager();
     * Boolean bool = game.getBoardShuffled();
     * }</pre>
     */
    public Boolean getBoardShuffled()
    {
        return isBoardShuffled;
    }


    /**
     * Set the shuffled-board flag to the given value.
     * @param boardShuffled The new value.
     * <pre>{@code
     * this.setBoardShuffled(true);
     * }</pre>
     */
    public void setBoardShuffled(Boolean boardShuffled)
    {
        this.isBoardShuffled = boardShuffled;
    }
}
