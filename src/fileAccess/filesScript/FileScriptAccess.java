package fileAccess.filesScript;

import java.io.File;
import java.io.IOException;
import java.util.*;

import fileAccess.crypto.*;

/**
 * FileScriptAccess
 * 
 * @author Dani
 * @version 1.0.0
 */
public class FileScriptAccess {

	private final File fichero;
	private boolean fileExist;
	private boolean fileError;

	/**
	 * FileScriptAccess
	 * 
	 * @param fileName Nombre del fichero
	 */
	public FileScriptAccess(String fileName) {
		if (fileName != null) {
			this.fichero = new File(fileName);
			this.fileExist = this.fichero.exists();
			this.fileError = !this.fileExist;
		} else {
			throw new NullPointerException(
					this.getClass().getCanonicalName() + ".FileScriptAccess: El nombre del fichero es nulo");
		}
	}

	/**
	 * Retorna el nombre y ruta del fichero.
	 * 
	 * @return String : Nombre y ruta del fichero
	 */
	public String getFileName() {
		return this.fichero.getName();
	}

	/**
	 * Retorna el resultado del ultimo acceso a fichero.
	 * 
	 * @return Retorna cierto si ha ocurrido algun fallo durante el acceso al
	 *         fichro. Retrna falso en caso contrario.
	 */
	public boolean isError() {
		return this.fileError;
	}

	/**
	 * Comprueba la existencia del fichero antes de ser leido.
	 * 
	 * @return Retorna cierto si existe el fichero en la ruta especificada. Retorna
	 *         falso en caso contrario.
	 */
	public boolean isExist() {
		return this.fileExist;
	}

	/**
	 * Lee todo el fichero y lo pone en un ArrayList para poderlo leer linea por
	 * linea. El metodo reconoce si el fichero esta encriptado o no.
	 * 
	 * @return Retorna una lista con todas las lineas leidas.
	 * @throws IOException Ocurre cuendo ha encontrado un problema al leer el
	 *                     fichero.
	 */
	public ArrayList<String> readAll() throws IOException {
		ArrayList<String> listaOrdenes = new ArrayList<String>();
		try {
			if (this.fichero.canRead() && this.fichero.canWrite() && this.fichero.exists()
					&& this.fichero.length() > 0) {

				CryptoFileReader buffer = new CryptoFileReader(this.fichero);

				byte[] bloqueLeido = buffer.readAllBytes();
				byte[] desencriptado = buffer.decrypt(bloqueLeido);
				buffer.buildToLine(desencriptado);

				String lineaLeida = buffer.readLine();
				while (lineaLeida != null) {
					if ((!lineaLeida.startsWith("#")) && (lineaLeida.length() > 1)) {
						listaOrdenes.add(lineaLeida);
					}
					lineaLeida = buffer.readLine();
				}
				buffer.close();
			}

		} catch (IOException e) {
			this.fileError = true;
			listaOrdenes.removeAll(listaOrdenes);
			IOException ex = new IOException(this.getClass().getCanonicalName() + ".readAll: \n" + e.getMessage());
			ex.setStackTrace(e.getStackTrace());
			throw ex;

		} catch (Exception e) {
			IOException ex = new IOException(this.getClass().getCanonicalName() + ".readAll: \n" + e.getMessage());
			ex.setStackTrace(e.getStackTrace());
			throw ex;
		}
		return listaOrdenes;
	}

	/**
	 * Escribe todo en un fichero. Es posible elegir si se quiere encriptar el
	 * fichero o no.
	 * 
	 * @param lista    ArrayList : Lista con todas las lineas.
	 * @param encrypto boolean : Si es cierto el fichero se grabara con formato
	 *                 encriptado. En caso contrario se grabara como texto plano.
	 * @throws IOException Ocurre cuando se ha encontrado algun problema durante la
	 *                     escritura del fichero.
	 */
	public void writeAll(ArrayList<String> lista, boolean encrypto) throws IOException {

		try {
			CryptoFileWriter buffer = new CryptoFileWriter(this.fichero, encrypto);
			for (String linea : lista) {
				buffer.writeLine(linea);
			}
			buffer.flush();
			buffer.close();

		} catch (IOException e) {
			this.fileError = true;
			IOException ex = new IOException(this.getClass().getCanonicalName() + ".writeFile: \n" + e.getMessage());
			ex.setStackTrace(e.getStackTrace());
			throw ex;
		}

	}

}
