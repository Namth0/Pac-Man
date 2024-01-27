package Views;

import Entities.Ghost;
import Entities.Pacgomme;
import Entities.Pacman;
import Entities.Tile;
import Handlers.PlayerMovementKeyEventListener;
import Logic.GameManager;
import UI.Lives;
import UI.Score;
import UI.TokensRemaining;
import Utilities.Configs;
import Utilities.Utils;

import javax.swing.*;
import java.awt.*;


/***
 * The Main window.
 */
public class MainWindow extends JFrame
{
    /**
     * The x delta to place the board at the center of the frame.
     */
    private  int boardXDelta = 0;


    /**
     * The y delta to place the board bellow the labels.
     */
    private final int boardYDelta = 100;


    /**
     * The game instance.
     */
    private GameManager game;


    /**
     * The label displaying the game's score.
     */
    private Score scoreDisplay;


    /**
     * The label displaying the player's lives remaining.
     */
    private Lives livesDisplay;


    /**
     * The label displaying the board's tokens remaining.
     */
    private TokensRemaining tokensRemainingDisplay;


    /**
     * The key event listener notifies here the new direction.
     * It prevents
     */
    private Utils.Moving directionBuffer = null;

    /**
     * The class constructor. During initialisation:
     * <ul>
     *     <li>A @link Logic.GameManager instance is created.</li>
     *     <li>A @link UI.Lives, @link UI.Score, @link UI.TokensRemaining instance is created.</li>
     * </ul>
     * <pre>{@code
     * MainWindow window = new MainWindow();
     * }</pre>
     */
    public MainWindow()
    {
        super();
        this.game = new GameManager();
        this.scoreDisplay = new Score();
        this.livesDisplay = new Lives(this.game.getLives());
        this.tokensRemainingDisplay = new TokensRemaining(this.game.getRemainingTokens());
        this.configureWindow();
        this.addKeyListener(new PlayerMovementKeyEventListener(this));
        this.UpdateLoop();
    }


    /**
     * The game's update loop.
     * <pre>{@code
     * UpdateLoop();
     * }</pre>
     * */
    private void UpdateLoop()
    {
        new Thread(() -> {
            while (true)
            {
                try { Thread.sleep(Configs.FPS); } catch (Exception ignored) {}
                this.updatePositions();
                if (this.directionBuffer != null) this.game.getPlayer().changeDirection(this.directionBuffer);
                this.directionBuffer = null;
                this.repaint();
            }
        }).start();
    }

    /**
     * Move the player and the ghosts on the board.
     * <pre>{@code
     * Utils.Moving direction = Utils.Moving.UP;
     * MainWindow window = new MainWindow();
     * window.updatePositions(direction);
     * }</pre>
     */
    public void updatePositions()
    {
        this.game.moveGhosts();
        Utils.Moving playerDirection = this.game.getPlayer().getCurrentDirection();
        this.game.movePlayer(playerDirection == null ? Utils.Moving.STILL : playerDirection);
        if (!this.game.getBoardShuffled()) this.paint(null);
        else
        {
            this.reset();
            this.game.setBoardShuffled(false);
            this.initialDisplay();
        }
    }


    /**
     * Sets the default styles of the window.
     * <pre>{@code
     * this.configureWindow();
     * }</pre>
     */
    private void configureWindow()
    {
        this.setSize(Configs.WINDOW_WIDTH, Configs.WINDOW_HEIGHT);
        this.setBackground(Configs.BACKGROUND_COLOR);
        this.setResizable(false);
        this.add(scoreDisplay);
        this.add(livesDisplay);
        this.add(tokensRemainingDisplay);
        this.scoreDisplay.setPreferredSize(new Dimension(200, 100));
        this.livesDisplay.setPreferredSize(new Dimension(200, 100));
        this.tokensRemainingDisplay.setPreferredSize(new Dimension(200, 100));
        int x = Configs.WINDOW_WIDTH / 5;
        this.scoreDisplay.setBounds(x, 0, 200, 100);
        this.livesDisplay.setBounds(2 * x, 0, 200, 100);
        this.tokensRemainingDisplay.setBounds(3 * x, 0, 200, 100);
        displayBoard();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    /**
     * Calculates the board's x delta to center the board on the X axis.
     * <pre>{@code
     * this.displayBoard();
     * }</pre>
     */
    private void displayBoard()
    {
        this.boardXDelta = (Configs.WINDOW_WIDTH - Configs.TILE_SIZE * Configs.BOARD_WIDTH) / 3;
        this.initialDisplay();
    }


    /**
     * The current game getter.
     * @return the current game.
     * <pre>{@code
     * MainWindow window = new MainWindow();
     * GameManager game = window.getGame();
     * }</pre>
     */
    public GameManager getGame()
    {
        return this.game;
    }


    /**
     * Displays the board for the first time on the window.
     * <pre>{@code
     * this.initialDisplay();
     * }</pre>
     */
    public void initialDisplay()
    {
        Logic.Tile[][] board = this.game.getBoard();
        for (int r = 0; r < board.length; r++)
        {
            for (int c = 0; c < board[r].length; c++)
            {
                if (board[r][c].hasPlayer())
                {
                    this.add(board[r][c].getPlayer());
                    board[r][c].getPlayer().setBounds(
                        new Rectangle(
                            boardXDelta + r * Configs.TILE_SIZE,
                            boardYDelta + c * Configs.TILE_SIZE,
                            Configs.TILE_SIZE,
                            Configs.TILE_SIZE
                        )
                    );
                }
                if (board[r][c].getGhost() != null)
                {
                    this.add(board[r][c].getGhost());
                    board[r][c].getGhost().setBounds(
                        new Rectangle(
                            boardXDelta + r * Configs.TILE_SIZE,
                            boardYDelta + c * Configs.TILE_SIZE,
                            Configs.TILE_SIZE,
                            Configs.TILE_SIZE
                        )
                    );
                }
            }
        }

        for (int r = 0; r < board.length; r++)
        {
            for (int c = 0; c < board[r].length; c++)
            {
                if (board[r][c].getToken() != null)
                {
                    this.add(board[r][c].getToken());
                    board[r][c].getToken().setBounds(
                        new Rectangle(
                            boardXDelta + r * Configs.TILE_SIZE,
                            boardYDelta + c * Configs.TILE_SIZE,
                            Configs.TILE_SIZE,
                            Configs.TILE_SIZE
                        )
                    );
                }
            }
        }
        for (int r = 0; r < board.length; r++)
        {
            for (int c = 0; c < board[r].length; c++) {
                this.add(board[r][c].getTile());
                board[r][c].getTile().setBounds(
                    new Rectangle(
                        boardXDelta + r * Configs.TILE_SIZE,
                        boardYDelta + c * Configs.TILE_SIZE,
                        Configs.TILE_SIZE,
                        Configs.TILE_SIZE
                    )
                );
            }
        }
    }


    /**
     * Removes all components on the window and replace the UIs.
     * <pre>{@code
     * this.reset();
     * }</pre>
     */
    private void reset()
    {
        this.getContentPane().removeAll();
        this.add(scoreDisplay);
        this.add(livesDisplay);
        this.add(tokensRemainingDisplay);
        this.game.toggleBoard();
    }


    /**
     * Creates a new game. Resets all components and redisplay the UIs.
     * <pre>{@code
     * this.newGame();
     * }</pre>
     */
    private void newGame()
    {
        this.getContentPane().removeAll();
        this.game = new GameManager();
        this.scoreDisplay = new Score();
        this.livesDisplay = new Lives(this.game.getLives());
        this.tokensRemainingDisplay = new TokensRemaining(this.game.getRemainingTokens());
        this.add(scoreDisplay);
        this.add(livesDisplay);
        this.add(tokensRemainingDisplay);
        this.scoreDisplay.setPreferredSize(new Dimension(200, 100));
        this.livesDisplay.setPreferredSize(new Dimension(200, 100));
        this.tokensRemainingDisplay.setPreferredSize(new Dimension(100, 100));
        int x = Configs.WINDOW_WIDTH / 5;
        this.scoreDisplay.setBounds(x, 0, 200, 100);
        this.livesDisplay.setBounds(2 * x, 0, 200, 100);
        this.tokensRemainingDisplay.setBounds(3 * x, 0, 200, 100);
        this.initialDisplay();
    }


    /**
     * Updates the board. Replace the ghosts, the player and hides the consumed tokens.
     * <pre>{@code
     * MainWindow window = new MainWindow();
     * window.update();
     * }</pre>
     */
    @Override
    public void paint(Graphics g)
    {
        if (g != null) super.paint(g);
        this.scoreDisplay.changeScore(this.game.getScore());
        this.livesDisplay.changeLives(this.game.getLives());
        this.tokensRemainingDisplay.changeText(this.game.getRemainingTokens());
        this.scoreDisplay.repaint();
        this.livesDisplay.repaint();
        this.tokensRemainingDisplay.repaint();
        Logic.Tile[][] board = this.game.getBoard();

        for (int r = 0; r < board.length; r++)
        {
            for (int c = 0; c < board[r].length; c++)
            {
                if (board[r][c].hasPlayer())
                {
                    if (board[r][c].getToken() != null)
                    {
                        this.remove(board[r][c].getToken());
                        board[r][c].removeToken();
                    }
                }
            }
        }
        // Position + gui of ghosts and pacman
        for (int r = 0; r < board.length; r++)
        {
            for (int c = 0; c < board[r].length; c++)
            {
                if (board[r][c].hasPlayer()) this.place(board[r][c].getPlayer(), r, c);
                if (board[r][c].getGhost() != null) this.place(board[r][c].getGhost(), r, c);
            }
        }
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[r].length; c++)
                if (board[r][c].getToken() != null) this.place(board[r][c].getToken(), r, c);

        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[r].length; c++)
                this.place(board[r][c].getTile(), r, c);

        if (this.game.getLives() <= 0 || this.game.getRemainingTokens() <= 0)
        {
            int res = JOptionPane.showConfirmDialog(this, "Game over. Start a new game ?");
            if (res == JOptionPane.YES_OPTION) this.newGame();
            else System.exit(0);
        }
    }


    /**
     * Place the given token on the window.
     * @param pacgomme The token to be placed.
     * @param r The row index of the token's tile.
     * @param c The column index of the token's tile.
     * <pre>{@code
     * Pacgomme token = new Pacgomme(TokenVariants.BLUE);
     * this.place(token, 10, 5);
     * }</pre>
     */
    private void place(Pacgomme pacgomme, int r, int c)
    {
        pacgomme.setLocation(boardXDelta + r * Configs.TILE_SIZE, boardYDelta + c * Configs.TILE_SIZE);
    }


    /**
     * Place the given tile on the window.
     * @param tile The tile to be placed.
     * @param r The row index of the token's tile.
     * @param c The column index of the token's tile.
     * <pre>{@code
     * Tile tile = new Tile(TileVariant.WALL);
     * this.place(tile, 10, 5);
     * }</pre>
     */
    private void place(Tile tile, int r, int c)
    {
        tile.setLocation(boardXDelta + r * Configs.TILE_SIZE, boardYDelta + c * Configs.TILE_SIZE);
    }


    /**
     * Place the given player on the window.
     * @param pacman The player to be placed.
     * @param r The row index of the token's tile.
     * @param c The column index of the token's tile.
     * <pre>{@code
     * Pacman player = new Pacman();
     * this.place(player, 10, 5);
     * }</pre>
     */
    private void place(Pacman pacman, int r, int c)
    {
        pacman.setLocation(boardXDelta + r * Configs.TILE_SIZE, boardYDelta + c * Configs.TILE_SIZE);
    }


    /**
     * Place the given ghost on the window.
     * @param ghost The ghost to be placed.
     * @param r The row index of the token's tile.
     * @param c The column index of the token's tile.
     * <pre>{@code
     * Ghost ghost = new Ghost();
     * this.place(ghost, 10, 5);
     * }</pre>
     */
    private void place(Ghost ghost, int r, int c)
    {
        ghost.setLocation(boardXDelta + r * Configs.TILE_SIZE, boardYDelta + c * Configs.TILE_SIZE);
    }


    /**
     * Sets a new direction in the direction buffer.
     * @param direction The new direction.
     * <pre>{@code
     * Utils.Moving direction = Utils.Moving.UP;
     * updateDirectionBuffer(direction);
     * }</pre>
     */
    public void updateDirectionBuffer(Utils.Moving direction) { this.directionBuffer = direction; }
}
