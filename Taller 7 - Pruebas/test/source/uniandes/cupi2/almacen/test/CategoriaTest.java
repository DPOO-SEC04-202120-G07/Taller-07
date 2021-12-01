package uniandes.cupi2.almacen.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import uniandes.cupi2.almacen.mundo.Categoria;
import uniandes.cupi2.almacen.mundo.Marca;
import uniandes.cupi2.almacen.mundo.NodoAlmacen;
import uniandes.cupi2.almacen.mundo.Producto;


public class CategoriaTest {

	private Categoria categoria;

	@Nested
	//Test secuencial que prueba el funcionamiento UNITARIO de cada método cuando la categoría ES CREADA SIN HIJOS
	class CategoriaSinHijosTest{
		
		@BeforeEach
		public void setUpEscenarioVacio() {
			
			String pIdentificador = "C-1";
			String pNombre = "Electrodomesticos";
			//Se usa el constructor que no crea hijos.
			categoria = new Categoria(pIdentificador, pNombre);
		}
		
		@Test //Se espera que NO SE LANCEN EXCEPCIONES ya que un nodo válido debe poderse agregar sin problema
		public void testAgregarNodoSinTipo() {
			String pIdPadre = null; 
			String pTipo = "Categoría";
			String pIdentificador = "P-1";
			String pNombre = "Licuadora";
			assertDoesNotThrow(()->categoria.agregarNodo(pIdPadre, pTipo, pIdentificador, pNombre));
		}
		
		@Test //Se espera que el resultado sea NULL puesto que no se encontraron nodos (Recordar que la categoría fue creada sin hijos)
		public void testBuscarNodo() {
			String pIdentificadorInexistente = "777";
			assertEquals(null, categoria.buscarNodo(pIdentificadorInexistente));
		}
		
		@Test //Se espera que el resultado sea 0 puesto que LA CATEGORÍA ESTÁ VACÍA 
		public void testDarValorVentas() {
			assertEquals(0, categoria.darValorVentas());
		}
		
		@Test //Se espera que el resultado sea nulo puesto que el nodo indicado no existe
		public void testEliminarNodo() {
			String pIdentificadorInexistente = "777";
			assertEquals(null, categoria.eliminarNodo(pIdentificadorInexistente));
		}
		
		@Test //Se espera que no se encuentre el producto puesto que la categoría está vacía 
		public void testBuscarProducto() {
			String pProductoNoDisponible = "P-505";
			assertEquals(null, categoria.buscarProducto(pProductoNoDisponible));
		}
		
		@Test //Se espera que no se encuentre el padre puesto que la categoría está vacía
		public void testBuscarPadre() {
			String pPadreAusente = "C-2";
			assertEquals(null, categoria.buscarPadre(pPadreAusente));
		}
		
		@Test //El tamaño de la lista debe ser 0 puesto que no hay productos en ella
		public void testDarProductos() {
			List<Producto> listaProductos = categoria.darProductos();
			assertEquals(0, listaProductos.size());
		}
		
		
		@Test //El tamaño de la lista de marcas debe ser 0 puesto que no hay productos en ella
		public void testDarMarcas() {
			List<Marca> listaMarcas = categoria.darMarcas();
			assertEquals(0, listaMarcas.size());
		}
		
		@Test //El tamaño de la lista debe ser 1 puesto que solo hay un nodo (la misma categoría)
		public void testDarPreorden() {
			List<NodoAlmacen> listaNodos = categoria.darPreorden();
			assertEquals(1, listaNodos.size());
		}
		
		@Test //El tamaño de la lista debe ser 1 puesto que solo hay un nodo (la misma categoría)
		public void testDarPosorden() {
			List<NodoAlmacen> listaNodos = categoria.darPosorden();
			assertEquals(1, listaNodos.size());
			
		}
		
	}
	


	

}
