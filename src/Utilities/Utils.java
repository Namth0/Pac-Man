package Utilities;

import Components.GhostStates;
import Components.PacmanStates;
import Components.TokenVariants;
import Entities.Ghost;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * Utility class. Contains useful functions and enums.
 */
public final class Utils
{
    /**
     * Check if the ghost has to randomly change direction.
     * @return true if the ghost has to randomly change direction.
     * <pre>{@code
     * Boolean bool = Utils.changeDirection();
     * }</pre>
     */
    public static Boolean changeDirection()
    {
        return new Random().nextInt(10) == 1;
    }


    /**
     * The available directions. The STILL variant is here to prevent bug.
     */
    public enum Moving
    {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        STILL
    }


    /**
     * Returns the given token's value.
     * @param token The given token.
     * @return the given token's value.
     * <pre>{@code
     * int score = Utils.getTokenScore(TokenVariants.BLUE);
     * }</pre>
     */
    public static int getTokenScore(TokenVariants token)
    {
        return switch (token)
        {
            case VIOLET -> 300;
            case BLUE -> 100;
            case NONE -> 0;
            case ORANGE -> 500;
            case GREEN -> 1000;
        };
    }


    /**
     * Returns the player color depending on the given state.
     * @param state The player's state.
     * @return the player color depending on the given state.
     * <pre>{@code
     * Color color = Utils.getPlayerColor(PacmanStates.Super);
     * }</pre>
     */
    public static Color getPlayerColor(PacmanStates state)
    {
        return switch (state)
        {
            case Normal -> Configs.PLAYER_COLOR;
            case Super -> Configs.PLAYER_SUPER_COLOR;
            case Invisible -> Configs.PLAYER_INVISIBILITY_COLOR;
        };
    }


    /**
     * Returns the ghost color depending on the given state.
     * @param state The ghost's state.
     * @return the ghost color depending on the given state.
     * <pre>{@code
     * Color color = Utils.getGhostColor(GhostStates.Afraid);
     * }</pre>
     */
    public static Color getGhostColor(GhostStates state)
    {
        return switch (state)
        {
            case Normal -> Configs.GHOST_NORMAL_COLOR;
            case Afraid -> Configs.GHOST_AFRAID_COLOR;
        };
    }


    /**
     * Creates new ghosts.
     * @return a list of new ghosts.
     * <pre>{@code
     * ArrayList<Entities.Ghost> ghosts = Utils.generateGhosts();
     * }</pre>
     */
    public static ArrayList<Entities.Ghost> generateGhosts()
    {
        ArrayList<Entities.Ghost> ghosts = new ArrayList<>();
        ghosts.add(new Ghost());
        ghosts.add(new Ghost());
        ghosts.add(new Ghost());
        ghosts.add(new Ghost());
        return ghosts;
    }
}
