package Trabajo_final.datos;

import java.io.*;
import java.util.*;
import net.datastructures.ArrayList;
import net.datastructures.List;
import Trabajo_final.modelo.Linea;
import Trabajo_final.modelo.Parada;

/**
 * Clase utilitaria para leer desde un archivo la definición de las líneas de colectivo
 * y construir objetos {@link Linea} con su recorrido de paradas.
 * 
 */
public class LectorLineas {

    /**
     * Carga las líneas desde un archivo de texto plano.
     * Cada línea del archivo debe tener el formato:
     * <pre>
     * CODIGO_LINEA;ID_PARADA_1;ID_PARADA_2;...;ID_PARADA_N
     * </pre>
     * 
     * @param archivo Ruta del archivo a leer.
     * @param paradas Mapa de paradas existentes (clave = ID de parada).
     * @return Lista de objetos {@link Linea} construidos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static java.util.List<Linea> cargarLineas(String archivo, Map<Integer, Parada> paradas) throws IOException {
        java.util.List<Linea> lista = new java.util.ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("#") || linea.isBlank()) continue;
                String[] partes = linea.split(";");
                String codigo = partes[0];
                List<Parada> recorrido = new ArrayList<>();
                for (int i = 1; i < partes.length; i++) {
                    int idParada = Integer.parseInt(partes[i]);
                    if (paradas.containsKey(idParada)) {
                        recorrido.add(recorrido.size(), paradas.get(idParada));
                    }
                }
                lista.add(new Linea(codigo, recorrido));
            }
        }
        return lista;
    }
}
