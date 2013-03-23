package moduloCargaBD;


/**
 * En este módulo se crearán las tablas que serán distruibidas en los diferentes servidores de Bases de datos que hacen parte
 * del cluster uGoogle y se hará efectiva la carga de los datos en los equipos correspondientes. Para este propósito se hará uso 
 * del algoritmo ConsistentHashing, que permite conocer en qué servidor (shard) ha de cargarse la tabla asociada a una palabra determinada.
 * (Esto es, las URLs asociadas a una palabra X estarán todas en una misma tabla dentro de un mismo servidor. Es decir, en un servidor con
 * IP x.x.x.x existirá una tabla en que se consignan TODAS las URLs asociadas a un determinado conjunto de palabras. Es decir, si las palabras
 * w1, w2 y w3 tienen todas asignadas un hashCode que las ubica en el mismo servidor S1, entonces la tabla que relaciona las Urls asociadas a una
 * palabra de búsqueda tendría una estructura como sigue:
 * 
 * 
 * 					EN: Servidor S1 con IP: x.x.x.x
 * 					***************************
 *                    Keyword     |  Url
 *                  ***************************
 *                       w1          url11
 *                       w1          url21
 *                       w1          url31
 *                       w2          url12
 *                       w2          url22
 *                       w2          url32
 *                       w3          url13
 *                       w3          url23
 *                       w3          url33
 *                  ****************************
 *                    TABLA 1. Keyword - Url (tabla construida a partir del índice invertido)
 *                    
 * Así, cada que se ingresa la palabra de búsqueda w1, se hará una consulta sobre el servidor S1 con IP x.x.x.x (se sabe que es éste servidor por la
 * aplicación del algoritmo ConsistentHashing sobre la palabra w1), en la que se solicitan todas las URLs
 * con valor en el campo Keyword = w1. El resultado de esta búsqueda serían entonces url11, url21 y url31. 
 * Una vez obtenidas las Urls se procede a consultar el rank asociado a cada una, con el objetivo de que se defina la importancia que tiene esa paǵina (Url)
 * para el criterio de búsqueda ingresado y de esta manera, la interfaz Web pueda mostrar los resultados en orden de relevancia (apareciendo primero la Url con 
 * mayor rank y así de manera descendnente). Dado que previamente en el módulo de Parsing se han obtenido los valores de rank para cada Url, y ya se han almacenado
 * estos valores en la tabla 2 (Url - Rank), entonces se realiza la consulta: Déme los valores de Rank para el campo Url = url11, luego con Url =url21 y finalmente
 * Url = url31. La tabla 2 del servidor S1 tendría entonces la estructura:
 * 
 *       
 * 					EN: Servidor S1 con IP: x.x.x.x
 * 					***************************
 *                       Url     |  Rank
 *                  ***************************
 *                       url11        8
 *                       url21        7
 *                       url31       10
 *                       url12        6
 *                       url22        5
 *                       url32        4
 *                       url13        5
 *                       url23        8
 *                       url33        9
 *                  ****************************
 *                    TABLA 2. Url - Rank (tabla construida a partir del PageRank)
 *
 * Bajo esta estructura, el resultado de la consulta para obtener los valores Rank asociados a las Urls url11, url21 y url31
 * sería 8, 7 y 10, respectivamente. Es decir, la interfaz web mostrará primero url31, luego url11 y finalmente url21. 
 * 
 * @author equipoBD
 *
 */
public class CargaBD {
}
