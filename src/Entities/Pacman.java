package Entities;

import Components.PacmanStates;
import Components.TileVariant;
import Utilities.Configs;
import Utilities.Utils;
import javax.swing.*;
import java.awt.*;


/**
 * The player's UI.
 */
public class Pacman extends JPanel
{
    /**
     * The player's current direction.
     */
    private Utils.Moving currentDirection;


    /**
     * The player's current state.
     */
    private PacmanStates state = PacmanStates.Normal;


    /**
     * The class' constructor.
     * <pre>{@code
     * Pacman player = new Player();
     * }</pre>
     */
    public Pacman()
    {
        super();
    }


    /**
     * Draws the player.
     * @param g  the <code>Graphics</code> context in which to paint
     * <pre>{@code
     * Pacman player = new Player();
     * player.repaint();
     * }</pre>
     */
    @Override
    public void paint(Graphics g)
    {
        int size = (int)(Configs.PLAYER_SIZE * 0.9);
        int position = (Configs.TILE_SIZE - size) / 2;
        g.setColor(Utils.getPlayerColor(this.state));
        g.fillOval(position, position, size, size);
        g.setColor(Configs.PLAYER_EYE_COLOR);
        g.fillOval(Configs.PLAYER_SIZE / 6, Configs.PLAYER_SIZE / 6, Configs.PLAYER_EYE_SIZE, Configs.PLAYER_EYE_SIZE);
        g.setColor(Color.BLACK);
        g.fillOval(Configs.PLAYER_SIZE / 6, Configs.PLAYER_SIZE / 6 + Configs.PLAYER_EYE_SIZE / 4, Configs.PLAYER_EYE_SIZE / 2, Configs.PLAYER_EYE_SIZE / 2);
        g.setColor(Configs.getTileColor(TileVariant.EMPTY));
        int[] mouthX = { Configs.TILE_SIZE / 2, Configs.TILE_SIZE, Configs.TILE_SIZE  };
        int[] mouthY = { Configs.TILE_SIZE / 2, Configs.TILE_SIZE / 3, 2 * Configs.TILE_SIZE / 3  };
        g.fillPolygon(mouthX, mouthY, 3);
    }


    /**
     * Changes the player's state.
     * @param state The new state.
     * <pre>{@code
     * Pacman player = new Player();
     * player.changeState(PacmanStates.Super);
     * }</pre>
     */
    public void changeState(PacmanStates state)
    {
        this.state = state;
        this.repaint();
    }


    /**
     * Returns the player's current state.
     * @return the player's current state.
     * <pre>{@code
     * Pacman player = new Player();
     * PacmanStates state = player.getState();
     * }</pre>
     */
    public PacmanStates getState()
    {
        return this.state;
    }


    /**
     * Returns the player's current direction.
     * @return the player's current direction.
     * <pre>{@code
     * Pacman player = new Player();
     * Utils.Moving direction = player.getCurrentDirection();
     * }</pre>
     */
    public Utils.Moving getCurrentDirection()
    {
        return this.currentDirection;
    }


    /**
     * Changes the player's direction.
     * @param direction The new direction.
     * <pre>{@code
     * Pacman player = new Player();
     * player.changeDirection(Utils.Moving.UP);
     * }</pre>
     */
    public void changeDirection(Utils.Moving direction)
    {
        this.currentDirection = direction;
    }
}
