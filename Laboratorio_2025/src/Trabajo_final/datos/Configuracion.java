package Trabajo_final.datos;

import java.io.*;
import java.util.*;

/**
 * Clase que representa la configuración inicial del sistema,
 * cargando los parámetros desde un archivo de propiedades.
 * 
 * Actualmente, solo se carga la cantidad total de pasajeros a simular.
 * 
 */
public class Configuracion {
    private int cantidadPasajeros;

    /**
     * Constructor que carga los datos de configuración desde un archivo `.properties`.
     * 
     * @param archivo Ruta del archivo de configuración.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public Configuracion(String archivo) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(archivo));
        this.cantidadPasajeros = Integer.parseInt(prop.getProperty("cantidadPasajeros"));
    }

    /**
     * Devuelve la cantidad total de pasajeros a simular.
     * 
     * @return cantidad de pasajeros.
     */
    public int getCantidadPasajeros() { 
        return cantidadPasajeros;
    }
}
