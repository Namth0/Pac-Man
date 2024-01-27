package UI;

import javax.swing.*;
import java.awt.*;


/**
 * Label used to display the player's lives.
 */
public class Lives extends JLabel
{
    /**
     * The class constructor.
     * <pre>{@code
     * Lives label = new Lives(3);
     * }</pre>
     */
    public Lives(int lives)
    {
        super();
        this.setText("♥ x" + lives);
        this.setFont(new Font("Arial", Font.PLAIN, 30));
        this.setForeground(Color.RED);
    }


    /**
     * Changes the player's lives displayed.
     * @param lives The new amount of lives.
     * <pre>{@code
     * Lives label = new Lives(3);
     * label.changeLives(4);
     * }</pre>
     */
    public void changeLives(int lives)
    {
        this.setText("♥ x" + lives);
    }
}
