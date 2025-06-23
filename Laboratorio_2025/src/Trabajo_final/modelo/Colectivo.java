package Trabajo_final.modelo;

import net.datastructures.ArrayList;
import net.datastructures.List;
import java.util.Iterator;

/**
 * Representa un colectivo que recorre una línea con una capacidad limitada
 * de pasajeros y asientos.
 * 
 * La clase gestiona el proceso de subir y bajar pasajeros,
 * así como las estadísticas de ocupación durante los recorridos.
 */
public class Colectivo {
    private int id;
    private Linea linea;
    private List<Pasajero> pasajeros;
    private int capacidadTotal = 40;
    private int asientosTotales = 30;

    private int totalPasajerosTransportados = 0;
    private int sumaOcupacion = 0;
    private int cantidadSubidas = 0;

    /**
     * Constructor del colectivo.
     * 
     * @param id Identificador del colectivo.
     * @param linea Línea que recorre el colectivo.
     */
    public Colectivo(int id, Linea linea) {
        this.id = id;
        this.linea = linea;
        this.pasajeros = new ArrayList<>();
    }

    /**
     * @return Identificador del colectivo.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Línea asignada al colectivo.
     */
    public Linea getLinea() {
        return linea;
    }

    /**
     * @return Lista de pasajeros que actualmente están en el colectivo.
     */
    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }

    /**
     * @return Cantidad total de pasajeros que transportó el colectivo.
     */
    public int getTotalPasajerosTransportados() {
        return totalPasajerosTransportados;
    }

    /**
     * @return Capacidad total del colectivo (cantidad máxima de pasajeros).
     */
    public int getCapacidadTotal() {
        return capacidadTotal;
    }

    /**
     * Indica si hay espacio para subir más pasajeros.
     * 
     * @return true si hay espacio disponible, false si el colectivo está lleno.
     */
    public boolean hayEspacio() {
        return pasajeros.size() < capacidadTotal;
    }

    /**
     * Indica si hay asientos disponibles en el colectivo.
     * 
     * @return true si hay asientos libres, false si todos están ocupados.
     */
    public boolean hayAsiento() {
        int sentados = 0;
        for (Pasajero p : pasajeros) {
            if (p.isFueSentado()) sentados++;
        }
        return sentados < asientosTotales;
    }

    /**
     * Intenta subir un pasajero al colectivo. Si hay lugar, lo sube y
     * actualiza su estado (sentado o no).
     * 
     * @param p Pasajero que desea subir.
     * @return true si el pasajero pudo subir, false en caso contrario.
     */
    public boolean subirPasajero(Pasajero p) {
        if (hayEspacio()) {
            if (hayAsiento()) {
                p.setFueSentado(true);
            } else {
                p.setFueSentado(false);
            }
            pasajeros.add(pasajeros.size(), p);
            p.setSubio(true);

            totalPasajerosTransportados++;
            sumaOcupacion += pasajeros.size();
            cantidadSubidas++;

            return true;
        } else {
            return false;
        }
    }

    /**
     * Baja del colectivo a los pasajeros cuyo destino coincide
     * con la parada actual.
     * 
     * @param paradaActual Parada en la que se encuentra el colectivo.
     * @return Lista de pasajeros que bajaron en esta parada.
     */
    public List<Pasajero> bajarPasajerosEn(Parada paradaActual) {
        List<Pasajero> queBajan = new ArrayList<>();
        Iterator<Pasajero> it = pasajeros.iterator();
        while (it.hasNext()) {
            Pasajero p = it.next();
            if (p.getDestino().getId() == paradaActual.getId()) {
                queBajan.add(queBajan.size(), p);
                it.remove();
            }
        }
        return queBajan;
    }

    /**
     * Calcula la ocupación promedio del colectivo durante los tramos recorridos.
     * 
     * @return Ocupación promedio (cantidad de pasajeros por tramo).
     */
    public double getOcupacionPromedio() {
        if (cantidadSubidas == 0) return 0;
        return (double) sumaOcupacion / cantidadSubidas;
    }

    /**
     * @return Representación en texto del colectivo (ID y línea).
     */
    @Override
    public String toString() {
        return "Colectivo{" + "id=" + id + ", linea=" + linea.getCodigo() + '}';
    }
}
