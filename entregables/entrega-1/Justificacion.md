# Diagrama de Clases
## Por qué elegimos lo que elegimos? 😛
Primeramente detectamos las entidades Hecho y Coleccion
como principales. Debido al principio de responsabilidad única
detectamos que en los requisitos los tipos de personas presentes
en el sistema no tienen comportamiento en esta capa, ya que le
asignamos esas responsabilidades a otras entidades (Colección, Hecho, FuenteExterna).

Para el requerimiento de filtrar, vimos que el comportamiento del
filtrado cambiaba según el tipo de filtro, y que además se pueden 
crear diversos filtros con funciones especificas, con lo cual decidimos
crear una interfaz común de un filtro y crear varios filtros distintos
que la implementen, dando así una solución más flexible, cohesiva ya que
la responsibilidad del filtrado se delega y es más mantenible ya que
permite una modificación más sencilla en un único punto que no afecta
al resto de componentes.

Al realizar el requerimiento de importación de un CSV nos dimos
cuenta de que podrían importarse más tipos de archivos, incluso a
una DB, pero a su vez no sabríamos como es la interfaz respectiva,
por lo cual modificamos/delegamos la importación del CSV y hacemos
que nos entregue una estructura intermedia llamada HechoValueObject.
Como el importador devuelve una colección, nos dimos cuenta de que
había cierto comportamiento en esta colección que no debía estar en
la fuente externa, con lo cual creamos ColeccionHechoValueObject que
se encarga de borrar los repetidos por Titulo repetido y entregar los
Hechos que luego cargaremos.

A su vez, en el importadorCSV, delegamos parte de la responsabilidad
de importar en dos componentes: LectorFilaCSV que se encarga de agarrar
el archivo y transformarlo en una lista de strings para luego cargarlo en 
un formato de filas csv que usaremos; y la clase FormatoFilaCSV que es un 
value Object que se encarga de facilitar la creación de un HechoValueObject
y además recae en él entender como se leen los campos de cada fila del CSV leído.

