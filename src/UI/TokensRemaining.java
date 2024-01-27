package UI;

import javax.swing.*;
import java.awt.*;


/**
 * Label used to display the tokens remaining on the board.
 */
public class TokensRemaining extends JLabel
{

    /**
     * The class constructor.
     * @param tokensRemaining The initial amount of tokens.
     * <pre>{@code
     * TokensRemaining label = new TokensRemaining(10);
     * }</pre>
     */
    public TokensRemaining(int tokensRemaining)
    {
        super();
        this.setText("♦x " + tokensRemaining);
        this.setFont(new Font("Arial", Font.PLAIN, 30));
        this.setForeground(Color.GREEN);
    }


    /**
     * Changes the amount of tokens displayed.
     * @param tokensRemaining The new amount of tokens.
     * <pre>{@code
     * TokensRemaining label = new TokensRemaining(10);
     * label.changeText(20);
     * }</pre>
     */
    public void changeText(int tokensRemaining)
    {
        this.setText("♦x " + tokensRemaining);
    }
}
