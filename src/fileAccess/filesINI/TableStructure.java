package fileAccess.filesINI;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 * TableStructure
 * 
 * @author Dani
 * @version 1.0.0
 */
public class TableStructure {

	private final Section<String, Parameter<String, String>> dataTable;

	/**
	 * TableStructure
	 */
	public TableStructure() {
		this.dataTable = new Section<String, Parameter<String, String>>();
	}

	/**
	 * Retorna un iterador con los nombres de las secciones.
	 * 
	 * @return Iterator : iterador con los nombres de las secciones.
	 */
	public Iterator<String> iteratorSection() {
		return this.dataTable.keySet().iterator();
	}

	/**
	 * Retorna un iterador con los nombres de los parametros.
	 * 
	 * @param nameSection String : Nombre de la seccion
	 * @return Iterator : iterador con los nombres de los parametros.
	 */
	public Iterator<String> iteratorParamName(String nameSection) {
		if (this.isExistSection(nameSection)) {
			return this.dataTable.get(nameSection).keySet().iterator();
		} else {
			return null;
		}
	}

	/**
	 * Retorna un iterador con los valores de los parametros.
	 * 
	 * @param nameSection String : Nombre de la seccion
	 * @return Iterator : iterador con los valores de los parametros.
	 */
	public Iterator<String> iteratorParamValue(String nameSection) {
		if (this.isExistSection(nameSection)) {
			return this.dataTable.get(nameSection).values().iterator();
		} else {
			return null;
		}
	}

	/**
	 * Inserta una nueva seccion. En caso de que exista una asignación para la
	 * clave, se reemplaza el valor anterior.
	 * 
	 * @param name String : Nombre de la seccion.
	 */
	public void addSection(String name) {
		if (!this.isExistSection(name)) {
			this.dataTable.put(name, new Parameter<String, String>());
		}
	}

	/**
	 * Inserta una nueva seccion. En caso de que exista una asignación para la
	 * clave, se reemplaza el valor anterior. Junto con el nombre de la seccion se
	 * insertan los parametros asociados.
	 * 
	 * @param name      String : Nombre de la seccion.
	 * @param paramList Parametrer : Lista de parametros. Se compone de pares
	 *                  clave-valor.
	 */
	public void addSection(String name, Parameter<String, String> paramList) {
		if (!this.isExistSection(name)) {
			this.dataTable.put(name, paramList);
		}
	}

	/**
	 * Comprueba si existe el nombre de una seccion.
	 * 
	 * @param name String : Nombre de la seccion.
	 * @return Retorna cierto si existe. Retorna falso en caso contrario.
	 */
	public boolean isExistSection(String name) {
		return this.dataTable.containsKey(name);
	}

	/**
	 * Comprueba si existe el nombre de un parametro.
	 * 
	 * @param name  String : Nombre de la seccion.
	 * @param param String : Nombre del parametro.
	 * @return Retorna cierto si existe. Retorna falso en caso contrario.
	 */
	public boolean isExistParameter(String name, String param) {
		if (this.isExistSection(name)) {
			if (this.dataTable.get(name).containsKey(param)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Inserta un parametro dentro de una seccion. Si no se encuentra el nombre de
	 * la seccion no se inserta el parametro.
	 * 
	 * @param name  String : Nombre de la seccion.
	 * @param param String : Nombre del parametro.
	 * @param value String : Valor asociado al parametro.
	 */
	public void addParameter(String name, String param, String value) {
		if (this.isExistSection(name)) {
			this.dataTable.get(name).put(param, value);
		}
	}

	/**
	 * Recupera el valor de un parametro situado dentro de una seccion.
	 * 
	 * @param name  String : Nombre de la seccion.
	 * @param param String : Nombre del parametro.
	 * @return String : Valor asociado al parametro.
	 */
	public String getParameterStr(String name, String param) {
		if (this.isExistSection(name)) {
			if (this.dataTable.get(name).containsKey(param)) {
				return this.dataTable.get(name).getStr(param);
			}
		}
		return null;
	}

	/**
	 * Recupera el valor de un parametro situado dentro de una seccion.
	 * 
	 * @param name  String : Nombre de la seccion.
	 * @param param String : Nombre del parametro.
	 * @return Integer : Valor asociado al parametro.
	 */
	public Integer getParameterInt(String name, String param) {
		if (this.isExistSection(name)) {
			if (this.dataTable.get(name).containsKey(param)) {
				return this.dataTable.get(name).getInt(param);
			}
		}
		return null;
	}

	/**
	 * Recupera el valor de un parametro situado dentro de una seccion.
	 * 
	 * @param nameSection  String : Nombre de la seccion.
	 * @param paramName String : Nombre del parametro.
	 * @return Double : Valor asociado al parametro.
	 */
	public Double getParameterDbl(String nameSection, String paramName) {
		if (this.isExistSection(nameSection)) {
			if (this.dataTable.get(nameSection).containsKey(paramName)) {
				return this.dataTable.get(nameSection).getDbl(paramName);
			}
		}
		return null;
	}

	/**
	 * Recupera el valor de un parametro situado dentro de una seccion.
	 * 
	 * @param nameSection  String : Nombre de la seccion.
	 * @param paramName String : Nombre del parametro.
	 * @return Long : Valor asociado al parametro.
	 */
	public Long getParameterLng(String nameSection, String paramName) {
		if (this.isExistSection(nameSection)) {
			if (this.dataTable.get(nameSection).containsKey(paramName)) {
				return this.dataTable.get(nameSection).getLng(paramName);
			}
		}
		return null;
	}

	/**
	 * Borra todos los elementos de la estructura de datos dejandola vacia.
	 * 
	 */
	public void removeAll() {
		for (Entry<String, Parameter<String, String>> entry : this.dataTable.entrySet()) {
			Parameter<String, String> val = entry.getValue();
			val.clear();
		}
		this.dataTable.clear();
		System.gc();
	}

}
