package fileAccess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import fileAccess.filesScript.FileScriptAccess;

public class ScriptFile {

	private final FileScriptAccess file;

	public ScriptFile(String fileName) {
		this.file = new FileScriptAccess(fileName);
	}

	public boolean isExistFile() {
		return this.file.isExist();
	}

	/**
	 * <p>
	 * Lee todo el fichero y lo pone en un ArrayList para poderlo leer linea por
	 * linea. El metodo reconoce si el fichero esta encriptado o no.
	 * </p>
	 * 
	 * @return Retorna una lista con todas las lineas leidas.
	 * @throws IOException           Ocurre cuando ha encontrado un problema al leer
	 *                               el fichero.
	 * @throws FileNotFoundException Ocurre cuando no ha encontrado el fichero.
	 */
	public ArrayList<String> readScriptFile() throws IOException, FileNotFoundException {
		if (this.file.isExist()) {
			return this.file.readAll();
		} else {
			throw new FileNotFoundException(this.getClass().getCanonicalName()
					+ ".readScriptFile: Fichero no encontrado " + this.file.getFileName());
		}
	}

	/**
	 * <p>
	 * Escribe todo en un fichero. Es posible elegir si se quiere encriptar el
	 * fichero o no.
	 * </p>
	 * 
	 * @param listData : ArrayList : Lista con todas las lineas.
	 * @param encrypto : boolean : Si es cierto el fichero se grabara con formato
	 *                 encriptado. En caso contrario se grabara como texto plano.
	 * @throws IOException Ocurre cuando se ha encontrado algun problema durante la
	 *                     escritura del fichero.
	 */
	public void writeScriptFile(ArrayList<String> listData, boolean encrypto) throws IOException {
		this.file.writeAll(listData, encrypto);
	}

}
