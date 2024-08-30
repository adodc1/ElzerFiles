package fileAccess.crypto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * CryptoFileWriter
 * 
 * @author Dani
 * @version 1.0.0
 */
public class CryptoFileWriter extends FileOutputStream {

	private String lista;
	private final CryptoAES crypter;
	private final boolean crypto;

	/**
	 * Este objeto esta diseñado para escribir flujos de bytes sin formato.
	 * 
	 * @param file   Descriptor de fichero.
	 * @param crypto boolean : Si es cierto se encriptaran los datos antes de grabar
	 *               el fichero. En caso contrario se se grabara como texto.
	 * @throws FileNotFoundException si el archivo existe pero es un directorio en
	 *                               lugar de un archivo normal, no existe pero no
	 *                               se puede crear o no se puede abrir por
	 *                               cualquier otro motivo.
	 */
	public CryptoFileWriter(File file, boolean crypto) throws FileNotFoundException {
		super(file);
		this.lista = new String();
		this.crypter = new CryptoAES();
		this.crypto = crypto;
	}

	/**
	 * Este objeto esta diseñado para escribir flujos de bytes sin formato.
	 * 
	 * @param name   String : Nombre y ruta del fichero.
	 * @param crypto boolean : Si es cierto se encriptaran los datos antes de grabar
	 *               el fichero. En caso contrario se se grabara como texto.
	 * @throws FileNotFoundException si el archivo existe pero es un directorio en
	 *                               lugar de un archivo normal, no existe pero no
	 *                               se puede crear o no se puede abrir por
	 *                               cualquier otro motivo.
	 */
	public CryptoFileWriter(String name, boolean crypto) throws FileNotFoundException {
		super(name);
		this.lista = new String();
		this.crypter = new CryptoAES();
		this.crypto = crypto;
	}

	/**
	 * Escribe una linea dentro del buffer.
	 * 
	 * @param text String : Texto para insertar.
	 */
	public void writeLine(String text) {
		this.lista += text + "\r\n";
	}

	@Override
	public void flush() throws IOException {

		if (this.crypto) {
			try {
				super.write(this.crypter.cryptoLineString(this.lista.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			super.write(this.lista.getBytes());
		}
		super.flush();
	}

}
