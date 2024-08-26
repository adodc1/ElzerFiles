package fileAccess.crypto;

/**
 * CryptoConstants
 * 
 * @author Dani
 * @version 1.0.0
 */
public interface CryptoConstants {

	/**
	 * Clave por defecto
	 */
	public static final byte[] CRYPTOKEY = "c3VwZXJTZWN1cmVL".getBytes();
	/**
	 * Tipo de algoritmo de encriptado.
	 */
	public static final String ALGORITMO = "AES";
	/**
	 * Tamaño de los bloques Puedes usar 128, 192 o 256 bits
	 */
	public static final int BLOQUES = 128;
	/**
	 * Tamaño de los bloques de bytes en el buffer.
	 */
	public static final int CRYPTOBYTESIZE = 16;
	/**
	 * Valor de corte en la verificacion de la varianza.
	 */
	public static final double VARIANZALIMITE = 2500.0;

}
