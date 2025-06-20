# ServicioAgregador

Seguimos manteniendo la idea de que los hechos deben estar en la fuente.

## Conexion con otros servicios
Tuvimos problemas al conectarnos con las fuentes en el service de Colecciones.
El código quedaba engorroso, había mucha repetición de lógica y  además para acceder
a que conexión le debíamos pedir los hechos se tenía que hacer un Type Test.
A su vez, vimos que en el enunciado de la primer entrega estaba la posibilidad de
agregar varias fuentes de un mismo tipo.

Para solucionar estos problemas decidimos delegar esta parte de las conexiones a
otras API a la clases que siguen la interfaz de la clase abstracta ConexionFuenteService.
Esta decide en ese caso si debe actualizarla o pedir los hechos directamente según
su clase implementada. 

Entonces el Service de colección lo que tendrá es un conjunto de conexiones que se guardan 
conforme a un ID que será univoco para el sistema, lo cual permitirá guardar en la base de 
datos los hechos ya que los ids se pueden repetir entre las fuentes. También tomamos la decisión
de colocarle a su vez el id a la fuente, para facilitar la búsqueda de la conexión que
corresponda y porque el service de colección se lo debe preguntar ya que forma parte del
caso de uso de actualizar la colección.

