package Trabajo_final.modelo;

import net.datastructures.LinkedQueue;
import net.datastructures.Queue;

/**
 * Representa una parada dentro de una línea de colectivo.
 * Contiene un identificador, una dirección y una cola de pasajeros esperando.
 */
public class Parada {
    private int id;
    private String direccion;
    private Queue<Pasajero> pasajeros;

    /**
     * Constructor de la parada.
     *
     * @param id Identificador único de la parada.
     * @param direccion Dirección física o descripción de la ubicación.
     */
    public Parada(int id, String direccion) {
        this.id = id;
        this.direccion = direccion;
        this.pasajeros = new LinkedQueue<>();
    }

    /**
     * @return Identificador de la parada.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Dirección de la parada.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @return Cola de pasajeros que están esperando en la parada.
     */
    public Queue<Pasajero> getPasajeros() {
        return pasajeros;
    }

    /**
     * Agrega un pasajero a la cola de espera en la parada.
     *
     * @param p Pasajero a agregar.
     */
    public void agregarPasajero(Pasajero p) {
        pasajeros.enqueue(p);
    }

    /**
     * Remueve un pasajero de la cola (el primero que llegó).
     *
     * @return El pasajero removido.
     */
    public Pasajero removerPasajero() {
        return pasajeros.dequeue();
    }

    /**
     * Verifica si hay pasajeros esperando en la parada.
     *
     * @return true si hay pasajeros, false en caso contrario.
     */
    public boolean tienePasajeros() {
        return !pasajeros.isEmpty();
    }

    /**
     * @return Representación textual de la parada (id y dirección).
     */
    @Override
    public String toString() {
        return "Parada{" + "id=" + id + ", direccion='" + direccion + '\'' + '}';
    }
}
