package fileAccess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;

import fileAccess.filesINI.FileIniAccess;
import fileAccess.filesINI.TableStructure;

/**
 * IniFile
 * 
 * @author Dani
 * @version 1.0.0
 */
public class IniFile {

	private final FileIniAccess ini;
	private final TableStructure dataTable;
	private String section;

	/**
	 * IniFile
	 * 
	 * @param fileName Nombre del fichero
	 */
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
	 * @param sectionName String : Nombre de la seccion.
	 * @param paramName   String : Nombre del parametro.
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
	 * @param sectionName String : Nombre de la seccion.
	 */
	public void setSection(String sectionName) {
		this.section = sectionName;
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
	 * @param sectionName String : Nombre de la seccion.
	 */
	public void addSection(String sectionName) {
		this.section = sectionName;
		this.dataTable.addSection(this.section);
	}

	/**
	 * Inserta un parametro dentro de la estructura de datos. Se inserta como un
	 * parametro-valor.
	 * 
	 * @param paramName String : Nombre del parametro.
	 * @param value     Object : Valor asociado al parametro.
	 */
	public void addParameter(String paramName, Object value) {
		this.dataTable.addParameter(this.section, paramName, value.toString());
	}

	/**
	 * Inserta un parametro en la estructura de datos.
	 * 
	 * @param sectionName String : Nombre de la seccion.
	 * @param paramName   String : Nombre del parametro.
	 * @param value       Object : Valor asociado al parametro.
	 */
	public void addParameter(String sectionName, String paramName, Object value) {
		this.section = sectionName;
		this.dataTable.addParameter(this.section, paramName, value.toString());
	}

	/**
	 * Obtener el valor asociado a un parametro con formato String.
	 * 
	 * @param paramName : String : Nombre del parametro.
	 * @return String : Valor asociado al parametro.
	 */
	public String getParameterStr(String paramName) {
		return this.dataTable.getParameterStr(this.section, paramName);
	}

	/**
	 * Obtener el valor asociado a un parametro con formato String.
	 * 
	 * @param sectionName String : Nombre de la seccion.
	 * @param paramName   String : Nombre del parametro.
	 * @return String : Valor asociado al parametro.
	 */
	public String getParameterStr(String sectionName, String paramName) {
		this.section = sectionName;
		return this.dataTable.getParameterStr(this.section, paramName);
	}

	/**
	 * Obtener el valor asociado a un parametro con formato Integer.
	 * 
	 * @param paramName String : Nombre del parametro.
	 * @return Integer : Valor asociado al parametro.
	 */
	public Integer getParameterInt(String paramName) {
		return this.dataTable.getParameterInt(this.section, paramName);
	}

	/**
	 * Obtener el valor asociado a un parametro con formato Integer.
	 * 
	 * @param sectionName String : Nombre de la seccion.
	 * @param paramName   String : Nombre del parametro.
	 * @return Integer : Valor asociado al parametro.
	 */
	public Integer getParameterInt(String sectionName, String paramName) {
		this.section = sectionName;
		return this.dataTable.getParameterInt(this.section, paramName);
	}

	/**
	 * Obtener el valor asociado a un parametro con formato Double.
	 * 
	 * @param paramName String : Nombre del parametro.
	 * @return Double : Valor asociado al parametro.
	 */
	public Double getParameterDbl(String paramName) {
		return this.dataTable.getParameterDbl(this.section, paramName);
	}

	/**
	 * Obtener el valor asociado a un parametro con formato Double.
	 * 
	 * @param sectionName String : Nombre de la seccion.
	 * @param paramName   String : Nombre del parametro.
	 * @return Double : Valor asociado al parametro.
	 */
	public Double getParameterDbl(String sectionName, String paramName) {
		this.section = sectionName;
		return this.dataTable.getParameterDbl(this.section, paramName);
	}

}
