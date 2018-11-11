#Nombre				:	John Michel Rivera de León.

Algoritmo Elegido	:	Distancia de Levenshtein

Descripción			:	Elegi el Algoritmo de Levenshtein ya que la medida que utiliza para calcular la similitud entre dos palabras es la distancia de edicion.
Este algoritmo cuenta la cantidad de operaciones requeridas para convertir una cadena de caracteres en otra, es rapido y se puede configurar de forma muy sencilla.
Se puede establecer un limite tanto para la distancia, asi como la similitud. de esta forma se puede controlar mejor el resultado que se pueda obtener.
Es decir puedo limitar la distancia a 3 operaciones para limitar los caracteres, y el grado de similitud podria ajustarlo a un porcentaje deseado como un 80%.
Existen multiples algoritmos, dependiento de los datos que se analizan uno sera mas util que otro, para este caso en especifico de nombres, me parece mas eficiente aplicar Levenshtein.

-------------
Ejecutar el programa (consola):
Desde linea de comandos ir a donde se encuentra el jar con las dependencias y ejecutar:
[Agregar nombre]:
java -jar prueba-jar-with-dependencies.jar add {"name":"Alberto Vera Padrón"}
[Listar nombres]
java -jar prueba-jar-with-dependencies.jar list
[Busqueda]
java -jar prueba-jar-with-dependencies.jar fuzzy-search {"search":"Alberto Vera Padrón"}
[o tambien para buscar]
java -jar prueba-jar-with-dependencies.jar search {"search":"Alberto Vera Padrón"}


----------------
Version WEB del programa: 
Tambien agregue una version web del programita para que sea mas comoda la lectura de los JSON.
La URL del servidor a donde lo subi es la siguiente:
http://23.101.129.16/front-MEM/Views/fuzzySearch/Index.html