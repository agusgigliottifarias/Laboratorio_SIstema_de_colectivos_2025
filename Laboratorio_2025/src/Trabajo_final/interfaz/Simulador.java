package Trabajo_final.interfaz;

import Trabajo_final.modelo.*;
import Trabajo_final.datos.*;
import Trabajo_final.logica.*;

import net.datastructures.List;
import net.datastructures.Map;
import net.datastructures.ArrayList;
import net.datastructures.UnsortedTableMap;

import java.util.*;
import javax.swing.JOptionPane;

/**
 * Clase principal que ejecuta la simulación de líneas de colectivos.
 *
 * Lee los archivos de configuración, paradas y líneas,
 * crea los objetos necesarios y lanza la simulación mediante
 * la clase {@link GestorSimulacion}.
 */
public class Simulador {

    /**
     * Método principal del programa.
     *
     * Ejecuta la simulación leyendo archivos externos de configuración
     * y muestra los resultados al finalizar.
     *
     * @param args Argumentos de línea de comando (no utilizados).
     */
    public static void main(String[] args) {
        System.out.println("Bienvenido a la Simulación de Líneas de Colectivos de Puerto Madryn 🚌");

        // --- INICIO DEL CONTADOR DE TIEMPO ---
        // Se usa nanoTime() para una mayor precisión en la medición del tiempo de ejecución.
        long startTime = System.nanoTime();

        try {
            // Cargar configuración desde archivo
            Configuracion config = new Configuracion("config.properties");

            // Cargar paradas desde archivo y convertir a estructura de datos personalizada
            java.util.Map<Integer, Parada> paradasJava = LectorParadas.cargarParadas("parada.txt");
            Map<Integer, Parada> paradasNet = new UnsortedTableMap<>();
            for (java.util.Map.Entry<Integer, Parada> entry : paradasJava.entrySet()) {
                paradasNet.put(entry.getKey(), entry.getValue());
            }

            // Cargar líneas desde archivo y convertir a estructura de datos personalizada
            java.util.List<Linea> lineasJava = LectorLineas.cargarLineas("linea.txt", paradasJava);
            List<Linea> lineasNet = new ArrayList<>();
            for (Linea linea : lineasJava) {
                lineasNet.add(lineasNet.size(), linea); // Agrega al final de la lista
            }

            // Crear el gestor de simulación y ejecutar
            GestorSimulacion gestor = new GestorSimulacion(lineasNet, paradasNet, config.getCantidadPasajeros());
            gestor.ejecutar();

            // --- FIN DEL CONTADOR DE TIEMPO ---
            long endTime = System.nanoTime();

            // Calcular el tiempo transcurrido en nanosegundos y convertir a segundos para mejor legibilidad
            long durationInNano = (endTime - startTime);
            double durationInSeconds = (double) durationInNano / 1_000_000_000.0; // Conversión a segundos

            // Mostrar resultados de la simulación, incluyendo el tiempo de ejecución
            String mensaje = String.format("""
                        ==== RESULTADOS DE LA SIMULACIÓN ====
                        Tiempo de Ejecución: %.4f segundos
                        Índice de Satisfacción: %.2f%%
                        Ocupación Promedio: %.2f pasajeros
                        =====================================
                        """, durationInSeconds, gestor.getIndiceSatisfaccion(), gestor.getOcupacionPromedio());

            JOptionPane.showMessageDialog(null, mensaje, "Resultados de la Simulación", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            // Captura cualquier excepción que ocurra durante la simulación o carga de archivos
            // y muestra un mensaje de error al usuario.
            JOptionPane.showMessageDialog(null,
                "Error en la simulación:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprime el stack trace en la consola para depuración
        }

        System.out.println("\nSimulación terminada. ¡Gracias por viajar con nosotros!");
    }
}