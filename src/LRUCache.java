/**
 * Implements an LRU cache.
 */

import java.util.*;

/**
 * Objects of this class represent an LRU cache data structure.
 *
 * Elements are key-value pairs.
 *
 * An underlying data structure is a queue, where the front of the queue is
 * always the least recently used element, and so gets removed from the cache
 * when space is needed.
 */
public class LRUCache {

	final private int maxSize;
	private int size;
	private Map<Object, Object> cacheMap;
	private List<Object> keyQueue;

	/**
	 * Instantiates a new, initially empty, LRUCache object.
	 *
	 * @param maxSize: The maximum number of elements that the cache can hold at
	 *                 any given, time.
	 */
	public LRUCache(int maxSize) {
		this.maxSize = maxSize;
		this.cacheMap = new HashMap<>();
		this.keyQueue = new LinkedList<>();
		this.size = 0;
	}

	/**
	 * Retrieves the current size, i.e., the number of elements in the cache.
	 *
	 * @return The current size of the cache.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Get the value corresponding to the given key if it has been cached.
	 *
	 * If the key _does_ exist in the cache, then it is put at the back of the
	 * queue, indicating that it has been most recently used.
	 *
	 * @param key: A key to look up a potential value for in the cache.
	 * @return The key's paired value, or null if the key is not in the cache.
	 */
	public Object get(Object key) {
		Object value = cacheMap.getOrDefault(key, null);
		if (value != null) {
			moveToBack(key);
		}
		return value;
	}

	/**
	 * Adds a new key-value pair to the cache.
	 *
	 * If key already exists in the cache, update its value without deleting any
	 * values from the cache, but move it to the back of the queue indicating
	 * that the key is the most recently used element.
	 *
	 * If the cache is full, then remove key at the front of queue from the key
	 * and the cache, before adding the new key value pair to the cache.
	 *
	 * @param key: The key of the new key-value pair to add to the cache.
	 * @param value: The value of the new key-value pair to add to the cache.
	 */
	public void put(Object key, Object value) {
		// If key already cached, then just move it to the back of the stack.
		if (cacheMap.containsKey(key)) {
			moveToBack(key);
			return;
		}

		// If the cache is full, then delete the least recently used, i.e., the
		// key at the front of the queue. Otherwise, the cache should just grow.
		if (size == maxSize) {
			Object keyToDelete = keyQueue.remove(0);
			cacheMap.remove(keyToDelete);
		} else {
			size++;
		}

		// If the key does not already exist in the cache, then add the
		// key-value pair to the map, and the key to the queue.
		cacheMap.put(key, value);
		keyQueue.add(key);
	}

	/**
	 * Move the given key to the back the queue without changing the order of
	 * any other keys in the queue.
	 *
	 * @param key: The key to move to the back.
	 */
	private void moveToBack(Object key) {
		keyQueue.removeIf(nextKey -> nextKey == key);
		keyQueue.add(key);
	}
}
