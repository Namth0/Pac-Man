package Handlers;

import Utilities.Utils;
import Views.MainWindow;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;


/**
 * Key event listener for moving entities.
 */
public class PlayerMovementKeyEventListener extends KeyAdapter
{
    /**
     * Key observed.
     */
    private final Character[] detectedKeys = new Character[] { 'z', 'q', 's', 'd' };


    /**
     * Source of the event.
     */
    MainWindow source;


    /**
     * The class' constructor.
     * @param source The source of the event.
     * <pre>{@code
     * MainWindow window = new MainWindow();
     * PlayerMovementKeyEventListener eventListener = new PlayerMovementKeyEventListener(window);
     * }</pre>
     */
    public PlayerMovementKeyEventListener(MainWindow source)
    {
        super();
        this.source = source;
    }


    /**
     * Callback of event.
     * @param arg the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent arg)
    {
        try
        {
            if (!Arrays.asList(detectedKeys).contains(arg.getKeyChar())) return;
            Utils.Moving direction = switch (arg.getKeyChar())
            {
                case 'z' -> Utils.Moving.UP;
                case 'q' -> Utils.Moving.LEFT;
                case 's' -> Utils.Moving.DOWN;
                case 'd' -> Utils.Moving.RIGHT;
                default ->
                    (!this.source.getGame().canPlayerMove(this.source.getGame().getPlayer().getCurrentDirection()))
                    ? Utils.Moving.STILL : this.source.getGame().getPlayer().getCurrentDirection();
            };
            this.source.updateDirectionBuffer(direction);
        }
        catch (Exception ignored) {}
    }
}
