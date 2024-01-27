package Entities;


import Components.TokenVariants;
import Utilities.Configs;
import javax.swing.*;
import java.awt.*;


/**
 * The token UI.
 */
public class Pacgomme extends JPanel
{
    /**
     * The token's variant.
     */
    private final TokenVariants token;


    /**
     * The class' constructor.
     * @param token The token variant.
     * <pre>{@code
     * Pacgomme token = new Pacgomme(TokenVariants.BLUE);
     * }</pre>
     */
    public Pacgomme(TokenVariants token)
    {
        super();
        this.token = token;
    }


    /**
     * Returns the token's variant.
     * @return the token's variant.
     * <pre>{@code
     * Pacgomme token = new Pacgomme(TokenVariants.BLUE);
     * TokenVariants variant = token.getToken();
     * }</pre>
     */
    public TokenVariants getToken()
    {
        return this.token;
    }


    /**
     * Draws the token.
     * @param g  the <code>Graphics</code> context in which to paint.
     * <pre>{@code
     * Pacgomme token = new Pacgomme(TokenVariants.BLUE);
     * token.repaint();
     * }</pre>
     */
    @Override
    public void paint(Graphics g)
    {
        g.setColor(Configs.getTokenColor((this.token == null) ? TokenVariants.NONE : this.token));
        int p = (Configs.TILE_SIZE - Configs.TOKEN_SIZE) / 2;
        g.fillOval(p, p, Configs.TOKEN_SIZE, Configs.TOKEN_SIZE);
    }
}
