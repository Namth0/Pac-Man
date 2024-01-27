package Entities;

import Components.TileVariant;
import Utilities.Configs;
import javax.swing.*;
import java.awt.*;


/**
 * The tile's UI.
 */
public class Tile extends JPanel
{
    /**
     * The tile's current variant.
     */
    private final TileVariant tileType;


    /**
     * The class' constructor.
     * @param tileType The tile variant.
     * <pre>{@code
     * Tile tile = new Tile(TileVariant.WALL);
     * }</pre>
     */
    public Tile(TileVariant tileType)
    {
        super();
        this.tileType = tileType;
        this.setPreferredSize(new Dimension(Configs.TILE_SIZE, Configs.TILE_SIZE));
    }


    /**
     * Returns the tile's variant.
     * @return the tile's variant.
     * <pre>{@code
     * Tile tile = new Tile(TileVariant.WALL);
     * TileVariant tile = tile.getTileType();
     * }</pre>
     */
    public TileVariant getTileType()
    {
        return this.tileType;
    }


    /**
     * Draws the tile.
     * @param g  the <code>Graphics</code> context in which to paint
     * <pre>{@code
     * Tile tile = new Tile(TileVariant.WALL);
     * tile.repaint();
     * }</pre>
     */
    @Override
    public void paint(Graphics g)
    {
        g.setColor(Configs.getTileColor(this.tileType));
        g.fillRect(0, 0, Configs.TILE_SIZE, Configs.TILE_SIZE);
    }
}