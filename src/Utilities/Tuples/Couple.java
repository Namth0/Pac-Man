package Utilities.Tuples;

/**
 * Tuple variant : Couple type.
 * @param <T> First type.
 * @param <U> Second type.
 */
public class Couple<T, U>
{
    public T item1;
    public U item2;

    /**
     * Class constructor.
     * @param item1 First item.
     * @param item2 Second item.
     * <pre>{@code
     * Couple<Integer, Boolean> demo = new Couple(10, false);
     * }</pre>
     */
    public Couple(T item1, U item2) {
        this.item1 = item1;
        this.item2 = item2;
    }
}
