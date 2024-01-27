package UI;

import javax.swing.*;
import java.awt.*;


/**
 * Label used to display the player's score.
 */
public class Score extends JLabel
{
    /**
     * The class constructor.
     * <pre>{@code
     * Score label = new Score();
     * }</pre>
     */
    public Score()
    {
        super();
        this.setText("0 pts");
        this.setFont(new Font("Arial", Font.PLAIN, 30));
    }


    /**
     * Changes the score displayed.
     * @param score The new score.
     * <pre>{@code
     * Score label = new Score();
     * label.changeScore(20);
     * }</pre>
     */
    public void changeScore(int score)
    {
        this.setText(score + " pts");
    }
}
