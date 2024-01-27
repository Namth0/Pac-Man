package Utilities.Tuples;


/**
 * Tuple variant : Triplet type.
 * @param <A> First type.
 * @param <B> Second type.
 * @param <C> Third type.
 */
public class Triplet<A, B, C>
{
    public A item1;
    public B item2;
    public C item3;


    /**
     * Class constructor.
     * @param item1 First item.
     * @param item2 Second item.
     * @param item3 Third item.
     * <pre>{@code
     * Triplet<Integer, Boolean, String> demo = new Triplet(10, false, "hello");
     * }</pre>
     */
    public Triplet(A item1, B item2, C item3)
    {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
    }
}