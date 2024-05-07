package fileAccess.crypto;

import java.util.Arrays;
import java.util.Comparator;

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
		if (calcularVarianza(buffer) > 2000.0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Calculo de la desviacion tipica por tramos. La desviacion tipica de un bloque
	 * de datos encriptado es muy baja debido a que las frecuencias de los bytes
	 * esta repartida entre 0 y 255. En caso de tratarse de un texto plano la
	 * desviacion tipica sera mayor debido a que las frecuencias son muy bajas en
	 * las zonas 0 al 32 y tambien 90 al 255.
	 * 
	 * @param buffer    String : texto para verificar.
	 * @param numTramos zonas sobre las que se aplicara la desviacion tipica.
	 * @return double : Diferencia de los valores de la desviacion tipica.
	 */
	@SuppressWarnings("unused")
	private static double desvestaTramos(byte[] buffer, int numTramos) {

		byte[] valores = Arrays.copyOf(buffer, buffer.length);
		Byte[] desviacionTipica = new Byte[numTramos];

		// Tamaño de cada tramo
		int tamTramo = valores.length / numTramos;

		// Ordenar los datos
		Arrays.sort(valores);

		for (int i = 0; i < numTramos; i++) {

			// Calcular la media
			double suma = 0;
			for (int j = i * tamTramo; j < (i + 1) * tamTramo; j++) {
				suma += valores[j];
			}
			double media = suma / tamTramo;

			// Calcular la varianza
			double varianza = 0;
			for (int j = i * tamTramo; j < (i + 1) * tamTramo; j++) {
				varianza += Math.pow(valores[j] - media, 2);
			}
			varianza /= tamTramo;

			// Calcular la desviación típica
			desviacionTipica[i] = (byte) (Math.round(Math.sqrt(varianza)) & 0x00FF);
		}

		Arrays.sort(desviacionTipica, new Comparator<Byte>() {

			public int compare(Byte o1, Byte o2) {
				return o2 - o1;
			}
		});

		return (desviacionTipica[0] - desviacionTipica[desviacionTipica.length - 1]) * 1.0;
	}

	/**
	 * Calculo de la desviacion tipica por tramos. La desviacion tipica de un bloque
	 * de datos encriptado es muy baja debido a que las frecuencias de los bytes
	 * esta repartida entre 0 y 255. En caso de tratarse de un texto plano la
	 * desviacion tipica sera mayor debido a que las frecuencias son muy bajas en
	 * las zonas 0 al 32 y tambien 90 al 255.
	 * 
	 * @param buffer byte[] : texto para verificar.
	 * @return double : desviacion tipica.
	 */
	@SuppressWarnings("unused")
	private static double desvestaPonderada(byte[] buffer) {

		byte[] datos = Arrays.copyOf(buffer, buffer.length);

		// Obtiene las frecuencias.
		int[] frecuencias = new int[256];
		for (byte b : buffer) {
			frecuencias[b & 0xFF]++;
		}

		// Calcular la suma de los datos y la suma de las frecuencias
		double sumaDatos = 0;
		int sumaFrecuencias = 0;
		for (int i = 0; i < datos.length; i++) {
			sumaDatos += datos[i] * frecuencias[datos[i] & 0xFF];
			sumaFrecuencias += frecuencias[datos[i] & 0xFF];
		}

		// Calcular la media
		double media = sumaDatos / sumaFrecuencias;

		// Calcular la varianza
		double varianza = 0;
		for (int i = 0; i < datos.length; i++) {
			varianza += frecuencias[datos[i] & 0xFF] * Math.pow(datos[i] - media, 2);
		}
		varianza /= sumaFrecuencias;

		// Calcular la desviación típica
		double desviacionTipica = Math.sqrt(varianza);

		return desviacionTipica;
	}

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
	 * @param datos : byte[] : texto para verificar.
	 * @return double : varianza.
	 */
	private static double mediaAritmetica(byte[] datos) {
		long suma = 0;
		for (long dato : datos) {
			suma += dato;
		}
		return suma / datos.length;
	}

}
