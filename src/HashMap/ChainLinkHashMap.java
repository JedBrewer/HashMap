package HashMap;

/**
 * Created by jed on 15/10/17.
 * <p>
 * An abstract class for implementing a HashMap using ChainBuckets, along with
 * an underlying doubly linked list structure (to facilitate ordering logic
 * over the list as a whole).
 *
 * @param <K> key type
 * @param <V> value type
 */
public abstract class ChainLinkHashMap<K, V> extends
        BaseChainHashMap<K, V> {

    public ChainLinkHashMap(int capacity) {
        super(capacity);
    }

    @Override
    protected ChainLinkBucket newBucket(K key, V value) {
        return new ChainLinkBucket(key, value, null);
    }

    protected class ChainLinkBucket extends AbstractChainBucket {
        ChainLinkBucket next, prev, pred, succ;

        public ChainLinkBucket(K key, V value, ChainLinkBucket next) {
            super(key, value, next);
        }

        @Override
        protected void onAccess() {
            ChainLinkHashMap.this.onAccess(this);
        }

        @Override
        protected void onChange() {
            ChainLinkHashMap.this.onChange(this);
        }

        @Override
        protected void onAdd() {
            ChainLinkHashMap.this.onAdd(this);
        }

        public boolean hasNext() {
            return next != null;
        }

        public AbstractChainBucket next() {
            return next;
        }

        @Override
        protected void setNext(AbstractChainBucket next) {
            this.next = (ChainLinkBucket) next;
        }
    }
}
