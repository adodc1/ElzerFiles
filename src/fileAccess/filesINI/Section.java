package fileAccess.filesINI;

import java.util.LinkedHashMap;

/**
 * Section
 * 
 * @author Dani
 * @version 1.0.0
 * 
 * @param <K> Clave
 * @param <V> Valor
 */
public class Section<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 1L;

	/**
	 * Section
	 */
	public Section() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public V put(K key, V value) {
		if (key instanceof String) {
			return super.put((K) key.toString().trim().toUpperCase(), value);
		} else {
			return super.put(key, value);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		if (key instanceof String) {
			return super.get((K) key.toString().trim().toUpperCase());
		} else {
			return super.get(key);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean containsKey(Object key) {
		if (key instanceof String) {
			return super.containsKey((K) key.toString().trim().toUpperCase());
		} else {
			return super.containsKey(key);
		}
	}

}
