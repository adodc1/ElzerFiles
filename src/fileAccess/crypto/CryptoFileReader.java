package fileAccess.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * CryptoFileReader
 * 
 * @author Dani
 * @version 1.0.0
 */
public class CryptoFileReader extends FileInputStream {

	private final ArrayList<String> list;
	private Iterator<String> iterador;
	private final CryptoAES crypto;
	private final File file;
	private byte[] fileReaded;

	/**
	 * Un <b>FileInputStream</b> obtiene bytes de entrada de un archivo en un
	 * sistema de archivos. Los archivos que están disponibles dependen del entorno
	 * del host. <b>FileInputStream</b> está diseñado para leer flujos de bytes sin
	 * procesar, como datos de imágenes.
	 * 
	 * @param file : Descriptor del fichero.
	 * @throws FileNotFoundException Si no se encuentra el archivo en la ruta
	 *                               especificada se generará una excepcion.
	 */
	public CryptoFileReader(File file) throws FileNotFoundException {
		super(file);
		this.file = file;
		this.list = new ArrayList<String>();
		this.crypto = new CryptoAES();
	}

	/**
	 * Un <b>FileInputStream</b> obtiene bytes de entrada de un archivo en un
	 * sistema de archivos. Los archivos que están disponibles dependen del entorno
	 * del host. <b>FileInputStream</b> está diseñado para leer flujos de bytes sin
	 * procesar, como datos de imágenes.
	 * 
	 * @param name : nombre y ruta del archivo.
	 * @throws FileNotFoundException Si no se encuentra el archivo en la ruta
	 *                               especificada se generará una excepcion.
	 */
	public CryptoFileReader(String name) throws FileNotFoundException {
		super(name);
		this.file = new File(name);
		this.list = new ArrayList<String>();
		this.crypto = new CryptoAES();
	}

	/**
	 * Crea una lista con todas las lineas procedentes del fichero.
	 */
	public void buildToLine() {
		if (this.fileReaded != null) {
			this.list.addAll(Arrays.asList(new String(this.fileReaded).split("\\r\\n")));
			this.iterador = this.list.iterator();
		}
	}

	/**
	 * Crea una lista con todas las lineas procedentes del fichero.
	 * 
	 * @param buffer : byte[] : bloque de datos.
	 */
	public void buildToLine(byte[] buffer) {
		if (buffer != null) {
			this.list.addAll(Arrays.asList(new String(buffer).split("\\r\\n")));
			this.iterador = this.list.iterator();
		} 
	}

	/**
	 * Lee una linea de texto de el buffer de entrada. Este método se bloquea si aun
	 * no hay ninguna entrada disponible.
	 * 
	 * @return String : cadena de texto que forma una linea.
	 */
	public String readLine() {
		if (iterador != null) {
			if (this.iterador.hasNext()) {
				return this.iterador.next();
			}
		}
		return null;
	}

	/**
	 * Lee todos los bytes restantes del flujo de entrada. Este método se bloquea
	 * hasta que se hayan leído todos los bytes restantes y se detecte el final de
	 * la secuencia o se produzca una excepción. Este método no cierra el flujo de
	 * entrada.
	 * 
	 * Cuando esta secuencia llega al final de la secuencia, más invocaciones de
	 * este método devolverán una matriz de bytes vacía.
	 * 
	 * Si se produce un error de E/S al leer el flujo de entrada, se recomienda
	 * cerrar la transmisión de inmediato.
	 * 
	 * En caso de que el flujo de entrada este encriptado lo desencripta.
	 * 
	 * @return array de bytes que contiene los bytes leidos desde el fichero.
	 * @throws IOException Si ocurre un problema durante la lectura.
	 */
	@Override
	public byte[] readAllBytes() throws IOException {
		Long fileSize = this.file.length();
		byte[] buffer = new byte[fileSize.intValue()];
		for (int i = 0; i < buffer.length; i++) {
			Integer byteReaded = super.read();
			buffer[i] = byteReaded.byteValue();
		}

		return buffer;
	}

	/**
	 * Desencripta una cadena con formato AES. La clave de encriptacion se encuentra
	 * en CryptoConstants.java La cadena debe estar encriptada mediante el metodo
	 * AES. Debe tener un formato de array de byte. El metodo retorna un array de
	 * byte con la cadena en texto plano.
	 * 
	 * @param buffer : byte[] : bloque de datos.
	 * @return Retorna cadena con el texto desencriptado.
	 * @throws Exception puede retornar alguna de las siguientes excepciones:<br>
	 *                   <b>NoSuchAlgorithmException:</b> Si la transformacion es
	 *                   nula, esta vacia, tiene un formato no valido o si ningun
	 *                   proveedor admite una implementacion de CipherSpi para el
	 *                   algoritmo especificado.<br>
	 *                   <b>InvalidKeyException:</b> Si la clave proporcionada no es
	 *                   apropiada para inicializar este cifrado, o requiere
	 *                   parámetros de algoritmo que no se pueden determinar a
	 *                   partir de la clave dada, o si la clave dada tiene un tamaño
	 *                   de clave que excede el tamaño de clave máximo permitido
	 *                   (según lo determinado a partir de los archivos de política
	 *                   de jurisdicción configurados) .
	 */
	public byte[] decrypt(byte[] buffer) throws Exception {
		if (CryptoStadistics.isCrypted(buffer)) {
			this.fileReaded = crypto.decryptoLineString(buffer);
		} else {
			this.fileReaded = buffer;
		}
		return this.fileReaded;
	}

}
