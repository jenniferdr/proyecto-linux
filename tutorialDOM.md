#Resumen de como usar lo que nos importa de DOM
***

##El paquete javax.xml.parsers
Este paquete es el punto de partida para usar DOM en Java. 
Contiene dos clases fundamentales: DocumentBuilderFactory y DocumentBuilder.

Es decir, hay que importar:

    javax.xml.parsers.DocumentBuilderFactory
    java.xml.parsers.DocumentBuilder

La clase DocumentBuilderFactory posee un método estático que permite obtener una implementación de la clase DocumentBuilderFactory.

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

El método newInstance() localizará la clase que implementa DocumentBuilderFactory

Esta especie de fabrica tiene propiedades que se pueden setear ejemplo factory.setXX() antes de crear el
DocumentBuilder, pero creo que ahora no nos interesa, despues podemos investigar.

Para crear el DocumentBuilder se hace:

    DocumentBuilder docBuild= factory.newDocumentBuilder();

Este 'constructor' es el que nos ayudara a crear o cargar el archivo convirtiendolo a DOM, si quieren crear 
un archivo vacio consulten la referencia, para cargar un archivo que es lo que nos interesa:

    Document doc = docBuild.parse(new File("archivo.xml"));

¿Que es el metodo parse?
El método parse() es un método sobrecargado y acepta un java.io.File,
un String conteniendo una URI, un java.io.InputStream o un
org.xml.sax.InputSource.En nuestro caso estamos usando el java.io.File 

(Se puede tener un manejador de errores, investigar)

## Recorrer el documento como un  arbolito bello y hermoso
 
Ahora se puede recorrer el arbol creado por el parser a traves de sus nodos. 
Un documento sólo puede tener un elemento hijo que es el elemento raíz del documento.
Un documento también puede tener asociado o no un objeto DocumentType
y varios hijos ProcessingInstruction.
De esta forma se obtiene el elemento raíz del documento:

    Element root = doc.getDocumentElement();
    //DocumentType de un documento:
    DocumentType docType = doc.getDoctype();

El objeto Document permite obtener listados de elementos por tagName y/o por espacio de nombres y también 
podemos buscar un elemento por su identiﬁcador.

    NodeList nList = doc.getElementsByTagName("proceso");
    
Los listados de elementos se devuelven en forma de NodeList.
NodeList es una interfaz que sólo deﬁne dos métodos: getLength() que devuelve la longitud de la lista
e item(int index) que devuelve un Node de la lista por su índice.

Un documento es también un nodo: la interfaz Document extiende de la interfaz Node, es decir que Node tiene 
mas atributos y metodos pero que rayos tiene Node??

##La interfaz Node

Todos los objetos de un árbol DOM son nodos. Todos los nodos tienen un campo ownerDocument que referencia
al documento que lo contiene, a excepción de los documentos, cuyo ownerDocument es null. La mayoría de
los nodos tienen un nodo padre, y todos los nodos tienen un hermano anterior y un hermano siguiente 
(previousSibling y nextSibling). Para obtener estos objetos llamamos a los métodos getOwnerDocument(), 
getParentNode(), getPreviousSibling() y getNextSibling() respectivamente. Estos métodos permiten recorrer
todo el árbol de nodos.
Todos los nodos tienen un nombre y un valor. Para obtener estas propiedades usamos getNodeName() y getNodeValue() respectivamente. 



Referencias:
http://www.uhu.es/josel_alvarez/xNvasTecnProg/material/dom.pdf