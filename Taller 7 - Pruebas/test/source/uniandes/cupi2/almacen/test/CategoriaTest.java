package uniandes.cupi2.almacen.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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


