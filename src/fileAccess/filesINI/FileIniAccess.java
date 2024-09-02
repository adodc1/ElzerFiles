package fileAccess.filesINI;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Iterator;

import fileAccess.crypto.*;

/**
 * FileIniAccess
 * 
 * @author Dani
 * @version 1.0.0
 */
public class FileIniAccess {

	private final File file;
	private boolean fileError = false;
	private TableStructure table;

	/**
	 * Crea el descriptor de fichero para acceder a los datos.<br>
	 * Comprueba que el fichero existe y se puede leer.
	 * 
	 * @param name : String : Ruta hasta el fichero ini.
	 * @throws NullPointerException Ocurre si el nombre del fichero es nulo.
	 */
	public FileIniAccess(String name) {
		if (name != null) {
			this.file = new File(name);
			this.fileError = !this.file.isFile();
		} else {
			throw new NullPointerException(
					this.getClass().getCanonicalName() + ".FileIniAccess: El nombre del fichero es nulo");
		}
		this.table = null;
	}

	/**
	 * Devuelve la ruta al fichero ini.
	 * 
	 * @return String : Ruta hasta el ficheor ini.
	 */
	public String getFileName() {
		return this.file.getName();
	}

	/**
	 * Comprueba si ha ocurrido algun error al acceder al fichero.
	 * 
	 * @return Retorna cierto si ha habido algun error. Retorna falso en caso
	 *         contrario.
	 */
	public boolean isFileError() {
		return this.fileError;
	}

	/**
	 * Asigna la estructura de datos que contendra los datos para enviar al fichero
	 * o para leer desde el fichero.
	 * 
	 * @param table TableStructure : Estructura de datos
	 */
	public void setDataTable(TableStructure table) {
		this.table = table;
	}

	/**
	 * Lee un fichero de configuracion dejando los datos en una estructura de tipo
	 * <b>TableStructure</b>.
	 * 
	 * @throws InvalidKeyException Ocurre si la longitud del nombre de un parametro
	 *                             es igual a cero, si el nombre de la seccion o
	 *                             parametro esta repetido.
	 * @throws IOException         Ocurre si ha surgido algun problema al acceder al
	 *                             fichero.
	 */
	public void readFile() throws InvalidKeyException, IOException {
		if (this.table != null) {
			this.fileError = false;
			try {
				CryptoFileReader buffer = new CryptoFileReader(this.file);
				String readedLine, section = "";
				String[] parametro;

				byte[] byteBlockReaded = buffer.readAllBytes();
				byte[] decrypted = buffer.decrypt(byteBlockReaded);
				buffer.buildToLine(decrypted);

				while ((readedLine = buffer.readLine()) != null) {
					if ((!readedLine.startsWith("#")) && (readedLine.length() > 1)) {
						if (readedLine.trim().startsWith("[") && readedLine.trim().endsWith("]")) {
							// Lectura de una seccion.
							section = readedLine.replace('[', ' ').replace(']', ' ').trim().toUpperCase();
							if (!this.table.isExistSection(section)) {
								this.table.addSection(section);
							} else {
								this.fileError = true;
								buffer.close();
								throw new InvalidKeyException(this.getClass().getCanonicalName() + ".readIniFile:\n"
										+ "Seccion repetida <" + section + "> en el fichero " + this.file.getPath());
							}
						} else {
							// Lectura de un parametro. El array siempre tendra dos elementos.
							parametro = readedLine.trim().split("=", 2);

							if (parametro.length == 2) {
								// El parametro tiene un valor asignado.
								if (parametro[0].length() > 0) {
									if (!this.table.isExistParameter(section, parametro[0])) {
										this.table.addParameter(section, parametro[0],
												(parametro[1].trim().split("#"))[0]);
									} else {
										this.fileError = true;
										buffer.close();
										throw new InvalidKeyException(this.getClass().getCanonicalName()
												+ ".leerFichero:\n" + "Parametro <" + readedLine
												+ "> esta repetido en la seccion <" + section + ">");
									}
								} else {
									this.fileError = true;
									buffer.close();
									throw new InvalidKeyException(this.getClass().getCanonicalName() + ".readIniFile:\n"
											+ "Error en parametro <" + readedLine + "> de la seccion <" + section
											+ ">");
								}
							}
						}
					}
				}
				buffer.close();

			} catch (IOException e) {
				this.fileError = true;
				IOException ex = new IOException(this.getClass().getCanonicalName() + ".readFile: \n" + e.getMessage());
				ex.setStackTrace(e.getStackTrace());
				throw ex;

			} catch (Exception e) {
				IOException ex = new IOException(this.getClass().getCanonicalName() + ".readFile: \n" + e.getMessage());
				ex.setStackTrace(e.getStackTrace());
				throw ex;
			}
		}
	}

	/**
	 * Escribe un fichero de configuracion desde una estructura de tipo
	 * <b>TableStructure</b>. El fichero puede ser encriptado o texto plano.
	 * 
	 * @param encrypto boolean : Cierto si el fichero debe estar encriptado. Falso
	 *                 en caso contrario.
	 * @throws IOException Ocurre si ha surgido algun problema al grabar el fichero.
	 */
	public void writeFile(boolean encrypto) throws IOException {
		if (this.table != null) {
			this.fileError = false;
			try {
				CryptoFileWriter buffer = new CryptoFileWriter(this.file, encrypto);
				Iterator<String> itSection = this.table.iteratorSection();
				while (itSection.hasNext()) {
					String section = (String) itSection.next();
					buffer.writeLine("#");
					buffer.writeLine("[" + section + "]");
					Iterator<String> itParamName = this.table.iteratorParamName(section);
					Iterator<String> itParamValue = this.table.iteratorParamValue(section);
					while (itParamName.hasNext() && itParamValue.hasNext()) {
						String name = (String) itParamName.next();
						String value = (String) itParamValue.next();
						buffer.writeLine(name + "=" + value);
					}
				}
				buffer.flush();
				buffer.close();

			} catch (IOException e) {
				this.fileError = true;
				IOException ex = new IOException("writeIniFile: " + e.getMessage());
				ex.setStackTrace(e.getStackTrace());
				throw ex;
			}
		}
	}

}
