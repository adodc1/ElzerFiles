package fileAccess.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * CryptoAES
 * 
 * @author Dani
 * @version 1.0.0
 */
public class CryptoAES {

	/**
	 * Encripta una cadena de texto con formato AES. La clave de encriptacion se
	 * encuentra en CryptoConstants.java
	 * 
	 * La cadena debe ser de caracteres de texto plano. Debe tener un formato de
	 * array de byte.
	 * 
	 * El metodo retorna un array de byte con la cadena encriptada.
	 * 
	 * @param data : byte[] : La cadena para ser encriptada.
	 * @return : La cadena encriptada.
	 * @throws Exception puede retornar las siguientes excepciones:<br>
	 *                   <b>IllegalStateException:</b> El metodo no se ha
	 *                   inicializado.<br>
	 *                   <b>IllegalBlockSizeException:</b> La longitud del texto no
	 *                   es multiplo del tamano del bloque.
	 */
	public byte[] cryptoLineString(byte[] data) throws Exception {
		SecretKey key = localKeyGenerator();
		Cipher c = Cipher.getInstance(CryptoConstants.ALGORITMO);
		c.init(Cipher.ENCRYPT_MODE, key);
		return c.doFinal(data);
	}

	/**
	 * Desencripta una cadena con formato AES. La clave de encriptacion se encuentra
	 * en CryptoConstants.java
	 * 
	 * La cadena debe estar encriptada mediante el metodo AES. Debe tener un formato
	 * de array de byte.
	 * 
	 * El metodo retorna un array de byte con la cadena en texto plano.
	 * 
	 * @param data : byte[] : La cadena para ser desencriptada.
	 * @return cadena en texto plano. En caso de existir un fallo retorna null.
	 * @throws Exception puede retornar alguna de las siguientes excepciones:<br>
	 *                   <b>NoSuchAlgorithmException:</b> si la transformacion es
	 *                   nula, esta vacia, tiene un formato no valido o si ningun
	 *                   proveedor admite una implementacion de CipherSpi para el
	 *                   algoritmo especificado.<br>
	 *                   <b>InvalidKeyException:</b> si la clave proporcionada no es
	 *                   apropiada para inicializar este cifrado, o requiere
	 *                   parámetros de algoritmo que no se pueden determinar a
	 *                   partir de la clave dada, o si la clave dada tiene un tamaño
	 *                   de clave que excede el tamaño de clave máximo permitido
	 *                   (según lo determinado a partir de los archivos de política
	 *                   de jurisdicción configurados) .
	 */
	public byte[] decryptoLineString(byte[] data) throws Exception {
		SecretKey key = localKeyGenerator();
		Cipher c = Cipher.getInstance(CryptoConstants.ALGORITMO);
		c.init(Cipher.DECRYPT_MODE, key);
		try {
			byte[] decValue = c.doFinal(data);
			return decValue;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Generador de claves secretas (simétricas).
	 * <p>
	 * Se admiten los siguientes formatos:<br>
	 * AES (128)<br>
	 * DESede (168)<br>
	 * HmacSHA1<br>
	 * HmacSHA256
	 * </p>
	 * 
	 * @return SecretKey : Proporciona la clave secreta para el encriptado.
	 * @throws Exception puede retornar alguna de las siguientes excepciones:<br>
	 *                   <b>NoSuchAlgorithmException:</b> si ningún proveedor admite
	 *                   una implementación de KeyGeneratorSpi para el algoritmo
	 *                   especificado<br>
	 *                   <b>NullPointerException:</b> si el algoritmo es nulo
	 */
	public SecretKey localKeyGenerator() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance(CryptoConstants.ALGORITMO);
		keyGen.init(CryptoConstants.BLOQUES); // Puedes usar 128, 192 o 256 bits
		SecretKey secretKey = new SecretKeySpec(CryptoConstants.CRYPTOKEY, CryptoConstants.ALGORITMO);
		return secretKey;
	}

}
