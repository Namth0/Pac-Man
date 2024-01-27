package Scenes;

import Components.TileVariant;
import Utilities.Configs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;


/**
 * Terrain generator class.
 */
public class SceneGenerator
{
    /**
     * Stack used for maze generation.
     */
    private Stack<Node> stack = new Stack<>();


    /**
     * Random module initialization.
     */
    private Random rand = new Random();


    /**
     * Generates a random board.
     * @return a new board.
     * <pre>{@code
     * SceneGenerator generator = new SceneGenerator();
     * TileVariant[][] board = generator.generateRandomBoard();
     * }</pre>
     */
    public TileVariant[][] generateRandomBoard()
    {
        TileVariant[][] board = new TileVariant[Configs.BOARD_WIDTH][Configs.BOARD_HEIGHT];
        for (TileVariant[] line : board) Arrays.fill(line, TileVariant.EMPTY);
        return fillIsolatedPatches(insertShapes(createAxes(generateMaze(board))));
    }


    /**
     * Add axes to the given board to produce the wrap-around effect.
     * @param board The board.
     * @return The board with axes.
     * <pre>{@code
     * SceneGenerator generator = new SceneGenerator();
     * TileVariant[][] board = ...;
     * TileVariant[][] boardWithAxes = generator.createAxes(board);
     * }</pre>
     */
    private TileVariant[][] createAxes(TileVariant[][] board)
    {
        Arrays.fill(board[0], TileVariant.WALL);
        for (int i = 0; i < board.length; i++)
        {
            board[i][0] = TileVariant.WALL;
            board[i][board[i].length - 1] = TileVariant.WALL;
        }
        Arrays.fill(board[board.length - 1], TileVariant.WALL);
        return board;
    }


    /**
     * Add shapes to the maze to widen the open-spaces.
     * @param board The board.
     * @return The board with the inserted shapes.
     * <pre>{@code
     * SceneGenerator generator = new SceneGenerator();
     * TileVariant[][] board = ...;
     * TileVariant[][] boardWithAxes = generator.insertShapes(board);
     * }</pre>
     */
    private TileVariant[][] insertShapes(TileVariant[][] board)
    {
        int yMiddle = (board.length / 2);
        int xMiddle = (board[0].length / 2);
        for (int l = 0; l < xMiddle; l += Configs.EMPTY_SPACE)
        {
            for (int i = 0; i < board.length; i++) board[i][xMiddle + l] = TileVariant.EMPTY;
            for (int i = 0; i < board.length; i++) board[i][xMiddle - l] = TileVariant.EMPTY;
        }
        for (int l = 0; l < yMiddle; l += Configs.EMPTY_SPACE)
        {
            for (int i = 0; i < board.length; i++) board[yMiddle + l][i] = TileVariant.EMPTY;
            for (int i = 0; i < board.length; i++) board[yMiddle - l][i] = TileVariant.EMPTY;
        }
        return board;
    }


    /**
     * Fills isolated patches on the given board. Otherwise, the game will be endless.
     * @param board The board
     * @return The board with isolated patches.
     * <pre>{@code
     * SceneGenerator generator = new SceneGenerator();
     * TileVariant[][] board = ...;
     * TileVariant[][] boardWithAxes = generator.fillIsolatedPatches(board);
     * }</pre>
     */
    private TileVariant[][] fillIsolatedPatches(TileVariant[][] board)
    {
        ArrayList<ArrayList<Node>> patches = new ArrayList<ArrayList<Node>>();
        for (int row = 0; row < board.length; row++)
        {
            for (int col = 0; col < board[row].length; col++)
            {
                if (board[row][col] == TileVariant.WALL) continue;
                if (this.tileIsInPatch(patches, new Node(row, col))) continue;;
                patches.add(calculatePatch(board, row, col, new ArrayList<Node>()));
            }
        }
        int maxLength = patches.stream().map(ArrayList::size).max((a, b) -> (a < b) ? b : a).orElse(0);
        ArrayList<Node> mainPatch = new ArrayList<Node>();
        for(ArrayList<Node> patch : patches)
        {
            if (patch.size() == maxLength)
            {
                mainPatch = patch;
                continue;
            }
            for(Node node : patch) board[node.x][node.y] = TileVariant.WALL;
        }
        return placeSpawningArea(board, mainPatch);
    }


    /**
     * Places the spawning areas on the board.
     * @param board The board.
     * @param freeTiles The empty tiles.
     * @return The board with the spawners.
     * <pre>{@code
     * SceneGenerator generator = new SceneGenerator();
     * TileVariant[][] board = ...;
     * ArrayList<Node> freeTiles = ...;
     * TileVariant[][] boardWithAxes = generator.placeSpawningArea(board, freeTiles);
     * }</pre>
     */
    private TileVariant[][] placeSpawningArea(TileVariant[][] board, ArrayList<Node> freeTiles)
    {
        for (int i = 0; i < board.length - 2; i++)
            if (board[1][1 + i] == TileVariant.EMPTY) break;
            else board[1][1 + i] = TileVariant.EMPTY;

        for (int i = 0; i < board.length - 2; i++)
            if (board[1][board[0].length - 2 - i] == TileVariant.EMPTY) break;
            else board[1][board[0].length - 2 - i] = TileVariant.EMPTY;

        for (int i = 0; i < board.length - 2; i++)
            if (board[board.length - 2][1 + i] == TileVariant.EMPTY) break;
            else board[board.length - 2][1 + i] = TileVariant.EMPTY;

        for (int i = 0; i < board.length - 2; i++)
            if (board[board.length - 2][board[0].length - 2 - i] == TileVariant.EMPTY) break;
            else board[board.length - 2][board[0].length - 2 - i] = TileVariant.EMPTY;


        for (int i = 0; i < board.length - 2; i++)
            if (board[1 + i][1] == TileVariant.EMPTY) break;
            else board[1 + i][1] = TileVariant.EMPTY;

        for (int i = 0; i < board.length - 2; i++)
            if (board[1 + i][board[0].length - 2] == TileVariant.EMPTY) break;
            else board[1 + i][board[0].length - 2] = TileVariant.EMPTY;

        for (int i = 0; i < board.length - 2; i++)
            if (board[board.length - 2 - i][1] == TileVariant.EMPTY) break;
            else board[board.length - 2 - i][1] = TileVariant.EMPTY;

        for (int i = 0; i < board.length - 2; i++)
            if (board[board.length - 2 - i][board[0].length - 2] == TileVariant.EMPTY) break;
            else board[board.length - 2 - i][board[0].length - 2] = TileVariant.EMPTY;

        board[1][1] = TileVariant.GHOST_SPAWN;
        board[board.length - 2][1] = TileVariant.GHOST_SPAWN;
        board[board.length - 2][board[0].length - 2] = TileVariant.GHOST_SPAWN;
        board[1][board[0].length - 2] = TileVariant.GHOST_SPAWN;
        board[board.length / 2][board[0].length / 2] = TileVariant.SPAWN;
        return board;
    }


    /**
     * Checks if the given tile is in the given patches.
     * @param patches The given patches.
     * @param tile The tile.
     * @return true if the given tile is in the given patches.
     * <pre>{@code
     * SceneGenerator generator = new SceneGenerator();
     * ArrayList<ArrayList<Node>> patches = ...;
     * Node tile = ...;
     * Boolean bool = generator.tileIsInPatch(patches, tile);
     * }</pre>
     */
    private Boolean tileIsInPatch(ArrayList<ArrayList<Node>> patches, Node tile)
    {
        for (ArrayList<Node> patch : patches) if (nodeIsInPatch(patch, tile)) return true;
        return false;
    }


    /**
     * Generate a patch from a given tile.
     * @param board The board.
     * @param x The studied tile's X coordinate.
     * @param y The studied tile's Y coordinate.
     * @param patch The constructed patch.
     * @return a patch from a given tile.
     * <pre>{@code
     * SceneGenerator generator = new SceneGenerator();
     * TileVariant[][] board = ...;
     * ArrayList<Node> patch = new ArrayList<>()
     * ArrayList<Node> filledPatch = calculatePatch(board, 5, 10, patch);
     * }</pre>
     */
    private ArrayList<Node> calculatePatch(TileVariant[][] board, int x, int y, ArrayList<Node> patch)
    {
        if (board[x][y] == TileVariant.WALL) return patch;
        if (nodeIsInPatch(patch, new Node(x, y))) return patch;
        patch.add(new Node(x, y));
        if (x > 0) patch = calculatePatch(board, x - 1, y, patch);
        if (x < board.length - 1) patch = calculatePatch(board, x + 1, y, patch);
        if (y < board.length - 1) patch = calculatePatch(board, x, y + 1, patch);
        if (y > 0) patch = calculatePatch(board, x, y - 1, patch);
        return patch;
    }


    /**
     * Checks if the given node is in the given patch.
     * @param patch The patch
     * @param node The node
     * @return true if the given node is in the given patch.
     * <pre>{@code
     * ArrayList<Node> patch = ...;
     * Node node = ...;
     * Boolean bool = this.nodeIsInPatch(patch, node);
     * }</pre>
     */
    private Boolean nodeIsInPatch(ArrayList<Node> patch, Node node)
    {
        for(Node n : patch) if (n.x == node.x && n.y == node.y) return true;
        return false;
    }


    /**
     * Generates a maze.
     * @param board The empty board.
     * @return The board filled with a maze.
     * <pre>{@code
     * TileVariant[][] board = ...;
     * TileVariant[][] filledBoard = this.generateMaze(board);
     * }</pre>
     */
    private TileVariant[][] generateMaze(TileVariant[][] board)
    {
        stack.push(new Node(0,0));
        while (!stack.empty())
        {
            Node next = stack.pop();
            if (validNextNode(next, board))
            {
                board[next.y][next.x] = TileVariant.WALL;
                ArrayList<Node> neighbors = findNeighbors(next);
                randomlyAddNodesToStack(neighbors);
            }
        }
        return board;
    }


    /**
     * Checks if the next node is valid.
     * @param node The next node.
     * @param board The board.
     * @return true if the next node is valid.
     * <pre>{@code
     * Node node = ...;
     * TileVariant[][] board = ...;
     * Boolean bool = this.validNextNode(node, board);
     * }</pre>
     */
    private boolean validNextNode(Node node, TileVariant[][] board)
    {
        int numNeighboringOnes = 0;
        for (int y = node.y - 1; y < node.y + 2; y++)
            for (int x = node.x - 1; x < node.x + 2; x++)
                if (pointOnGrid(x, y) && pointNotNode(node, x, y) && board[y][x] == TileVariant.WALL)
                    numNeighboringOnes++;
        return (numNeighboringOnes < 3) && board[node.y][node.x] != TileVariant.WALL;
    }


    /**
     * Add random nodes to the stack.
     * @param nodes The given node.
     * <pre>{@code
     * ArrayList<Node> nodes = ...;
     * this.randomlyAddNodesToStack(nodes);
     * }</pre>
     */
    private void randomlyAddNodesToStack(ArrayList<Node> nodes)
    {
        int targetIndex;
        while (!nodes.isEmpty())
        {
            targetIndex = rand.nextInt(nodes.size());
            stack.push(nodes.remove(targetIndex));
        }
    }


    /**
     * Returns the nodes neighbouring the given node.
     * @param node The node.
     * @return the nodes neighbouring the given node.
     * <pre>{@code
     * Node node = new Node(10, 5);
     * ArrayList<Node> neighbours = this.findNeighbors(node);
     * }</pre>
     */
    private ArrayList<Node> findNeighbors(Node node)
    {
        ArrayList<Node> neighbors = new ArrayList<>();
        for (int y = node.y - 1; y < node.y + 2; y++)
            for (int x = node.x - 1; x < node.x + 2; x++)
                if (pointOnGrid(x, y) && pointNotCorner(node, x, y) && pointNotNode(node, x, y))
                    neighbors.add(new Node(x, y));
        return neighbors;
    }


    /**
     * Checks if the given coordinate are on the grid.
     * @param x The X Coordinate.
     * @param y The Y Coordinate.
     * @return true if the given coordinate are on the grid.
     * <pre>{@code
     * Node node = new Node(10, 5);
     * Boolean bool = this.pointOnGrid(5, 10);
     * }</pre>
     */
    private Boolean pointOnGrid(int x, int y)
    {
        return x >= 0 && y >= 0 && x < Configs.BOARD_WIDTH && y < Configs.BOARD_HEIGHT;
    }


    /**
     * Checks if the given coordinates and the given node are in a corner.
     * @param node The Node
     * @param x The X Coordinate
     * @param y The Y Coordinate
     * @return true if the given coordinates and the given node are in a corner.
     * <pre>{@code
     * Node node = new Node(10, 5);
     * Boolean bool = this.pointNotCorner(node, 5, 10);
     * }</pre>
     */
    private Boolean pointNotCorner(Node node, int x, int y)
    {
        return (x == node.x || y == node.y);
    }


    /**
     * Checks if the given coordinate matches with the given node.
     * @param node The Node
     * @param x The X Coordinate
     * @param y The Y Coordinate
     * @return true if the given coordinate matches with the given node.
     * <pre>{@code
     * Node node = new Node(10, 5);
     * Boolean bool = this.pointNotNode(node, 5, 10);
     * }</pre>
     */
    private Boolean pointNotNode(Node node, int x, int y)
    {
        return !(x == node.x && y == node.y);
    }
}


/**
 * Class used for the maze generation.
 */
class Node
{
    /**
     * The X coordinate.
     */
    public int x;


    /**
     * The Y coordinate.
     */
    public int y;


    /**
     * The class' constructor.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * <pre>{@code
     * Node node = new Node(10, 5);
     * }</pre>
     */
    public Node(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}