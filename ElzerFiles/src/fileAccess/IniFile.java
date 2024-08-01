package fileAccess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;

import fileAccess.filesINI.FileIniAccess;
import fileAccess.filesINI.TableStructure;

public class IniFile {

	private final FileIniAccess ini;
	private final TableStructure dataTable;
	private String section;

	public IniFile(String fileName) {
		this.ini = new FileIniAccess(fileName);
		this.dataTable = new TableStructure();
		this.ini.setDataTable(dataTable);
		this.section = "";
	}

	/**
	 * Retorna el nombre del fichero de configuracion.
	 * 
	 * @return String : Nombre y ruta del fichero.
	 */
	public String getFileName() {
		return this.ini.getFileName();
	}

	/**
	 * Retorna cierto si ha ocurrido algun fallo al acceder al fichero.
	 * 
	 * @return boolean : Si ha ocurrido algun error retorna cierto. Retorna falso en
	 *         caso contrarrio.
	 */
	public boolean isFileError() {
		return this.ini.isFileError();
	}

	/**
	 * Comprueba si existe una determinada seccion.
	 * 
	 * @param sectionName String : Nombre de la seccion.
	 * @return boolean : Retorna cierto si existe. Retorna falso en caso contrario.
	 */
	public boolean isSectionExist(String sectionName) {
		return this.dataTable.isExistSection(sectionName);
	}

	/**
	 * Comprueba si existe un determinado parametro.
	 * 
	 * @param paramName String : Nombre del parametro.
	 * @return boolean : Retorna cierto si existe el parametro. Retorna falso en
	 *         caso contrario.
	 */
	public boolean isParamExist(String sectionName, String paramName) {
		return this.dataTable.isExistParameter(sectionName, paramName);
	}

	/**
	 * Lectura del fichero de configuracion INI.
	 * 
	 * @throws InvalidKeyException   Ocurre si la longitud del nombre de un
	 *                               parametro es igual a cero, si el nombre de la
	 *                               seccion o parametro esta repetido.
	 * @throws IOException           Ocurre si ha surgido algun problema al acceder
	 *                               al fichero.
	 * @throws FileNotFoundException Ocurre sin no ha encontrado el nombre o la ruta
	 *                               del fichero.
	 */
	public void readIniFile() throws InvalidKeyException, IOException, FileNotFoundException {
		if (!this.ini.isFileError()) {
			this.ini.readFile();
		} else {
			throw new FileNotFoundException(this.getClass().getCanonicalName()
					+ ".readIniFile:\nNo se ha encontrado el fichero: " + this.ini.getFileName());
		}
	}

	/**
	 * Escritura del fichero de configuracion.
	 * 
	 * @param encrypto boolean : Si es cierto el fichero de configuracion se
	 *                 guardara encriptado. Si es falso se grabara como texto plano.
	 * @throws IOException Ocurre si ha surgido algun problema al acceder al
	 *                     fichero.
	 */
	public void writeIniFile(boolean encrypto) throws IOException {
		this.ini.writeFile(encrypto);
	}

	/**
	 * Borra todos los elementos de la estructura de datos dejandola vacia.
	 */
	public void removeAll() {
		this.dataTable.removeAll();

	}

	/**
	 * Establece el nombre de una seccion. Posteriormente se podran acceder a sus
	 * parametros.
	 * 
	 * @param name String : Nombre de la seccion.
	 */
	public void setSection(String name) {
		this.section = name;
	}

	/**
	 * Obtener el nombre de la ultima seccion asignada.
	 * 
	 * @return String : Nombre de la seccion.
	 */
	public String getSection() {
		return this.section;
	}

	/**
	 * Inserta el nombre de una seccion.
	 * 
	 * @param name String : Nombre de la seccion.
	 */
	public void addSection(String name) {
		this.section = name;
		this.dataTable.addSection(this.section);
	}

	/**
	 * Inserta un parametro dentro de la estructura de datos. Se inserta como un
	 * parametro-valor.
	 * 
	 * @param name  String : Nombre del parametro.
	 * @param value Object : Valor asociado al parametro.
	 */
	public void addParameter(String name, Object value) {
		this.dataTable.addParameter(this.section, name, value.toString());
	}

	/**
	 * Inserta un parametro en la estructura de datos.
	 * 
	 * @param section String : Nombre de la seccion.
	 * @param param   String : Nombre del parametro.
	 * @param value   Object : Valor asociado al parametro.
	 */
	public void addParameter(String section, String param, Object value) {
		this.section = section;
		this.dataTable.addParameter(this.section, param, value.toString());
	}

	/**
	 * Obtener el valor asociado a un parametro con formato String.
	 * 
	 * @param name : String : Nombre del parametro.
	 * @return String : Valor asociado al parametro.
	 */
	public String getParameterStr(String name) {
		return this.dataTable.getParameterStr(this.section, name);
	}

	/**
	 * Obtener el valor asociado a un parametro con formato String.
	 * 
	 * @param section String : Nombre de la seccion.
	 * @param param   String : Nombre del parametro.
	 * @return String : Valor asociado al parametro.
	 */
	public String getParameterStr(String section, String param) {
		this.section = section;
		return this.dataTable.getParameterStr(this.section, param);
	}

	/**
	 * Obtener el valor asociado a un parametro con formato Integer.
	 * 
	 * @param name String : Nombre del parametro.
	 * @return Integer : Valor asociado al parametro.
	 */
	public Integer getParameterInt(String name) {
		return this.dataTable.getParameterInt(this.section, name);
	}

	/**
	 * Obtener el valor asociado a un parametro con formato Integer.
	 * 
	 * @param section String : Nombre de la seccion.
	 * @param param   String : Nombre del parametro.
	 * @return Integer : Valor asociado al parametro.
	 */
	public Integer getParameterInt(String name, String param) {
		this.section = name;
		return this.dataTable.getParameterInt(this.section, param);
	}

	/**
	 * Obtener el valor asociado a un parametro con formato Double.
	 * 
	 * @param name String : Nombre del parametro.
	 * @return Double : Valor asociado al parametro.
	 */
	public Double getParameterDbl(String name) {
		return this.dataTable.getParameterDbl(this.section, name);
	}

	/**
	 * Obtener el valor asociado a un parametro con formato Double.
	 * 
	 * @param section String : Nombre de la seccion.
	 * @param param   String : Nombre del parametro.
	 * @return Double : Valor asociado al parametro.
	 */
	public Double getParameterDbl(String section, String param) {
		this.section = section;
		return this.dataTable.getParameterDbl(this.section, param);
	}

}
