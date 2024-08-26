package fileAccess.filesINI;

import java.util.LinkedHashMap;

/**
 * Parameter
 * 
 * @author Dani
 * @version 1.0.0
 * 
 * @param <K> clave
 * @param <V> valor
 */
public class Parameter<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = 1L;

	/**
	 * Parameter
	 */
	public Parameter() {
		super();
	}

	/**
	 * Retorna el valor de un parametro convirtiendolo al formato String.
	 * 
	 * @param key String : Nombre del parametro.
	 * @return Retorna el valor del parametro con formato String.
	 */
	public String getStr(String key) {
		if (this.containsKey(key)) {
			return super.get(key).toString();
		} else {
			return null;
		}
	}

	/**
	 * Retorna el valor de un parametro convirtiendolo al formato Integer.
	 * 
	 * @param key String : Nombre del parametro.
	 * @return Retorna el valor del parametro con formato Integer.
	 */
	public Integer getInt(String key) {
		try {
			return Integer.parseInt((String) super.get(key));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Retorna el valor de un parametro convirtiendolo al formato Double.
	 * 
	 * @param key String : Nombre del parametro.
	 * @return Retorna el valor del parametro con formato Double.
	 */
	public Double getDbl(String key) {
		try {
			return Double.parseDouble((String) super.get(key));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Retorna el valor de un parametro convirtiendolo al formato Long.
	 * 
	 * @param key String : Nombre del parametro.
	 * @return Retorna el valor del parametro con formato Long.
	 */
	public Long getLng(String key) {
		try {
			return Long.parseLong((String) super.get(key));
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
