package HashMap;

/**
 * Created by jed on 15/10/17.
 * <p>
 * A Node contains a key-value pair. In addition, it contains overrideable
 * methods for additional logic around accessing and changing the value,
 * particularly for use in implementing specific algorithms.
 * <p>
 * The equals method is overridden to check equality with the key in the case
 * of the object in comparison being another key (implemented by the Hashable
 * interface in this package).
 *
 * @param <K> key type
 * @param <V> value type
 */
public abstract class Node<K, V> {
    private final K key;
    private V value;

    protected Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            return key.equals(obj);
        } else {
            return this.equals(obj);
        }
    }

    public V get() {
        onAccess();
        return value;
    }

    public V set(V value) {
        V old = this.value;
        onChange();
        this.value = value;
        return old;
    }

    protected abstract void onAccess();

    protected abstract void onChange();

    protected abstract void onAdd();
}
