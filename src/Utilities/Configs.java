package Utilities;

import Components.TileVariant;
import Components.TokenVariants;
import java.awt.*;
import java.util.Random;


/**
 * Contains all static variables used in the project.
 */
public class Configs
{
    /**
     * The width in pixels of the window.
     */
    public static int WINDOW_WIDTH = 1080;


    /**
     * The height in pixels of the window.
     */
    public static int WINDOW_HEIGHT = 1180;


    /**
     * The window's background color.
     */
    public static Color BACKGROUND_COLOR = Color.decode("#121212");


    ///////////////////////////////////////////////////////
    /// Board
    ///////////////////////////////////////////////////////


    /**
     * The amount of columns on the board.
     */
    public static int BOARD_WIDTH = 21;


    /**
     * The amount of rows on the board.
     */
    public static int BOARD_HEIGHT = 21;


    /**
     * The space between axes for the wrap-around effect.
     */
    public static int EMPTY_SPACE = 5;


    ///////////////////////////////////////////////////////
    /// Tiles
    ///////////////////////////////////////////////////////


    /**
     * Calculate the board tile's sizes. The size is based on the window's minimum dimension
     * and the board's maximum dimension.
     */
    public static int TILE_SIZE = Math.min(WINDOW_HEIGHT, WINDOW_WIDTH) / Math.max(BOARD_WIDTH, BOARD_HEIGHT) - 2;


    /**
     * The walls' color.
     */
    public static Color WALL_COLOR = Color.decode("#154360");


    /**
     * The empty tile's color.
     */
    public static Color EMPTY_COLOR = Color.decode("#212121");


    /**
     * The spawn tile's color.
     */
    public static Color SPAWN_COLOR = Color.decode("#212121");


    /**
     * The ghost-spawn tile's color.
     */
    public static Color GHOST_SPAWN_COLOR = Color.decode("#212121");


    /**
     * Return the tile's color depending on its variant.
     * @param tile The tile variant
     * @return the tile's color.
     * <pre>{@code
     * Color color = Utils.getTileColor(TileVariant.SPAWN);
     * }</pre>
     */
    public static Color getTileColor(TileVariant tile)
    {
        return switch (tile)
        {
            case WALL -> WALL_COLOR;
            case EMPTY -> EMPTY_COLOR;
            case SPAWN -> SPAWN_COLOR;
            case GHOST_SPAWN -> GHOST_SPAWN_COLOR;
        };
    }


    ///////////////////////////////////////////////////////
    /// Entities
    ///////////////////////////////////////////////////////


    /**
     * The ghost's size in pixels.
     */
    public static int GHOST_SIZE = TILE_SIZE;


    /**
     * The ghost's default color.
     */
    public static Color GHOST_NORMAL_COLOR = Color.decode("#b63ab8");


    /**
     * The ghost's color under the afraid state.
     */
    public static Color GHOST_AFRAID_COLOR = Color.decode("#0000ff");


    /**
     * The token's size in pixel.
     */
    public static int TOKEN_SIZE = (int)(TILE_SIZE * 0.3);


    /**
     * The ghost's eyes color.
     */
    public static Color GHOST_EYE_COLOR = Color.decode("#ffffff");


    /**
     * The ghost eye's sizes color.
     */
    public static int GHOST_EYE_SIZE = (int)(GHOST_SIZE * 0.15);


    /**
     * Returns the token's color depending on its variant.
     * @param token The token variant.
     * @return the token's color depending on its variant.
     * <pre>{@code
     * Color color = Utils.getTokenColor(TokenVariants.BLUE);
     * }</pre>
     */
    public static Color getTokenColor(TokenVariants token)
    {
        return switch (token)
        {
            case BLUE -> Color.decode("#2980b9");
            case GREEN -> Color.decode("#1e8449");
            case ORANGE -> Color.decode("#d35400");
            case VIOLET -> Color.decode("#fd89ff");
            case NONE -> Color.RED;
        };
    }


    /**
     * Creates a random token.
     * @return A token.
     * <pre>{@code
     * TokenVariants variant = Utils.getRandomToken();
     * }</pre>
     */
    public static TokenVariants getRandomToken()
    {
        Random rand = new Random();
        try
        {
            switch (rand.nextInt(30))
            {
                case 1:
                    return TokenVariants.GREEN;
                case 2:
                    return TokenVariants.ORANGE;
                case 3:
                    return TokenVariants.VIOLET;
                default:
                    return TokenVariants.BLUE;
            }
        } catch (Exception ignored) {};
        return TokenVariants.BLUE;
    }


    ///////////////////////////////////////////////////////
    /// PLAYER
    ///////////////////////////////////////////////////////


    /**
     * The player's size in pixel.
     */
    public static int PLAYER_SIZE = TILE_SIZE;


    /**
     * The player's default color.
     */
    public static Color PLAYER_COLOR = Color.decode("#ffff00");


    /**
     * The player's color under the invisibility state.
     */
    public static Color PLAYER_INVISIBILITY_COLOR = Color.decode("#d4ac0d");


    /**
     * The player's color under the super state.
     */
    public static Color PLAYER_SUPER_COLOR = Color.decode("#d35400");


    /**
     * The player eye's color.
     */
    public static Color PLAYER_EYE_COLOR = Color.decode("#ffffff");


    /**
     * The player eye's size in pixel.
     */
    public static int PLAYER_EYE_SIZE = (int)(PLAYER_SIZE * 0.3);


    ///////////////////////////////////////////////////////
    /// GAME
    ///////////////////////////////////////////////////////


    /**
     * Refresh rate of the game (in ms).
     * The pattern is (1000.0 / [refresh per seconds]).
     */
    public static long FPS = (long)(1000.0 / 3.0);


    /**
     * The duration of effects in turns.
     */
    public static int EVENT_TIMER = 5;


    /**
     * The player's initial lives remaining.
     */
    public static int PLAYER_LIVES = 3;
}
