package HashMap;

/**
 * Created by jed on 15/10/17.
 * <p>
 * An abstract class for a hash map.
 * This class contains the underlying logic to inject different behaviour on
 * access and mutation operations.
 *
 * @param <K> key type
 * @param <V> value type
 */
public abstract class HashMap<K, V> {
    protected Bucket<K, V>[] table;
    /**
     * Current number of mappings.
     */
    protected int size;
    /**
     * Maximum number of mappings.
     */
    protected int capacity;
    /**
     * Number of buckets.
     */
    protected int bucketCount;
    private int MIN_CAPACITY = 2;

    public HashMap(int capacity) {
        if (capacity < MIN_CAPACITY)
            throw new IllegalArgumentException(
                    String.format("capacity must be at least %d",
                            MIN_CAPACITY));
        this.capacity = capacity;
        bucketCount = bucketCount();
        table = new Bucket[bucketCount];

    }

    protected abstract int bucketCount();

    protected V get(K key) {
        int h = hash(key) % bucketCount;
        if (table[h] != null)
            if (setCurrent(table[h].get(key)) != null)
                return current().get();
        return null;
    }

    protected abstract Node<K, V> setCurrent(Node<K, V> current);

    protected abstract Node<K, V> current();

    protected V put(K key, V value) {
        int h = hash(key) % bucketCount;
        if (table[h] != null)
            return table[h].put(key, value);
        evictIf();
        table[h] = newBucket(key, value);
        onAccess(table[h].first());
        return null;
    }

    protected final void evictIf() {
        if (size == capacity)
            evict();
        else
            size++;
    }

    protected abstract Bucket<K, V> newBucket(K key, V value);

    protected abstract void onAdd(Node node);

    protected abstract void evict();

    protected abstract void onAccess(Node node);

    protected abstract void onChange(Node node);

    protected abstract int hash(K key);
}
