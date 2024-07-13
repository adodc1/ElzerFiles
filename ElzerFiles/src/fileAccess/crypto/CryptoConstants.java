package fileAccess.crypto;

public interface CryptoConstants {

	public static final byte[] CRYPTOKEY = "c3VwZXJTZWN1cmVL".getBytes();
	public static final String ALGORITMO = "AES";
	public static final int BLOQUES = 128; // Puedes usar 128, 192 o 256 bits
	public static final int CRYPTOBYTESIZE = 16; // tamaño de los bloques de encriptacion.
	public static final double VARIANZALIMITE = 2500.0; // tamaño de los bloques de encriptacion.

}
