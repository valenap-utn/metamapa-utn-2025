# ServicioAgregador

Seguimos manteniendo la idea de que los hechos deben estar en la fuente.

## Conexion con otros servicios

Volví a poner la Fuente solo con el origen y cree un Factory 
de clients de fuente porque con las decisiones que tomamos anteriormente 
me di cuenta de que el client siempre hace lo mismo (traer hechos con filtros o sin ellos, 
y comunicar a las fuentes de los hechos a marcar como eliminados ) entonces decidí por 
simplicidad y mantenibilidad hacer un único client que tiene como parámetro una clase 
que se encarga de mapear cada hecho de una fuente distinta (por eso hay un factory para 
que se coloque el mapeador correcto), así hay un único punto donde esta la lógica de acceso 
a las fuentes.

Ahora el hecho y la fuente tienen origen el cual permite
comunicarse con este factory y así las entidades de dominio no tienen conocimiento 
de este client. La fuente también tiene origen debido a que las colecciones tienen fuentes y -por nuestro diseño- se necesita para acceder a estos clients o al repository de hechos. El origen contiene la URL y el tipo de fuente, lo cual le permite al sistema tener flexibilidad para crear conexiones y agregar nuevas sin que el sistema conozca todas las que existen.
Igual hay algunos temas que no tengo muy claros: cómo hacer la asignación de los ids en el caso de proxy (existiría un repo para eso o algo así?, sobre todo para el caso de la API que nos brindaron). Se podría poner como endpoint posible para marcar como eliminado uno con el verbo delete? Porque creo que tiene más sentido que con el verbo put que estamos usando.

## Detección de spam
Decidimos que, para no dejar acoplado el detector de spam con la solicitud, el service se encargue
de llevarle la justificación al detector de spam para que determine si es spam o no y es por eso que queda 
desconectado en el diagrama

## Algoritmos de consenso
Se definió usar un patrón Strategy para las colecciones porque los algoritmos se deben cambiar en tiempo de ejecución
y además por extensibilidad dado que pueden crearse facilmente más algoritmos y de esta forma se logra un diseño desacoplado.

A su vez, para poder hacer el cambio del algoritmo de forma performante en tiempo de ejecución, decidimos que los hechos guardaran 
los algoritmos por los cuales han sido determinados como consensuados. 

