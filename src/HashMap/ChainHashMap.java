package HashMap;

/**
 * Created by jed on 15/10/17.
 * <p>
 * An abstract class for implementing HashMaps using ChainBuckets, but
 * without any extra infrastructure.
 *
 * @param <K> key type
 * @param <V> value type
 */
public abstract class ChainHashMap<K, V> extends
        BaseChainHashMap<K, V> {

    public ChainHashMap(int capacity) {
        super(capacity);
    }

    @Override
    protected ChainBucket newBucket(K key, V value) {
        return new ChainBucket(key, value, null);
    }

    class ChainBucket extends AbstractChainBucket {

        AbstractChainBucket next;

        public ChainBucket(K key, V value, ChainBucket next) {
            super(key, value, next);
        }

        @Override
        protected void onAccess() {
            ChainHashMap.this.onAccess(this);
        }

        @Override
        protected void onChange() {
            ChainHashMap.this.onChange(this);
        }

        @Override
        protected void onAdd() {
            ChainHashMap.this.onAdd(this);
        }

        public boolean hasNext() {
            return next != null;
        }

        public AbstractChainBucket next() {
            return next;
        }

        @Override
        protected void setNext(AbstractChainBucket next) {
            this.next = next;
        }
    }
}
