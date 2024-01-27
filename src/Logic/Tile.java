package Logic;

import Components.TileVariant;
import Components.TokenVariants;
import Entities.Ghost;
import Entities.Pacgomme;
import Entities.Pacman;
import Utilities.Configs;
import java.util.*;


/**
 * The tile class.
 */
public class Tile
{
    /**
     * The Front-end tile.
     */
    private Entities.Tile tile;


    /**
     * The ghosts on the tile.
     */
    private ArrayList<Ghost> ghostsOnTile;


    /**
     * The player on the tile.
     */
    private Pacman player;


    /**
     * The token on the tile.
     */
    private Pacgomme token;


    /**
     * The class' constructor. Initialized with only one ghost.
     * @param tile The tile component.
     * @param ghost The ghost on the tile.
     * @param player The player on the tile.
     * @param token The token on the tile.
     * <pre>{@code
     * Ghost ghost = new Ghost();
     * Pacman player = new Pacman();
     * Pacgomme token = new Pacgomme(TokenVariants.BLUE);
     * Entities.Tile frontTile = new Entities.Tile(TileVariant.WALL);
     * Logic.Tile tile = new Logic.Tile(frontTile, ghost, player, token);
     * }</pre>
     */
    public Tile(Entities.Tile tile, Ghost ghost, Pacman player, Pacgomme token)
    {
        this.tile = tile;
        this.ghostsOnTile = new ArrayList<>();
        this.ghostsOnTile.add(ghost);
        this.player = player;
        this.token = token;
    }


    /**
     * The class' constructor. Initialized with no ghosts and no player on it.
     * @param tile The tile component.
     * @param token The token on the tile.
     * <pre>{@code
     * Entities.Tile frontTile = new Entities.Tile(TileVariant.WALL);
     * Pacgomme token = new Pacgomme(TokenVariants.BLUE);
     * Logic.Tile tile = new Logic.Tile(frontTile, token);
     * }</pre>
     */
    public Tile(Entities.Tile tile, Pacgomme token)
    {
        this.tile = tile;
        this.ghostsOnTile = new ArrayList<>();
        this.player = null;
        this.token = token;
    }


    /**
     * Removes and returns a ghost from a ghost list.
     * @param ghosts The ghost list.
     * @return returns a ghost from a ghost list.
     * <pre>{@code
     * ArrayList<Ghost> ghosts = ...;
     * Ghost ghost = Logic.Tile.popGhost(ghosts);
     * }</pre>
     */
    private static Ghost popGhost(ArrayList<Ghost> ghosts)
    {
        if (ghosts.isEmpty()) return null;
        Ghost g = ghosts.get(0);
        ghosts.remove(0);
        return g;
    }


    /**
     * Removes and returns a ghost on the tile.
     * @return a ghost on the tile.
     * <pre>{@code
     * Logic.Tile tile = ...;
     * Ghost ghost = tile.popGhost();
     * }</pre>
     */
    public Ghost popGhost()
    {
        if (this.ghostsOnTile.isEmpty()) return null;
        Ghost g = this.ghostsOnTile.get(0);
        this.ghostsOnTile.remove(0);
        return g;
    }


    /**
     * Returns the tile component.
     * @return the tile component.
     * <pre>{@code
     * Logic.Tile tile = ...;
     * Entities.Tile component = tile.getTile();
     * }</pre>
     */
    public Entities.Tile getTile()
    {
        return this.tile;
    }


    /**
     * Places the given ghost on the tile.
     * @param ghost The ghost to be placed.
     * <pre>{@code
     * Logic.Tile tile = ...;
     * Ghost ghost = new Ghost();
     * tile.addGhost(ghost);
     * }</pre>
     */
    public void addGhost(Ghost ghost)
    {
        this.ghostsOnTile.add(ghost);
    }


    /**
     * Removes the token from the tile.
     * <pre>{@code
     * Logic.Tile tile = ...;
     * tile.removeToken();
     * }</pre>
     */
    public void removeToken()
    {
        this.token.setVisible(false);
        this.token = null;
    }


    /**
     * Sets a new token on the tile.
     * @param token The new token.
     * <pre>{@code
     * Logic.Tile tile = ...;
     * tile.setToken(TokenVariants.BLUE);
     * }</pre>
     */
    public void setToken(TokenVariants token)
    {
        if (token == null) this.token = null;
        else this.token = new Pacgomme(token);
    }


    /**
     * Returns the player if a player is on the tile else returns null.
     * @return the player if a player is on the tile else returns null.
     * <pre>{@code
     * Logic.Tile tile = ...;
     * Pacman player = tile.getPlayer();
     * }</pre>
     */
    public Pacman getPlayer()
    {
         return this.player;
    }


    /**
     * Checks if a player is on the tile.
     * @return true if a player is on the tile.
     * <pre>{@code
     * Logic.Tile tile = ...;
     * Boolean bool = tile.hasPlayer();
     * }</pre>
     */
    public Boolean hasPlayer()
    {
        return this.player != null;
    }


    /**
     * Sets a new player on the tile.
     * @param player The player or null if there is no player on the tile.
     * <pre>{@code
     * Logic.Tile tile = ...;
     * Pacman player = new Pacman();
     * tile.setPlayer(player);
     * tile.setPlayer(null);
     * }</pre>
     */
    public void setPlayer(Pacman player)
    {
        this.player = player;
    }


    /**
     * Returns the token on the tile.
     * @return the token on the tile.
     * <pre>{@code
     * Logic.Tile tile = ...;
     * Pacgomme token = tile.getToken();
     * }</pre>
     */
    public Pacgomme getToken()
    {
        return this.token;
    }


    /**
     * Returns the first ghost placed on the tile.
     * @return the first ghost placed on the tile.
     * <pre>{@code
     * Logic.Tile tile = ...;
     * Ghost ghost = tile.getGhost();
     * }</pre>
     */
    public Ghost getGhost()
    {
        if (this.ghostsOnTile.isEmpty()) return null;
        return this.ghostsOnTile.get(0);
    }


    /**
     * Returns the ghosts on the tile.
     * @return the ghosts on the tile.
     * <pre>{@code
     * Logic.Tile tile = ...;
     * ArrayList<Ghost> ghosts = tile.getGhosts();
     * }</pre>
     */
    public ArrayList<Ghost> getGhosts()
    {
        return this.ghostsOnTile;
    }


    /**
     * Converts a TileVariant[][] board to a Logic[][] board. Places the given ghosts and player and add random tokens.
     * @param board The initial board.
     * @param ghosts The ghosts.
     * @param player The player.
     * @return a Logic[][] board.
     * <pre>{@code
     * TileVariant[][] board = ...;
     * ArrayList<Ghost> ghosts = ...;
     * Pacman player = new Pacman();
     * Tile[][] newBoard = Logic.Tile.fromGeneratedBoard(board, ghosts, player);
     * }</pre>
     */
    public static Tile[][] fromGeneratedBoard(TileVariant[][] board, ArrayList<Ghost> ghosts, Pacman player)
    {
        Tile[][] completedBoard = new Tile[board.length][board[0].length];
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board[0].length; col++)
                completedBoard[row][col] = switch (board[row][col]) {
                    case WALL -> new Tile(new Entities.Tile(TileVariant.WALL), null);
                    case SPAWN -> new Tile(new Entities.Tile(TileVariant.SPAWN), null, player,  null);
                    case EMPTY -> new Tile(new Entities.Tile(TileVariant.EMPTY), new Pacgomme(Configs.getRandomToken()));
                    case GHOST_SPAWN -> new Tile(
                        new Entities.Tile(TileVariant.GHOST_SPAWN), popGhost(ghosts),null, null
                    );
                };
        return completedBoard;
    }
}
