package HashMap;

/**
 * Created by jed on 15/10/17.
 * <p>
 * A bucket is a collection of Nodes referenced by a hash. All these Nodes
 * contain a key of Hashable type K, each of which hashes to the same hash,
 * and a value of type V. Thus, at the very least, Bucket must specify the
 * type parameters K and V.
 * <p>
 * There are various ways a bucket can be implemented, but it must have the
 * following methods to communicate with and be manipulated by its hash
 * structure.
 * <p>
 * It must be able to receive a request for the Node of a certain key, and
 * return that Node.
 * <p>
 * It must be able to receive a Node and store it. In this case, it is only
 * the key and value that actually need to be sent. Any other structure
 * within the node (aside from the internals for this package) that needs
 * to be recalled should be refactored into type V.
 * <p>
 * There is also a hashCheck function. In general, this should not be
 * necessary and is set to true, but it is there for cases where there may be
 * some doubt (a dynamic hash for instance, as sometimes occurs on resizing).
 * <p>
 * In addition, it contains overrideable methods for additional logic for
 * implementing specific algorithms.
 *
 * @param <K> key type
 * @param <V> value type
 */
public interface Bucket<K, V> {
    Node<K, V> get(K key);

    V put(K key, V value);

    Node<K, V> first();

    // returns true if the key has the hash of the bucket, false otherwise
    default boolean hashCheck(K key) {
        return true;
    }

    default void onNotStored() {
    }
}
