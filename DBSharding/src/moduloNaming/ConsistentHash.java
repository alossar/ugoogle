package moduloNaming;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import moduloNaming.HashFunction;


public class ConsistentHash<T> {

	/**
	 * La clase ConsistentHash define el comportamiento del c�rculo que permitirá determinar en dónde se buscará la información asociada a las palabras de búsqueda
	 * ingresadas por el usuario. Esta estructura está formada por tres atributos: una instancia de la interfaz HashFunction, un número de réplicas para los nodos
	 * y el circulo en sí mismo, que se implementará mediante un TreeMap (para propósitos de utilizar búsqueda binaria).
	 */
	private final HashFunction hashFunction;
	private final int numberOfReplicas;
	// el circulo es representado como un mapa de enteros
	private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();

	/**
	 * En la construcción del ConsistentHashing se inicializan los atributos con los valores pasados por parámetro, y se agregan los nodos que serán parte del conjunto
	 * de servidores (shards) sobre los que se realizarán las búsquedas correspondientes. 
	 * En cuanto a las r�plicas, hay un n�mero definido para todos los servidores. Estas r�plicas hacen las veces de nodos 
	 * virtuales, a los cuales se asignar� la misma IP del servidor real al cual hagan referencia.
	 * @param hashFunction
	 * @param numberOfReplicas
	 * @param nodes
	 */
	
	public ConsistentHash(HashFunction hashFunction, int numberOfReplicas,
			Collection<T> nodes) {

		this.hashFunction = hashFunction;
		this.numberOfReplicas = numberOfReplicas;

		for (T node : nodes) {
			add(node);
		}
	}
	// Agregar nodos es agregar un nuevo servidor, guardo el nodo para saber la
	// localizacion
	// de mi nuevo nodo
	
	/**
	 * En este método se ejecuta la edición de un nuevo nodo al conjunto de shards en los que se almacenará la información de la Base de Datos. A cada uno de los nodos se asocian cinco réplicas.
	 * Se utiliza el método put para este propósito, asociando a cada nodo los códigos Hash de sus réplicas.
	 * @param node
	 */
	public void add(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.put(hashFunction.hash(node.toString() + i), node);
			System.out.println("hash del servidor "+node.toString() + i +" "+hashFunction.hash(node.toString()+i));
		}
		}
	/**
	 * En caso de que se necesite remover algún nodo del conjunto total de shards que se haya definido inicialmente. Para esto, se remueven del círculo todos los códigos hash
	 * de las réplicas que previamente se hayan asignado a cada nodo. 
	 * @param node
	 */
		public void remove(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.remove(hashFunction.hash(node.toString() + i));
		}
		}
		/**
	 * Este método retorna el servidor donde se buscarán las URLs asociadas a la
	 * palabra clave ingresada (el parámetro key aquí definido). Una vez se ha verificado
	 * que el círculo no está vacío, se calcula el código hash de la palabra clave y se compara
	 * este valor con cada uno de los códigos hash que son claves en el TreeMap que representa el círculo. Si el círculo no contiene el código hash
	 * de la palabra clave, entonces se intenta buscar objetos valor cuyo código hash sea mayor al pasado por parámetro. Para este propósito se hace uso 
	 * del método tailMap(hash), que retorna la porción del círculo que cumple con ese criterio. Sobre este círculo se obtiene entonces el hash más cercano 
	 * al hash de la palabra clave. Finalmente se retorna el nodo asociado con el hash más cercano. Éste nodo será aquel donde se realizará la búsqueda requerida.
	 * @param key
	 * @return
	 */
	public T get(Object key) {
		if (circle.isEmpty()) {
			return null;
		}
		int hash = hashFunction.hash(key);
		if (!circle.containsKey(hash)) {
			SortedMap<Integer, T> tailMap = circle.tailMap(hash);
			//System.out.println(" llave 1  " + circle.tailMap(hash).firstKey() + "  Ultima llave  " + circle.tailMap(hash).lastKey());
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		return circle.get(hash);
	}
}
