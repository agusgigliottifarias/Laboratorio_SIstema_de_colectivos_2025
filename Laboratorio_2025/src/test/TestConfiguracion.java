package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import Trabajo_final.datos.Configuracion;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase de pruebas unitarias para la clase Configuracion.
 * Verifica la correcta carga del archivo de configuración y manejo de errores.
 */
public class TestConfiguracion {

    /**
     * Testea que la cantidad de pasajeros se cargue correctamente desde un archivo válido.
     * 
     * @throws IOException Si ocurre un error al crear o leer el archivo de prueba.
     */
    @Test
    public void testCantidadPasajerosDesdeArchivo() throws IOException {
        // 1. Crear archivo temporal de prueba
        String rutaArchivo = "config_test.txt";
        FileWriter fw = new FileWriter(rutaArchivo);
        fw.write("cantidadPasajeros=1234\n");
        fw.close();

        // 2. Cargar configuración
        Configuracion config = new Configuracion(rutaArchivo);

        // 3. Validar el resultado
        assertEquals(1234, config.getCantidadPasajeros(), "La cantidad de pasajeros debería ser 1234");

        // 4. (Opcional) Borrar el archivo de prueba
        new java.io.File(rutaArchivo).delete();
    }
    
    /**
     * Testea que se lance una excepción al intentar cargar un archivo sin la propiedad
     * "cantidadPasajeros" o con un valor inválido.
     */
    @Test
    public void testArchivoSinCantidadPasajeros() {
        String rutaArchivo = "config_mal.txt";
        try (FileWriter fw = new FileWriter(rutaArchivo)) {
            fw.write("otraCosa=42\n"); // Archivo sin cantidadPasajeros
        } catch (IOException e) {
            fail("No se pudo crear archivo de prueba");
        }

        // Se espera NumberFormatException por falta de la clave requerida
        assertThrows(NumberFormatException.class, () -> {
            new Configuracion(rutaArchivo);
        });

        new java.io.File(rutaArchivo).delete();
    }

}
