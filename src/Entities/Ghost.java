package Entities;

import Components.GhostStates;
import Utilities.Configs;
import Utilities.Utils;
import javax.swing.*;
import java.awt.*;


/**
 * The Ghost UI.
 */
public class Ghost extends JPanel
{
    /**
     * The ghost's current direction.
     */
    private Utils.Moving direction = Utils.Moving.UP;


    /**
     * The ghost's current state.
     */
    private GhostStates state = GhostStates.Normal;


    /**
     * Indicates if the ghost is frozen. Flag used under the Afraid state.
     */
    private Boolean isFrozen = false;


    /**
     * The class constructor.
     * <pre>{@code
     * Ghost ghost = new Ghost();
     * }</pre>
     */
    public Ghost()
    {
        super();
    }


    /**
     * Returns the ghost's current direction.
     * @return the ghost's current direction.
     * <pre>{@code
     * Ghost ghost = new Ghost();
     * Utils.Moving direction = ghost.getDirection();
     * }</pre>
     */
    public Utils.Moving getDirection()
    {
        return this.direction;
    }


    /**
     * Sets a new direction.
     * @param newDirection The new direction.
     * <pre>{@code
     * Ghost ghost = new Ghost();
     * ghost.setDirection(Utils.Moving.UP);
     * }</pre>
     */
    public void setDirection(Utils.Moving newDirection)
    {
        this.direction = newDirection;
    }


    /**
     * Sets a new state.
     * @param state The new state.
     * <pre>{@code
     * Ghost ghost = new Ghost();
     * ghost.changeState(GhostStates.Afraid);
     * }</pre>
     */
    public void changeState(GhostStates state)
    {
        this.state = state;
        this.repaint();
    }


    /**
     * Returns the ghost's current state.
     * @return the ghost's current state.
     * <pre>{@code
     * Ghost ghost = new Ghost();
     * GhostStates state = ghost.getState();
     * }</pre>
     */
    public GhostStates getState()
    {
        return this.state;
    }


    /**
     * Returns true if the ghost is frozen.
     * @return true if the ghost is frozen.
     * <pre>{@code
     * Ghost ghost = new Ghost();
     * Boolean bool = ghost.getIsFrozen();
     * }</pre>
     */
    public Boolean getIsFrozen()
    {
        return this.isFrozen;
    }


    /**
     * Toggles the ghost's frozen flag.
     * <pre>{@code
     * Ghost ghost = new Ghost();
     * ghost.toggleIsFrozen();
     * }</pre>
     */
    public void toggleIsFrozen()
    {
        this.isFrozen = !this.isFrozen;
    }


    /**
     * Draws the ghost.
     * @param g  the <code>Graphics</code> context in which to paint
     * <pre>{@code
     * Ghost ghost = new Ghost();
     * ghost.repaint();
     * }</pre>
     */
    @Override
    public void paint(Graphics g)
    {
        Color color = Utils.getGhostColor(this.state);
        int sphereSize = Configs.GHOST_SIZE / 2;
        int p = (Configs.GHOST_SIZE - sphereSize) / 2;
        g.setColor(color);
        g.fillOval(p, p / 2, sphereSize, sphereSize);
        g.fillRect(p, (int)((p + sphereSize) / 2), sphereSize, sphereSize);
        int s = sphereSize / 3;
        g.setColor(Configs.GHOST_EYE_COLOR);
        g.fillOval(2 * Configs.GHOST_SIZE / 6, (int)(1.2 * p), Configs.GHOST_EYE_SIZE, Configs.GHOST_EYE_SIZE);
        g.fillOval(
            2 * Configs.GHOST_SIZE / 6 + (int)(1.5 * Configs.GHOST_EYE_SIZE),
            (int)(1.2 * p),
            Configs.GHOST_EYE_SIZE,
            Configs.GHOST_EYE_SIZE
        );
        g.setColor(Color.BLACK);
        g.fillOval(
            Configs.GHOST_EYE_SIZE / 4 + 2 * Configs.GHOST_SIZE / 5,
            Configs.GHOST_EYE_SIZE / 4 + (int)(1.3 * p),
            Configs.GHOST_EYE_SIZE / 2,
            Configs.GHOST_EYE_SIZE / 2
        );
        g.fillOval(
            2 * Configs.GHOST_SIZE / 5 + (int)(1.5 * Configs.GHOST_EYE_SIZE) + Configs.GHOST_EYE_SIZE / 4,
            (int)(1.3 * p) + Configs.GHOST_EYE_SIZE / 4,
            Configs.GHOST_EYE_SIZE / 2,
            Configs.GHOST_EYE_SIZE / 2
        );
    }
}
