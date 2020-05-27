/**
 * This is the test suite for the LRUCache class.
 */

import org.junit.Test;

import static org.junit.Assert.*;


public class TestLRUCache {

	/**
	 * Test case where there the cache is empty -- the size should be zero, and
	 * retrieving any element should result in null.
	 */
	@Test
	public void testEmpty() {
		LRUCache cache = new LRUCache(3);
		assertEquals(0, cache.getSize());
		assertNull(cache.get(1));
		assertNull(cache.get(3));
	}

	/**
	 * Test case that ensures that the cache grows as expected as elements are
	 * added.
	 */
	@Test
	public void testGrowth() {
		LRUCache cache = new LRUCache(3);

		// Add first element.
		cache.put(1, "aaa");
		assertEquals(1, cache.getSize());

		// Add second element.
		cache.put(2, "bbb");
		assertEquals(2, cache.getSize());

		// Add third element.
		cache.put(3, "ccc");
		assertEquals(3, cache.getSize());

		assertSame("aaa", cache.get(1));
		assertSame("bbb", cache.get(2));
		assertSame("ccc", cache.get(3));
	}

	/**
	 * Test case where there the cache has elements stored but a key that is not
	 * available in the cache is tried.
	 */
	@Test
	public void testInvalidElement() {
		LRUCache cache = new LRUCache(3);

		cache.put(1, "xyz");
		cache.put(2, "aaa");
		cache.put(3, "zzz");

		assertEquals(3, cache.getSize());
		assertNull(cache.get(4));
	}

	/**
	 * Test case where there the cache is filled without any retrievals, then
	 * a new element is added -- the first key entered is the first to go.
	 */
	@Test
	public void testReplace() {
		LRUCache cache = new LRUCache(3);

		// Initial fill
		cache.put(1, "aaa");
		cache.put(2, "bbb");
		cache.put(3, "ccc");

		assertEquals(3, cache.getSize());

		// Cause replacement by new addition.
		cache.put(4, "ddd");

		assertEquals(3, cache.getSize());
		assertNull(cache.get(1));
		assertEquals("bbb", cache.get(2));
		assertEquals("ccc", cache.get(3));
		assertEquals("ddd", cache.get(4));
	}

	/**
	 * Test case where there the cache is filled after retrievals, then a new
	 * element is added -- the element that have been retrieved least recently
	 * is the first to go.
	 */
	@Test
	public void testReplaceAfterRetrieval() {
		LRUCache cache = new LRUCache(3);

		// Initial fill
		cache.put(1, "aaa");
		cache.put(2, "bbb");
		cache.put(3, "ccc");

		assertEquals(3, cache.getSize());

		// Make retrievals.
		assertEquals("aaa", cache.get(1));
		assertEquals("bbb", cache.get(2));

		// Cause first replacement by new addition.
		cache.put(4, "ddd");

		// Assert contents with retrievals.
		assertEquals(3, cache.getSize());
		assertEquals("bbb", cache.get(2));
		assertNull(cache.get(3));
		assertEquals("ddd", cache.get(4));
		assertEquals("aaa", cache.get(1));

		// Cause second and third replacement by new addition.
		cache.put(5, "eee");
		cache.put(3, "CCC");

		// Assert contents after second and third replacement.
		assertEquals("aaa", cache.get(1));
		assertNull(cache.get(2));
		assertEquals("CCC", cache.get(3));
		assertNull(cache.get(4));
		assertEquals("eee", cache.get(5));
	}
}
