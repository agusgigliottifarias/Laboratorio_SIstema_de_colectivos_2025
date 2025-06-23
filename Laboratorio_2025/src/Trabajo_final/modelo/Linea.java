package Trabajo_final.modelo;

import net.datastructures.ArrayList;
import net.datastructures.List;

/**
 * Representa una línea de colectivo, compuesta por un código identificador
 * y una lista de paradas que conforman su recorrido.
 */
public class Linea {
    private String codigo;
    private List<Parada> paradas;

    /**
     * Constructor de la línea.
     * 
     * @param codigo Código identificador de la línea.
     * @param paradas Lista de paradas en orden de recorrido.
     */
    public Linea(String codigo, List<Parada> paradas) {
        this.codigo = codigo;
        this.paradas = paradas;
    }

    /**
     * @return Código identificador de la línea.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return Lista de paradas que conforman el recorrido de la línea.
     */
    public List<Parada> getParadas() {
        return paradas;
    }

    /**
     * @return Representación textual de la línea.
     */
    @Override
    public String toString() {
        return "Linea{" + "codigo='" + codigo + '\'' + ", paradas=" + paradas + '}';
    }
}
