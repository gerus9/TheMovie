# TheMovie
Rappi excercise

## Capas

| Capa | Descripción |
| ------ | ------ |
| adapters | Contiene todos los adapters de los diferentes RecyclerView |
| custom | Contiene las vistas personalizadas
| db | Es la capa de base de datos se implementa ORMLite, pero la capa permite la modificación del guardado con cualquier otro framework |
| interfaces | Contiene todas las interfaces que comunican las diferentes capas
| models | Son los modelos que representan la información
| network | Es la capa de comunicación que utilizo para manejar las comunicaciones a través de interfaces, es una capa sin framework
| utils | Contiene archivos que pueden ser utiles en cualquier clase
| view | Contiene todas las vistas dividas en paquetes como son los activities, fragments, dialogs


## Responsabilidad de clases 

(Una disculpa, por la mala calidad de la imagen)

![ScreenShot](http://i243.photobucket.com/albums/ff237/gerus_09/Captura%20de%20pantalla%202017-03-25%20a%20las%2019.04.23%20p.m..png)

## Responsabilidad única

Las responsabilidad única consiste en que cada elemento debe tener una funcionalidad basica, como por ejemplo en el caso de los Manager (Database and
Network) ambos tienen la responsabilidad de comunicar la información con la vista, para evitar que se mezclen las capas como de comunicación
como de datos y así crear estructuras escalables, donde si es necesario cambiar la capa de comunicación solo se modifican algunos archivos
pero no le pega a las vistas.

En el código de la aplicación podemos ver como se pudo reutilizar muchos objetos Movies y TV, ya que ambos heredan de Detail, por lo que fue 
más fácil manipular las vistas, a través de una clases genericas que heredaban de un mismo objeto padre.

## Código limpio

Consiste en que el código sea legible para cualquier personas que va a leer el código fuente, son pequeñas reglas que se estructuran dependiendo
de la empresa, que son patrones que todos los programadores deben de seguir para un mejor mantenimiento de la aplicación. 

En la aplicación se podrán dar cuenta que las varibles globales empiezan con M, los parámetros inician con la clase P, los métodos tiene el 
prefijo 'prc' para identificar los métodos de la clase con los métodos propios. También se puede ver el uso de los archivos como strings, integer,
dimens, color, para poder identificar los recursos facilmente.

## Implementacion

* Vectores 
* BuildTypes
* Variants Outputs
* Internacionalización de textos (EU/ES)
* ButterKnife
* ORMLite
* Glide
* Gson
* Recursos para las diferentes versiones de android
* CustomViews
* Animations
* Material Design
* Fragments
* Android support v25+



