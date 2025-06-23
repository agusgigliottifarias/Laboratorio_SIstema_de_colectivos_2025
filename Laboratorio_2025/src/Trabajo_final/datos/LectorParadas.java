package Trabajo_final.datos;

import java.io.*;
import java.util.*;
import Trabajo_final.modelo.Parada;

/**
 * Clase utilitaria para cargar las paradas desde un archivo de texto plano.
 * Cada línea representa una parada con su ID y dirección.
 * 
 */
public class LectorParadas {

    /**
     * Carga las paradas desde un archivo de texto.
     * Cada línea del archivo debe tener el formato:
     * <pre>
     * ID_PARADA;DIRECCION
     * </pre>
     * 
     * @param archivo Ruta del archivo a leer.
     * @return Mapa de paradas (clave = ID, valor = objeto {@link Parada}).
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static Map<Integer, Parada> cargarParadas(String archivo) throws IOException {
        Map<Integer, Parada> mapa = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("#") || linea.isBlank()) continue;
                String[] partes = linea.split(";");
                int id = Integer.parseInt(partes[0]);
                String direccion = partes[1];
                mapa.put(id, new Parada(id, direccion));
            }
        }
        return mapa;
    }
}
