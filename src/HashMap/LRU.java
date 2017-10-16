package HashMap;

import java.util.function.Function;

/**
 * Created by jed on 15/10/17.
 * <p>
 * An implementation of LRU using ChainHashMap.
 * In this implementation, a fixed size bucket array with a load factor of
 * 1.0 is used in order to avoid unused buckets. In addition, the entire
 * bucket array is allocated at the outset to avoid resizing, as the use will
 * dictate that it is filled quickly relative to the lifetime of the cache.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class LRU<K, V> extends ChainLinkHashMap<K, V> {

    private final Function<K, V> lookup;
    private ChainLinkBucket head, tail;
    private ChainLinkBucket evicted;       // singleton pool
    private ChainLinkBucket current;       // singleton pool
    private int bitShift;

    public LRU(int capacity, Function<K, V> lookup) {
        super(capacity);
        this.lookup = lookup;
        bucketCount = capacity;
        initBitShift();
    }

    @Override
    protected int bucketCount() {
        return capacity;
    }

    @Override
    public V get(K key) {
        V value;

        if ((value = super.get(key)) == null) {
            put(key, value = lookup.apply(key));
        }
        return value;
    }

    @Override
    protected V put(K key, V value) {
        return super.put(key, value);
    }

    @Override
    protected void onAdd(Node node) {
        onAccess(node);
    }

    @Override
    protected void evict() {
        evicted = head;
        head = head.succ;
        evicted.succ = head.pred = null;

        if (evicted.prev != null) {
            evicted.prev.next = evicted.next;
            evicted.next.prev = evicted.prev;
            evicted.next = evicted.prev = null;
        }
    }

    @Override
    protected void onChange(Node node) {
        onAccess(node);
    }

    @Override
    protected void onAccess(Node node) {
        if (node == head && size > 1) {
            head = head.succ;
        }

        current = tail;
        tail = (ChainLinkBucket) node;
        if (current == null) {
            head = tail;
        } else {
            if (tail.pred != null)
                tail.pred.succ = tail.succ;
            if (tail.succ != null)
                tail.succ.pred = tail.pred;
            current.succ = tail;
            tail.pred = current;
            tail.succ = null;
        }
    }

    /**
     * This performs an XOR operation with a bit shift of the operation to
     * account for power-of-two multiples in the capacity of the bucket array.
     * The only reason this is a problem is due to certain data structures
     * having invariance among lower bits in many similar instantiations.
     * Because the bucket array capacity is fixed and not necessarily a
     * power-of-two, there is no need for the additional xor operation in the
     * case where the array capacity is even.
     */
    final protected int hash(K key) {
        int h;
        return (key == null) ? 0 : (bitShift == 0 ? keyHash(key) :
                (h = keyHash(key)) ^ (h >>> bitShift));
    }

    /**
     * This provides a default hash function for keys. It uses the standard
     * hash function for the key's type, but it can be overridden.
     */
    int keyHash(K key) {
        return key.hashCode();
    }

    void initBitShift() {
        int i = 0;
        while ((capacity & 1) == 0) {
            capacity >>>= 1;
            i++;
        }
        bitShift = i;
    }

    @Override
    protected AbstractChainBucket current() {
        return current;
    }

    @Override
    protected AbstractChainBucket setCurrent(AbstractChainBucket current) {
        return this.current = (ChainLinkBucket) current;
    }
}
