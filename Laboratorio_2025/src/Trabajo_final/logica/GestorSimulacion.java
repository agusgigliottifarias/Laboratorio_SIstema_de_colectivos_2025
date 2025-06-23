package Trabajo_final.logica;

import Trabajo_final.modelo.*;
import net.datastructures.List;
import net.datastructures.ArrayList;
import net.datastructures.Queue;
import net.datastructures.LinkedQueue;
import net.datastructures.Map;
import java.util.Random;

/**
 * Clase responsable de gestionar la simulación de recorridos de colectivos,
 * controlando la subida y bajada de pasajeros, el cálculo de la ocupación
 * y la satisfacción de los usuarios.
 */
public class GestorSimulacion {
    private List<Linea> lineas;
    private Map<Integer, Parada> paradas;
    private int cantidadPasajeros;

    private List<Pasajero> pasajerosProcesados = new ArrayList<>();

    private int totalPasajerosGenerados = 0;
    private int totalPasajerosSubidos = 0;
    private int sumaPasajerosEnColectivo = 0;
    private int totalRecorridos = 0;
    private double sumaOcupacionTramos = 0.0;
    private int cantidadTramos = 0;

    private int[] calificaciones = new int[6];

    /**
     * Constructor del gestor de simulación.
     * 
     * @param lineas Lista de líneas de colectivos disponibles.
     * @param paradas Mapa de paradas existentes.
     * @param cantidadPasajeros Cantidad total de pasajeros a generar.
     */
    public GestorSimulacion(List<Linea> lineas, Map<Integer, Parada> paradas, int cantidadPasajeros) {
        this.lineas = lineas;
        this.paradas = paradas;
        this.cantidadPasajeros = cantidadPasajeros;
    }

    /**
     * Ejecuta la simulación completa de los recorridos de los colectivos,
     * generando los pasajeros, procesando paradas y calculando métricas.
     */
    public void ejecutar() {
        generarPasajeros();
        int idColectivo = 1;
        int cantidadRecorridos = 2;

        for (Linea linea : lineas) {
            Colectivo colectivo = new Colectivo(idColectivo++, linea);
            System.out.println("\n== Recorriendo línea: " + linea.getCodigo() + " ==");

            for (int r = 1; r <= cantidadRecorridos; r++) {
                System.out.println("== Recorrido número " + r + " ==");

                // Ida
                List<Parada> paradas = linea.getParadas();
                for (Parada parada : paradas) {
                    procesarParada(parada, colectivo);
                    registrarOcupacion(colectivo);
                    mostrarOcupacion(colectivo, parada);
                }

                
            }
        }
    }

    /**
     * Procesa una parada: baja los pasajeros que llegan a su destino
     * y sube a los que están esperando si hay capacidad.
     * 
     * @param parada Parada actual a procesar.
     * @param colectivo Colectivo que está recorriendo.
     */
    public void procesarParada(Parada parada, Colectivo colectivo) {
        System.out.println("\nParada actual: " + parada);

        List<Pasajero> queBajan = colectivo.bajarPasajerosEn(parada);
        System.out.println("Pasajeros que bajan: " + queBajan);

        Queue<Pasajero> pasajerosEnParada = parada.getPasajeros();
        Queue<Pasajero> pasajerosRestantes = new LinkedQueue<>();

        while (!pasajerosEnParada.isEmpty()) {
            Pasajero p = pasajerosEnParada.dequeue();
            if (colectivo.subirPasajero(p)) {
                System.out.println("Pasajero que sube: " + p);
                pasajerosProcesados.add(pasajerosProcesados.size(), p);
                totalPasajerosSubidos++;
                p.setSubio(true);
                calificarPasajero(p);
            } else {
                p.setNumeroDeIntentos(p.getNumeroDeIntentos() + 1);
                pasajerosRestantes.enqueue(p);
            }
        }

        // Reincorporar los pasajeros que no pudieron subir
        while (!pasajerosRestantes.isEmpty()) {
            pasajerosEnParada.enqueue(pasajerosRestantes.dequeue());
        }
    }

    /**
     * Asigna una calificación al pasajero en base a sus intentos de subida
     * y si pudo viajar sentado.
     * 
     * @param p Pasajero a calificar.
     */
    private void calificarPasajero(Pasajero p) {
        int intentos = p.getNumeroDeIntentos();
        boolean sentado = p.isFueSentado();
        int calif = 0;

        if (intentos == 0 && sentado) calif = 5;
        else if (intentos == 0 && !sentado) calif = 4;
        else if (intentos == 1) calif = 3;
        else if (intentos >= 2) calif = 2;
        else calif = 1;

        calificaciones[calif]++;
    }

    /**
     * Muestra por consola la ocupación actual del colectivo en la parada dada.
     * 
     * @param colectivo Colectivo actual.
     * @param parada Parada desde la cual se calcula el tramo.
     */
    private void mostrarOcupacion(Colectivo colectivo, Parada parada) {
        double ocupacion = (double) colectivo.getPasajeros().size() / colectivo.getCapacidadTotal() * 100;
        System.out.printf("Ocupación del colectivo %d en tramo después de parada %s: %.2f%%\n", 
                colectivo.getId(), parada.toString(), ocupacion);
    }

    /**
     * Registra la ocupación del colectivo para calcular la ocupación promedio.
     * 
     * @param colectivo Colectivo en el tramo actual.
     */
    private void registrarOcupacion(Colectivo colectivo) {
        double ocupacion = (double) colectivo.getPasajeros().size() / colectivo.getCapacidadTotal();
        sumaOcupacionTramos += ocupacion;
        cantidadTramos++;
    }

    /**
     * Genera pasajeros aleatorios para cada línea del sistema.
     * Se asignan paradas de origen y destino de forma aleatoria.
     */
    private void generarPasajeros() {
        Random rand = new Random();
        int idPasajero = 1;

        for (Linea linea : lineas) {
            List<Parada> paradasDeLinea = linea.getParadas();
            int cantidadParadas = paradasDeLinea.size();

            if (cantidadParadas < 2) continue;

            int pasajerosPorLinea = cantidadPasajeros / lineas.size();
            totalPasajerosGenerados += pasajerosPorLinea;

            for (int i = 0; i < pasajerosPorLinea; i++) {
                int origenIndex = rand.nextInt(cantidadParadas - 1);
                int destinoIndex = rand.nextInt(cantidadParadas - origenIndex - 1) + origenIndex + 1;

                Parada origen = paradasDeLinea.get(origenIndex);
                Parada destino = paradasDeLinea.get(destinoIndex);

                Pasajero p = new Pasajero(idPasajero++, destino);
                origen.agregarPasajero(p);
            }
        }
    }

    /**
     * Calcula el índice de satisfacción general de los pasajeros.
     * 
     * @return Valor entre 0 y 100 que representa el porcentaje de satisfacción.
     */
    public double getIndiceSatisfaccion() {
        int totalPasajeros = 0;
        int sumaCalif = 0;
        for (int i = 1; i < calificaciones.length; i++) {
            totalPasajeros += calificaciones[i];
            sumaCalif += calificaciones[i] * i;
        }
        if (totalPasajeros == 0) return 0.0;
        return ((double) sumaCalif / (totalPasajeros * 5) * 100);
    }

    /**
     * Calcula la ocupación promedio del colectivo durante la simulación,
     * considerando todos los tramos recorridos.
     * 
     * @return Porcentaje promedio de ocupación.
     */
    public double getOcupacionPromedio() {
        if (cantidadTramos == 0) return 0.0;
        return sumaOcupacionTramos / cantidadTramos * 100;
    }
}
