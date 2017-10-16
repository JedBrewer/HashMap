package HashMap;

/**
 * Created by jed on 15/10/17.
 * <p>
 * An abstract class for a map using ChainBuckets.
 * A ChainBucket is a Bucket implemented as the head Node of a singly linked
 * list of Nodes.
 *
 * @param <K> key type
 * @param <V> value type
 */
public abstract class BaseChainHashMap<K, V> extends HashMap<K, V> {

    public BaseChainHashMap(int capacity) {
        super(capacity);
    }

    protected abstract AbstractChainBucket current();

    protected abstract AbstractChainBucket setCurrent(AbstractChainBucket current);

    protected final AbstractChainBucket setCurrent(Node<K, V> current) {
        return setCurrent((AbstractChainBucket) current);
    }

    protected abstract AbstractChainBucket newBucket(K key, V value);

    protected abstract class AbstractChainBucket
            extends Node<K, V> implements Bucket<K, V> {

        AbstractChainBucket(K key, V value, AbstractChainBucket next) {
            super(key, value);
            this.setNext(next);
        }

        @Override
        public Node<K, V> get(K key) {
            if (this.equals(key))
                return this;
            setCurrent(this);
            while (current().hasNext())
                if (setCurrent(current().next()).equals(key))
                    return this;
            onNotStored();
            return null;
        }

        @Override
        public V put(K key, V value) {
            if (this.equals(key))
                return this.set(value);
            setCurrent(this);
            while (current().hasNext())
                if (setCurrent(current().next()).equals(key))
                    return current().set(value);
            evictIf();
            setNext(newBucket(key, value));
            next().onAdd();
            return null;
        }

        protected abstract boolean hasNext();

        protected abstract AbstractChainBucket next();

        protected abstract void setNext(AbstractChainBucket next);

        public final Node<K, V> first() {
            return this;
        }
    }
}