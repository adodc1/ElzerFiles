package fileAccess.crypto;

/**
 * CryptoStadistics
 * 
 * @author Dani
 * @version 1.0.0
 */
public abstract class CryptoStadistics {

	/**
	 * Comprueba si el texto introducido corresponde a un texto ecriptado o un texto
	 * plano.
	 * 
	 * @param buffer byte : texto para comprobar.
	 * @return Si el texto esta encriptado retornara cierto. Retornara falso en caso
	 *         sontrario.
	 */
	public static boolean isCrypted(byte[] buffer) {
		if (isMultiplo16(buffer)) {
			if (isTextoEncriptado(buffer)) {
				if (calcularVarianza(buffer) > CryptoConstants.VARIANZALIMITE) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Calcula el valor de la varianza aplicado a todos los bytes del buffer. El
	 * resultado muestra la dispersion de los datos. La dispersion de los datos es
	 * mayor en ficheros de texto plano que en ficheros encriptados. Esto es debido
	 * a que los caracteres legibles estan concentrados en una parte del espectro de
	 * la tabla ascii. Sin embargo, los encriptados ocupan toda la tabla ascii.
	 * 
	 * @param datos byte : buffer de datos.
	 * @return double : resultado del calculo de la varianza.
	 */
	public static double calcularVarianza(byte[] datos) {
		double sumaDiferenciasCuadradas = 0.0;
		double media = mediaAritmetica(datos);
		for (byte dato : datos) {
			sumaDiferenciasCuadradas += Math.pow(dato - media, 2);
		}
		return sumaDiferenciasCuadradas / datos.length;
	}

	/**
	 * La varianza es una medida estadística que indica la dispersión de un conjunto
	 * de datos respecto a su media. Una varianza alta sugiere que los datos están
	 * más dispersos, mientras que una varianza baja indica que están más agrupados
	 * cerca de la media. En un fichero encriptado los datos estan muy dispersos.
	 * 
	 * @param datos : byte : texto para verificar.
	 * @return double : varianza.
	 */
	private static double mediaAritmetica(byte[] datos) {
		long suma = 0;
		for (long dato : datos) {
			suma += dato;
		}
		return suma / datos.length;
	}

	private static boolean isMultiplo16(byte[] datos) {
		return (datos.length % CryptoConstants.CRYPTOBYTESIZE == 0);
	}

	private static boolean isTextoEncriptado(byte[] datos) {
		double charEspecial = 0;
		for (byte dato : datos) {
			if ((dato < 32) || (dato > 122)) {
				charEspecial++;
			}
		}
		double frecRelEspecial = (charEspecial / datos.length) * 100;

		return (frecRelEspecial > 5.0);
	}

}
