package moduloEjecutable;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import moduloNaming.ConsistentHash;
import moduloNaming.HashFunction;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/**
		 * 
		 * Se crea una lista con un conjunto de cinco servidores que conforman
		 * el conjunto de shards en que se realizarán las búsquedas.
		 */
		ArrayList lista = new ArrayList();
		lista.add("192.168.130.1:db1");
		lista.add("192.168.130.2:db2");
		lista.add("192.168.130.3:db3");
		// lista.add("192.168.130.4:db4");
		// lista.add("192.168.130.5:db5");

		/**
		 * Esta clase interna define la funci�n hash md5 que se va a utilizar
		 */
		HashFunction hashFunction = new HashFunction() {

			@Override
			public Integer hash(Object key) {
				// TODO Auto-generated method stub

				try {
					MessageDigest md = MessageDigest.getInstance("MD5");
					byte[] messageDigest = md.digest(key.toString().getBytes());
					BigInteger number = new BigInteger(1, messageDigest);
					return number.intValue();

					// return ((hashlib.md5(key).hexdigest(),16);

				} catch (NoSuchAlgorithmException e) {
					throw new RuntimeException(e);
				}
			}
		};

		/**
		 * Se crea un objeto ConsistentHash que usa la funci�n hash definida
		 * anteriormente (MD5) en el conjunto de nodos definidos en la lista,
		 * con 5 r�plicas.
		 */
		ConsistentHash consistentHash = new ConsistentHash(hashFunction, 5,
				lista);

		// consistentHash.add("192");

		// System.out.println(consistentHash.get("1"));

		ArrayList lista2 = new ArrayList();
		for (int i = 0; i < 10; i++) {
			lista2.add(i);
		}

		for (int i = 0; i < lista2.size(); i++) {
			System.out.println("Objeto " + i + " con Hashcode "
					+ consistentHash.get(lista2.get(i)));

		}
		/**
		 * Se agrega el nodo 192 y se realiza el proceso nuevamente para
		 * verificar que se realiza la distribuci�n teniendo en cuenta ese nodo.
		 */
		consistentHash.add("192");

		for (int i = 0; i < lista2.size(); i++) {
			System.out.println("Objeto " + i + " con Hashcode "
					+ consistentHash.get(lista2.get(i)));

		}

		/**
		 * En esta parte del c�digo, a modo de prueba se elimina el nodo 192 que
		 * hab�a sido agregado anteriormente con el fin de ver lo que pasa al
		 * eliminar el nodo y realizar nuevamente el proceso
		 */
		consistentHash.remove("192");
		System.out.println("192 borrado");
		for (int i = 0; i < lista2.size(); i++) {
			System.out.println("Objeto " + i + " con Hashcode  "
					+ consistentHash.get(lista2.get(i)));

		}

		/**
		 * Con este fragmento de c�digo se procede a probar que al eliminar el
		 * nodo y aplicar nuevamente el hashing consistente, la distribuci�n es
		 * correcta
		 */
		System.out.println("Probando!!");
		for (int i = 0; i < lista2.size(); i++) {
			System.out.println("Objeto " + i + " con Hashcode  "
					+ consistentHash.get(lista2.get(i)));

		}

	}
}