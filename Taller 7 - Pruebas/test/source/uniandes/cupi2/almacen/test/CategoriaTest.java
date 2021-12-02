package uniandes.cupi2.almacen.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import uniandes.cupi2.almacen.mundo.AlmacenException;
import uniandes.cupi2.almacen.mundo.Categoria;
import uniandes.cupi2.almacen.mundo.Marca;
import uniandes.cupi2.almacen.mundo.NodoAlmacen;
import uniandes.cupi2.almacen.mundo.Producto;

public class CategoriaTest {

	private Categoria categoria;

	@Nested
	// Test que prueba el funcionamiento UNITARIO de cada método cuando
	// la categoría ES CREADA SIN HIJOS
	class CategoriaSinHijosTest {

		@BeforeEach
		public void setUpEscenarioVacio() {

			String pIdentificador = "C-1";
			String pNombre = "Electrodomesticos";
			// Se usa el constructor que no crea hijos.
			categoria = new Categoria(pIdentificador, pNombre);
		}

		@Test // Se espera que NO SE LANCEN EXCEPCIONES ya que un nodo válido debe poderse
				// agregar sin problema
		public void testAgregarNodo() {
			String pIdPadre = null;
			String pTipo = "Categoría";
			String pIdentificador = "P-1";
			String pNombre = "Licuadora";
			assertDoesNotThrow(() -> categoria.agregarNodo(pIdPadre, pTipo, pIdentificador, pNombre));
		}

		@Test // Se espera que el resultado sea NULL puesto que no se encontraron nodos
				// (Recordar que la categoría fue creada sin hijos)
		public void testBuscarNodo() {
			String pIdentificadorInexistente = "777";
			assertEquals(null, categoria.buscarNodo(pIdentificadorInexistente));
		}

		@Test // Se espera que el resultado sea 0 puesto que LA CATEGORÍA ESTÁ VACÍA
		public void testDarValorVentas() {
			assertEquals(0, categoria.darValorVentas());
		}

		@Test // Se espera que el resultado sea nulo puesto que el nodo indicado no existe
		public void testEliminarNodo() {
			String pIdentificadorInexistente = "777";
			assertEquals(null, categoria.eliminarNodo(pIdentificadorInexistente));
		}

		@Test // Se espera que no se encuentre el producto puesto que la categoría está vacía
		public void testBuscarProducto() {
			String pProductoNoDisponible = "P-505";
			assertEquals(null, categoria.buscarProducto(pProductoNoDisponible));
		}

		@Test // Se espera que no se encuentre el padre puesto que la categoría está vacía
		public void testBuscarPadre() {
			String pPadreAusente = "C-2";
			assertEquals(null, categoria.buscarPadre(pPadreAusente));
		}

		@Test // El tamaño de la lista debe ser 0 puesto que no hay productos en ella
		public void testDarProductos() {
			List<Producto> listaProductos = categoria.darProductos();
			assertEquals(0, listaProductos.size());
		}

		@Test // El tamaño de la lista de marcas debe ser 0 puesto que no hay productos en
				// ella
		public void testDarMarcas() {
			List<Marca> listaMarcas = categoria.darMarcas();
			assertEquals(0, listaMarcas.size());
		}

		@Test // El tamaño de la lista debe ser 1 puesto que solo hay un nodo (la misma
				// categoría)
		public void testDarPreorden() {
			List<NodoAlmacen> listaNodos = categoria.darPreorden();
			assertEquals(1, listaNodos.size());
		}

		@Test // El tamaño de la lista debe ser 1 puesto que solo hay un nodo (la misma
				// categoría)
		public void testDarPosorden() {
			List<NodoAlmacen> listaNodos = categoria.darPosorden();
			assertEquals(1, listaNodos.size());
		}
	}
	
	
	@Nested // Test que prueba el funcionamiento UNITARIO de cada método cuando
	// la categoría ES CREADA CON HIJOS
	class CategoriaConHijosTest{
		
		@BeforeEach //Se crea una nueva categoria CON HIJOS basada en un archivo de prueba
		public void setUpEscenarioConHijos() throws AlmacenException, IOException {
			
			File archivoCatTest = new File("./test/testData/categoriaTest.txt");
			
			// Se usa el constructor que SÍ crea hijos.
			 BufferedReader in = new BufferedReader( new FileReader( archivoCatTest ) );			
			 categoria = new Categoria( in.readLine( ), in );
		}
		
		
		@Test // No se esperan excepciones, indicando que el nodo se agregó exitosamente a la categoria televisores
		//En adición, se espera que el tamaño de los nodos haya aumentado en 1!
		public void testAgregarNodo() {
			String pIdPadre = "111";
			String pTipo = "Categoría";
			String pIdentificador = "P-1";
			String pNombre = "Licuadora";
			
			int nodosAntes = categoria.darNodos().size(); 
			
			assertDoesNotThrow(() -> categoria.agregarNodo(pIdPadre, pTipo, pIdentificador, pNombre));
			
			int nodosDespues = categoria.darNodos().size(); 
			
			assertEquals(1, nodosDespues-nodosAntes);
			
		}
		
	@Test //Se debe retornar un objeto de tipo nodo (siendo lá única alternativa a nulo), puesto que se está buscando un objeto existente
	public void testBuscarNodo() {
		
		String pIdExistente = "111";
		assertNotNull(categoria.buscarNodo(pIdExistente));
	}
	
	@Test //Debería darse un valor esperado en dado caso que todos los cálculos hayan sido realizados correctamente
	public void testDarValorVentas() {
		
		int valorEsperado = 1898900 + 2*(2499000); //Valor de venta de 1 ED 55" UHD Smart TV + 2 LED Curvo 49" Smart TV 4K Ultra HD
		assertEquals(valorEsperado, categoria.darValorVentas());		
	}
	
	@Test //Debería retornarse un objeto no nulo, puesto que se ha eliminado un nodo existente
	public void testEliminarNodo() {
		String pIdExistente = "1111";

		assertNotNull(categoria.eliminarNodo(pIdExistente));
	}
	
	@Test //Se debería encontrar un objeto no nulo (TV en este caso), pues el objeto fue cargado anteriormente
	public void testBuscarProducto() {

		String pIdProductoExist = "31759941"; //Id de LED 49" Smart TV Full HD
		assertNotNull(categoria.buscarProducto(pIdProductoExist));
		
	}
	
	@Test // Se debería encontrar un objeto no nulo, pues la marca ingresada tiene un padre (la categoría)
	public void testBuscarPadre() {
		String pIdPadreExist = "1112"; //Marca LG
		assertNotNull(categoria.buscarPadre(pIdPadreExist));
	}
	
	@Test //Se debería retornar el número de productos cargados en el archivo de prueba (5)
	public void testDarProductos() {
		int numProductosCargados = 5;
		assertEquals(numProductosCargados, categoria.darProductos().size());
	}
	
	@Test //Se debería retornar el número de marcas cargadas en el archivo de prueba (2)
	public void testDarMarcas() {
		int numMarcas = 2;
		assertEquals(numMarcas, categoria.darMarcas().size());
	}
	
	@Test //Se debería retornar una lista ordenada por preorden de los objetos cargados
	public void testDarPreorden() {
		
		List<String> listaEsperada = new ArrayList<String>();
		listaEsperada.add("Televisores");
		listaEsperada.add("SAMSUNG");
		listaEsperada.add("LG");
		
		List<NodoAlmacen> listaReal = categoria.darPreorden();
		
		int count = 0;
		for(NodoAlmacen nodo : listaReal) {
			String nombre_nodo = nodo.darNombre();
			assertEquals(listaEsperada.get(count), nombre_nodo);
			count+=1;
		}
	}
	
	@Test //Se debería retornar una lista ordenada por posorden de los objetos cargados
	public void testDarPosorden() {

		List<String> listaEsperada = new ArrayList<String>();
		listaEsperada.add("SAMSUNG");
		listaEsperada.add("LG");
		listaEsperada.add("Televisores");

		
		List<NodoAlmacen> listaReal = categoria.darPosorden();
		
		int count = 0;
		for(NodoAlmacen nodo : listaReal) {
			String nombre_nodo = nodo.darNombre();
			assertEquals(listaEsperada.get(count), nombre_nodo);
			count+=1;
		}
		
		
	}
		
		
		
		
	}
	
	@Nested
	// Test que prueba el funcionamiento UNITARIO de cada método cuando
	// la categoría ES CREADA CON HIJOS pero TODOS LOS METODOS SON CASOS FALLIDOS
	class CategoriaFallidaTest {

		@BeforeEach //Se crea una nueva categoria CON HIJOS basada en un archivo de prueba
		public void setUpEscenarioConHijos() throws AlmacenException, IOException {
			
			File archivoCatTest = new File("./test/testData/categoriaTest.txt");
			
			// Se usa el constructor que SÍ crea hijos.
			 BufferedReader in = new BufferedReader( new FileReader( archivoCatTest ) );			
			 categoria = new Categoria( in.readLine( ), in );
		}
		
		
		@Test // Debido a que el nodo que se quiere agregar ya existe se tirara una excepcion
		// Esto provocara que este test de un caso fallido
		public void testAgregarNodo() {
			String pIdPadre = "111";
			String pTipo = "Categoría";
			String pIdentificador = "1112";
			String pNombre = "Fallida";
			
			assertDoesNotThrow(() -> categoria.agregarNodo(pIdPadre, pTipo, pIdentificador, pNombre));
			
		}
			
		@Test //Se retornara nulo debido a que se esta buscando un elemento no existente
		//Esto provocara que el test de un caso fallido
		public void testBuscarNodo() {
			
			String pIdExistente = "INEXISTENTE";
			assertNotNull(categoria.buscarNodo(pIdExistente));
		}
		
		@Test //El valor esperado es un valor aleatorio que no corresponde al real
		//Esto provocara que el test de un caso fallido
		public void testDarValorVentas() {
			
			int valorEsperado = 123123; //Valor arbitrario
			assertEquals(valorEsperado, categoria.darValorVentas());		
		}
		
		@Test //Se retornara null debido a que se quiere eliminar un nodo inexistente
		//Esto provocara que el test de un caso fallido
		public void testEliminarNodo() {
			String pIdExistente = "INEXISTENTE";
	
			assertNotNull(categoria.eliminarNodo(pIdExistente));
		}
		
		@Test //Se retornara un producto null ya que el id ingresado no existe o no ha sido cargado
		//Esto provocara que el test de un caso fallido
		public void testBuscarProducto() {
	
			String pIdProductoExist = "INEXISTENTE"; 
			assertNotNull(categoria.buscarProducto(pIdProductoExist));
			
		}
		
		@Test // Se retornara null ya que el ID ingresado es la raiz y por lo tanto su padre es nulo
		//Esto provocara que el test de un caso fallido
		public void testBuscarPadre() {
			String pIdPadreExist = "111"; //Nodo raiz (Televisores)
			assertNotNull(categoria.buscarPadre(pIdPadreExist));
		}
		
		@Test //El numero de productos cargado desde el archivo de pruebas no concordara con el numero con el que se compara
		//Esto provocara que el test de un caso fallido
		public void testDarProductos() {
			int numProductosCargados = 555;
			assertEquals(numProductosCargados, categoria.darProductos().size());
		}
		
		@Test //El numero de marcas cargadas desde el archivo de pruebas no concordara con el numero con el que se compara
		//Esto provocara que el test de un caso fallido
		public void testDarMarcas() {
			int numMarcas = 555;
			assertEquals(numMarcas, categoria.darMarcas().size());
		}
		
		@Test //La lista esperada con la cual se compara no concuerda con la lista ordenada en Preorden
		//Esto provocara que el test de un caso fallido
		public void testDarPreorden() {
			
			List<String> listaEsperada = new ArrayList<String>();
			listaEsperada.add("SAMSUNG");
			listaEsperada.add("LG");
			listaEsperada.add("Televisores");
			
			List<NodoAlmacen> listaReal = categoria.darPreorden();
			
			int count = 0;
			for(NodoAlmacen nodo : listaReal) {
				String nombre_nodo = nodo.darNombre();
				assertEquals(listaEsperada.get(count), nombre_nodo);
				count+=1;
			}
		}
		
		@Test //La lista esperada con la cual se compara no concuerda con la lista ordenada en Posorden
		//Esto provocara que el test de un caso fallido
		public void testDarPosorden() {
	
			List<String> listaEsperada = new ArrayList<String>();
			listaEsperada.add("Televisores");
			listaEsperada.add("LG");
			listaEsperada.add("SAMSUNG");
	
			
			List<NodoAlmacen> listaReal = categoria.darPosorden();
			
			int count = 0;
			for(NodoAlmacen nodo : listaReal) {
				String nombre_nodo = nodo.darNombre();
				assertEquals(listaEsperada.get(count), nombre_nodo);
				count+=1;
			}
			
			
		}
	}
	
	@Nested //Test adicionales que se aseguran que la todo el proceso sea exitoso
	class AdicionalTest{
		
		@Test
		public void cargarCategoriaTest() {
			File archivoCatTest = new File("./test/testData/categoriaTest.txt");
			assertDoesNotThrow( () ->new BufferedReader( new FileReader( archivoCatTest ) ));			
		}
	}
	
}


