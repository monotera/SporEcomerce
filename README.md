# SporeEcomerce

SporeEcomerce es un juego de estrategia y comercio ambientado en el año 31337, en
que la humanidad ha colonizado muchos sistemas estelares. Un equipo de jugadores son la tripulación
de una nave comerciante, la cual viaja de planeta en planeta, comprando y vendiendo bienes de diverso
tipo. El objetivo del juego es ganar la mayor cantidad de dinero en un plazo determinado. Para ello, el
sistema debe llevar registro del dinero que tiene el equipo, así como del tiempo transcurrido en los
viajes realizados de un planeta a otro. En el año 31337, el espacio conocido comprende un cubo de 300 años luz de lado con
aproximadamente 40.000 estrellas. La nave espacial tiene un computador a bordo con las coordenadas
(x,y,z) de todas las estrellas, indicando cuáles son habitadas o no. Aproximadamente el 1% de las
estrellas tienen uno o más planetas habitados.

## 1 Comercio

Cada vez que el comerciante llega a un planeta, el sistema debe mostrarle un listado de los productos
que puede comprar o vender en cada planeta. La unidad monetaria es el “crédito” y se usa para todas
las transacciones de compra y venta. Los precios unitarios de venta de cada producto (PV) se calculan con la siguiente fórmula:

PV = FD / (1 +S)<br/>
Donde
FD: Factor de demanda. Es diferente para cada producto y para cada planeta. Fluctúa entre 0 y
1.000.
S: Stock del producto en el planeta. Fluctúa entre 0 y 1.000.

Mientras mayor sea el stock de un producto en un planeta, menor será el precio al cual el comerciante
podrá venderlo.

En forma similar, el precio unitario de compra (PC) se calcula con la siguiente fórmula

PC = FO / (1 + S)<br/>
Donde
FO: Factor de oferta. Es diferente para cada producto y para cada planeta. Fluctúa entre 0 y 1.000.

## 2 Viaje Espacial

En el año 31337 los viajes interestelares son muchísimo más expeditos que en el pasado, gracias a una
red de agujeros de gusano que conectan muchas estrellas del espacio conocido. Los agujeros de gusano
permiten viajar a una velocidad promedio de 1 año-luz por día.

Debido al enorme costo de fabricar un agujero de gusano, no todas las estrellas están conectadas
directamente a través de esta red. Eso significa que para ir de una estrella A a una estrella B, no siempre
habrá una ruta directa a través de un agujero de gusano, sino que habrá que buscar una ruta indirecta a
través de otras estrellas^1. Afortunadamente, todos las estrellas habitadas tienen rutas directas o

1 Por supuesto, algunas estrellas muy cercanas pueden ser recorridas a través del espacio normal a
velocidades relativistas. Sin embargo, los tiempos son prohibitivos, ya que a la velocidad cercanas a la


indirectas que las conectan entre sí.

El viaje dentro de un agujero de gusano se asemeja a las antiguas carreteras del siglo XXI. Las naves
pueden viajar muy rápido mientras se encuentren dentro del agujero, no pueden salir de éste mientras
están viajando y pueden encontrarse con otras naves que están realizando el mismo trayecto. Todos los
agujeros de gusano son bidireccionales.

Una vez la nave llega a una estrella puede visitar cualquiera de los planetas habitados que posee. Los
sistemas de propulsión en el año 31337 permiten a una nave moverse rápidamente dentro de un mismo
sistema solar. Por ese motivo, el tiempo de viajar de un planeta a otro de una misma estrella se asume
que es cero.

## 3 Navegación

Para viajar, el sistema debe indicar al jugador las 10 estrellas más cercanas. Luego de escoger la
estrella, la nave es transportada hacia la misma. El sistema debe registrar el tiempo que tome el viaje,
para efectos del cálculo del tiempo total que el jugador tiene para ganar dinero.

## 4 Naves espaciales

Todas las naves espaciales tienen la siguiente información:

## Nombre

Cada nombre es único en el juego

## Carga

Las naves tienen una bodega para transportar productos, con una capacidad limitada indicada en m^3.
Cada unidad de producto ocupa un volumen específico, el cual es constante en todo el universo. Por
ejemplo, una unidad de condimentos exóticos ocupa 0.25 m^3, mientras que una unidad de robot
sirviente ocupa 1 m^3. Por simplicidad, el peso de cada producto no se tiene en cuenta para calcular la
capacidad de carga.

## Velocidad

Cada nave tiene una velocidad máxima que puede alcanzar, medida en km/s.

## 5 Roles

Este juego debe soportar múltiples jugadores, ya sea viajando en naves distintas o como tripulación en
la misma nave. Para ello, cada jugador debe autenticarse con usuario y contraseña antes de jugar. Los
jugadores tendrán asignado uno de los siguientes roles:

- Piloto: Puede mover a la nave a través del espacio, pero no puede comerciar
- Comerciante: Puede comprar y vender productos en un planeta, pero no puede pilotar la nave
- Capitán: Puede realizar todas las acciones del piloto y comerciante

de la luz, puede tomar varios años ir de una estrella a otra.


Cabe señalar que el sistema debe llevar rastro de la posición de todas las naves que estén jugando.
Cuando un equipo llega a una estrella, el sistema debe mostrar todas las demás naves que se encuentren
en dicho lugar.
