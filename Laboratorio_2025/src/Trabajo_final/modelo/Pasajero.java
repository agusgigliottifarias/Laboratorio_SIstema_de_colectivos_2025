package Trabajo_final.modelo;

/**
 * Representa un pasajero que desea realizar un viaje en colectivo,
 * indicando su destino, cantidad de intentos, si logró subir y si viajó sentado.
 */
public class Pasajero {
    private int id;
    private Parada destino;
    private int numeroDeIntentos = 1;
    private boolean subio = false;
    private boolean fueSentado = true;

    /**
     * Constructor completo del pasajero.
     *
     * @param id Identificador único del pasajero.
     * @param destino Parada de destino.
     * @param numeroDeIntentos Cantidad de intentos previos de subida.
     * @param subio true si logró subir al colectivo.
     * @param fueSentado true si logró viajar sentado.
     */
    public Pasajero(int id, Parada destino, int numeroDeIntentos, boolean subio, boolean fueSentado) {
        this.id = id;
        this.destino = destino;
        this.numeroDeIntentos = numeroDeIntentos;
        this.subio = subio;
        this.fueSentado = fueSentado;
    }

    /**
     * Constructor simplificado (por defecto no subió y está sentado).
     *
     * @param id Identificador del pasajero.
     * @param destino Parada de destino.
     */
    public Pasajero(int id, Parada destino) {
        this(id, destino, 1, false, true);
    }

    /**
     * @return Identificador del pasajero.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Parada de destino del pasajero.
     */
    public Parada getDestino() {
        return destino;
    }

    /**
     * @return Cantidad de intentos que el pasajero hizo para subir.
     */
    public int getNumeroDeIntentos() {
        return numeroDeIntentos;
    }

    /**
     * Establece la cantidad de intentos que hizo el pasajero.
     *
     * @param numeroDeIntentos Cantidad de intentos.
     */
    public void setNumeroDeIntentos(int numeroDeIntentos) {
        this.numeroDeIntentos = numeroDeIntentos;
    }

    /**
     * @return true si el pasajero logró subir al colectivo.
     */
    public boolean isSubio() {
        return subio;
    }

    /**
     * Establece si el pasajero logró subir.
     *
     * @param subio true si subió.
     */
    public void setSubio(boolean subio) {
        this.subio = subio;
    }

    /**
     * @return true si el pasajero viajó sentado.
     */
    public boolean isFueSentado() {
        return fueSentado;
    }

    /**
     * Establece si el pasajero viajó sentado.
     *
     * @param fueSentado true si viajó sentado.
     */
    public void setFueSentado(boolean fueSentado) {
        this.fueSentado = fueSentado;
    }

    /**
     * Calcula una calificación de satisfacción del pasajero según su experiencia de viaje.
     *
     * @return Un valor de 1 (muy insatisfecho) a 5 (muy satisfecho).
     */
    public int satisfaccion() {
        if (!subio) return 1;
        if (numeroDeIntentos == 1 && fueSentado) return 5;
        if (numeroDeIntentos == 1 && !fueSentado) return 4;
        if (numeroDeIntentos == 2) return 3;
        if (numeroDeIntentos >= 3) return 2;
        return 1;
    }

    /**
     * @return Representación textual del pasajero (id y destino).
     */
    @Override
    public String toString() {
        return "Pasajero{" + "id=" + id + ", destino=" + destino.getId() + '}';
    }
}
