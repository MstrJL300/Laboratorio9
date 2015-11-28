Laboratorio Git y Angular
=========================

La primera parte introduce la utilización del controlador de versiones
GIT.

La segunda del ejercicio consiste en crear un servicio web
REST que es el servidor para una aplicación de un blog.

En la tercera parte se trabaja en la creación de una interfaz angular
que utiliza el servicio creado en la parte II.

La tercera parte consiste en completar los servicios y la interfaz
propuesta.

Parte I
=======

Repositorio remoto en Github
----------------------------

Github es un servicio de hosting para repositorios git.  Si no tiene
cuenta en [github](https://github.com/) cree una nueva cuenta.

* Cree un nuevo repositorio a traves de la interface web de Github, en
  la pestaña de repositorios seleccione la opción de crear un nuevo
  repositorio.

* Coloque un nombre y seleccione el tipo de repositorio publico (el
  tipo de repositorio privado es pago en Github).

Adicione un compañero de trabajo
--------------------------------

Entre en el sitio web de Github a `Settings` y añada a un compañero como colaborador (`Collaborator`).

Creación del repositorio GIT
----------------------------

* Abra un terminal en linux.
* Cree el directorio <dir> donde va a realizar el taller
````bash
$ mkdir <dir>
````
* Ubiquese en la carpeta que acaba de crear
````bash
$ cd <dir>
````
* Inicialice el repositorio GIT con el siguiente comando:
````bash
$ git init
````

Una vez creado el repositorio utilice el comando:
````bash
$ git remote add origin <url-repositorio>
````

Guardar cambios en el repositorio remoto
----------------------------------------

Utilice el comando: ````bash $ git push -u origin master ```` Para
enviar al repositorio remoto el repositorio local que acaba de crear.

Para actualizar el repositorio local con los cambios del repositorio
remoto, utilice el comando:

````bash
$ git pull
````

Para enviar los cambios realizados en el repositorio local al
repositorio remoto utilice el comando:

````bash
$ git push
````

Para el resto del ejercicio trabaje con su compañero el cual debe
obtener una vesión del repositorio creado utilizando el comando:

````bash
$ git clone <url-repositorio>
````

Donde `<url-repositorio>` corresponde a la dirección url del
repositorio que creo en el sitio de Github.

En diversos puntos utilice los comandos vistos, pueden crear
diferentes archivos y modificarlos.

Si por inconsistencias no se puede realizar el comando `push` puede utilizar 

````bash
git pull --rebase <url-repositorio> master
````

Si aparecen conflictos en algunos archivos, estos tienen el formato

````diff
<<<<<<< HEAD (commit más reciente del repositorio)

Cambio obtenido del respositorio externo 

Mediante el pull --rebase

=======

Cambio del repositorio local que se intentó 

combinar con el cambio del repositorio externo.

>>>>>>> comentario del último commit
````

En cuyo caso el autor tiene que modificar el código entre `<<<<` y
`>>>>` haciendo el merge de las versiones para posteriormente volver a
enviarla al repositorio.

Estructura de directorios inicial
---------------------------------

El comando `mkdir` se utiliza para crear directorios.

* Cree la estructura inicial de directorios:
````bash
$ mkdir -p src/main/resources/static
$ mkdir -p src/main/java/edu/eci/arsw/model
$ mkdir -p src/main/java/edu/eci/arsw/controler
````

La opción `-p` se utiliza para crear recursivamente los directorios
inexistentes.

Creación del archivo POM
------------------------

Edite el archivo `pom.xml`
````xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>edu.eci.arsw</groupId>
    <artifactId>angular-spring</artifactId>
    <version>0.1.0</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.2.7.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <properties>
        <java.version>1.8</java.version>
    </properties>


    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-releases</id>
            <url>https://repo.spring.io/libs-release</url>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>spring-releases</id>
            <url>https://repo.spring.io/libs-release</url>
        </pluginRepository>
    </pluginRepositories>
</project>
````

Estado del repositorio
----------------------

Para ver el estado del repositorio ejecute el siguiente comando en la
terminal:
````bash
$ git status
````

Note que no se incluyen los directorios que se encuentran vacios.

Adición de archivos al indice del repositorio
---------------------------------------------

Para adicionar los archivos creados o las modificaciones al repositorio
utilice el comando:
````bash
$ git add pom.xml
````

Alternativamente se utiliza el comando:
````bash
$ git add .
````
para incluir las modificaciones realizadas en el directorio actual.

Revise el estado del repositorio con el respectivo comando.

Guardar los cambios al repositorio
----------------------------------

Utilice el comando: ````bash $ git commit -m "<mensaje>" ```` donde
`<mensaje>` es un mensage del usuario que describe los cambios
realizados.

El comando commit utiliza la opción `-a` para adicionar los cambios
realizados a los archivos que ya se encontraban en el índice del
repositorio.


A lo largo del ejercicio utilice los comandos `status`, `add` y
`commit` para manejar el repositorio localmentea medida que va creando
nuevos archivos y modificaciones en el código  y los comandos `push`
y `pull` para guardar cambios en el repositorio remoto.

Creación del servicio REST
--------------------------

Se va a utilizar la clase java (POJO)
`src/main/java/edu/eci/arsw/model/Entry.java` para la creación de las
entradas a un blog:

````java
package edu.eci.arsw.model;

public class Entry {

    private String title;
    private String content;

    public Entry() {
    }

    public Entry(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
            return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
            this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
````

Creación del controlador
------------------------

Cree el controlador del servicio REST en `src/main/java/edu/eci/arsw/controller/EntryController.java`

````java
package edu.eci.arsw.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import edu.eci.arsw.model.*;

@RestController
public class EntryController {

    private final AtomicLong counter = new AtomicLong();
    private List<Entry> entries = new ArrayList<>();
    {
        entries.add(new Entry("Title0","Content0"));
        entries.add(new Entry("Title1","Content1"));
    }

    @RequestMapping(method = RequestMethod.GET,value = "/blogs")
    public List<Entry> getEntries() {
        return entries;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/blog")
    public  ResponseEntity<?>  postEntry(@RequestBody Entry p) {
        entries.add(p);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
````

Creando la Aplicación
---------------------

Para poder ejecutar la aplicación es necesario crear una clase con el
método main, en este caso
`src/main/java/edu/eci/arsw/Application.java`.

````java
package edu.eci.arsw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
````

Ejecutando la aplicación
------------------------

Para ejecutar la aplicación utilice los siguientes comandos:
````bash
$ mvn package
$ mvn spring-boot:run
````

Recuerde siempre volver a realizar el comando `package` antes de
ejecutar la applicación para que los cambios en el código se vean
reflejados en la aplicación.

Listando las entradas
---------------------

Abra un navegador en la dirección [http://localhost:8080/blogs](http://localhost:8080/blogs) y compruebe que el servicio esta funcionando.

Creando una nueva entrada
-------------------------

Pruebe crear una nueva entrada utilizando el comando curl:

````bash
curl -H "Content-Type: application/json" -X POST -d '{ "title" : "titulo", "content" : "contenido" }' http://localhost:8080/blog
````

Actualice el navegador [http://localhost:8080/blogs](http://localhost:8080/blogs) y compruebe que la lista se ha actualizado.

Parte II
=========

Ejemplo Angular
---------------

Descargue la librería bootstrap del sitio [http://getbootstrap.com/getting-started/#download](http://getbootstrap.com/getting-started/#download) y descompacte los archivos y copie los directorios:

* `bootstrap-3.3.5/dist/js` al directorio `src/main/resources/static/js`.
* `bootstrap-3.3.5/dist/css` al directorio `src/main/resources/static/css`.
* `bootstrap-3.3.5/dist/fonts` al directorio `src/main/resources/static/fonts`.

Descargue la librería de angular del sitio [https://angularjs.org/](https://angularjs.org/) con los extras `angular-route.min.js` y `angular-resource.min.js` y guardelos en el directorio `src/main/resources/static/js`.

En el directorio `src/main/resources/static` cree el archivo
`index.html`

````html
<!DOCTYPE html>
<html ng-app="app">
    <head >
        <script src="js/angular.min.js"></script>
        <script src="js/app/controller/blogctrl.js"></script>

        <link rel="stylesheet" href="css/bootstrap.css">
        <title>Blog</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body ng-controller="blogCtrl">
        <div>
            <h1>{{title}}</h1><br>
            <ol>
                Titulo:<input ng-model="title">
            </ol>
        </div>
    </body>
</html>
````

y el módulo javascript `js/app/controllers/blogctrl.js`

````javascript
var app = angular.module("app",[]);

app.controller("blogCtrl", function($scope) {
    $scope.title = "Title";
});
````

La primera líne corresponde a la creación del modulo 'app' y
posteriormente se crea la funcion de construcción del controlador
utilizando el método `.controller`. Se utiliza injección de
anotaciones `{{title}}` para especificar la dependencia del
controlador con el `$scope` provisto por angular. Note el
comportamiento cuando se escribe en la entrada de texto.

Una vez que se ha visto la relación entre el controlador y el modelo
hacemos un formulario en el archivo index.html.

````html
<!DOCTYPE html>
<html ng-app="app">
    <head >
        <script src="js/angular.min.js"></script>
        <script src="js/app/controller/blogctrl.js"></script>

        <link rel="stylesheet" href="css/bootstrap.css">
        <title>Blog</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body ng-controller="blogCtrl">
            <div>
          <form>
            <fieldset>
                <legend>Entry</legend>
                <label for='title'>Title:</label><input id='title' name='title' type="text" ng-model= "entry.title">
                <label for='content'>Content:</label><input id='content' name='content' type="text" ng-model= "entry.content">
          </form>
        </div>
    </body>
</html>
````

y modifique el controlador de manera que se tenga una elemento `entry`

````javascript
var app = angular.module("app",[]);

app.controller("blogCtrl", function($scope) {
    $scope.entry = {title : "Title",
                    content : "Content"};
});
````

Servicios
---------

Un servicio es un objeto javascript que permite obtener información.
Los servicios interactuan únicamente con otros servicios o con un
servidor.

Por ejemplo, el servicio `$log` (definido en angular) permite crear un
log de la aplicación.  Para inyectar servicios se colocan en la función de inicialización del controlador:

````javascript
var app = angular.module("app",[]);

app.controller("blogCtrl", function($scope,$log) {
    $scope.entry = {title : "Title",
                    content : "Content"};
    $log.debug('se creo el $scope');
});
````

Revise en la consola que al recargar la página efectivamente aparezca
el mensage del log.

El servicio  `$http`
--------------------

El servicio `$http permite realizar peticiones `ajax` al servidor, en
este caso se hara una petición al servicio REST que nos entrega la
lista de entradas en el blog que se realizo en spring. Note que se tiene una función en caso de éxito y otra en caso de error.

````javascript
var app = angular.module("app",[]);

app.controller("blogCtrl", function($scope,$log,$http) {
    $scope.entry = {title : "Title",
                    content : "Content"};
    $scope.entries = [];
    $log.debug('se creo el $scope');

    $scope.loadData = function() {
        var configList = {
                method: "GET",
                url: "blogs"
                    };

        var response=$http(configList);

        response.success(function(data, status, headers, config) {
            $scope.entries = data;
            });

        response.error(function(data, status, headers, config) {
            alert("Ha fallado la petición. Estado HTTP:"+status);
            });
    };
    $scope.loadData();
});
````

El código html para visualizar las entradas es el siguiente:

````html
<!DOCTYPE html>
<html ng-app="app">
    <head >
        <script src="js/angular.min.js"></script>
        <script src="js/app/controller/blogctrl.js"></script>

        <link rel="stylesheet" href="css/bootstrap.css">
        <title>Blog</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body ng-controller="blogCtrl">
            <div>
          <form>
            <fieldset>
                <legend>Entry</legend>
                <label for='title'>Title:</label><input id='title' name='title' type="text" ng-model= "entry.title">
                <label for='content'>Content:</label><input id='content' name='content' type="text" ng-model= "entry.content">
          </form>
        </div>
        <table class="table">
          <tr>
            <th>Título </th>
            <th>Contenido</th>
            <th>Editar </th>
          </tr>

          <tr ng-repeat="e in entries">
            <td> {{ e.title }} </td>
            <td> {{ e.content }} </td>
            <td>
              <button type="button" class="btn btn-warning"  ng-click="edit($index)">
                Editar
              </button>
            </td>
          </tr>
        </table>
    </body>
</html>
````

Alternativamente el código en la función loadData puede ser abreviado
asi:

````javascript
    $scope.loadData = function() {
        $http({
            method: "GET",
            url: "blogs"
        }).success(function(data) {
            $scope.entries = data;
        }).error(function(data,status,headers,config) {
            alert("Ha fallado la petición. Estado HTTP:"+status);
        });
    };
````

Crear una nueva entrada
-----------------------

Para crear una nueva entrada se adiciona un boton de submit a la forma
y el tag `ng-submit` (por simplicidad se incluye solo el código de la
forma):

````html
          <form ng-submit="processForm()">
            <fieldset>
                <legend>Entry</legend>
                <label for='title'>Title:</label><input id='title' name='title' type="text" ng-model= "entry.title">
                <label for='content'>Content:</label><input id='content' name='content' type="text" ng-model= "entry.content">
                <button type="submit" class="btn btn-success">
                  <span class="glyphicon glyphicon-plus">Crear
                </button>
          </form>
````

y se incluye el código en javascript para que el controlador procese
la forma:

````javascript
    $scope.processForm = function() {
        $log.debug($scope.entry);
        $http({
            method  : 'POST',
            url     : 'blog',
            data    : $scope.entry
        }).success(function(data) {
            console.log(data);
            $scope.loadData();
        });
    };
````

Parte III
=========

Cree los servicios REST de actualización y eliminación de una entrada
y programelo de tal manera que lo pueda llamar desde la interfaz
angular. 
