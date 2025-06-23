package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Trabajo_final.modelo.*;
import Trabajo_final.logica.GestorSimulacion;

import net.datastructures.*;

/**
 * Pruebas unitarias para validar el cálculo del índice de satisfacción en GestorSimulacion.
 */
public class TestIndiceSatisfaccion {

    /**
     * Test básico que crea una línea con paradas y ejecuta la simulación para obtener
     * el índice de satisfacción. Verifica que el índice esté en el rango esperado [0,100].
     */
    @Test
    public void testIndiceBasico() {
        
        Parada p1 = new Parada(1, "1 De Marzo, 405");
        Parada p2 = new Parada(2, "1 De Marzo, 499");
        Parada p3 = new Parada(3, "25 De Mayo, 299");

        List<Parada> paradas = new ArrayList<>();
        paradas.add(paradas.size(), p1);
        paradas.add(paradas.size(), p2);
        paradas.add(paradas.size(), p3);

        Linea linea = new Linea("Línea Real", paradas);
        List<Linea> lineas = new ArrayList<>();
        lineas.add(0, linea);

        Map<Integer, Parada> mapaParadas = new UnsortedTableMap<>();
        mapaParadas.put(1, p1);
        mapaParadas.put(2, p2);
        mapaParadas.put(3, p3);

        GestorSimulacion gestor = new GestorSimulacion(lineas, mapaParadas, 20);
        gestor.ejecutar();

        double indice = gestor.getIndiceSatisfaccion();

        System.out.println("Índice de satisfacción: " + indice);

        assertTrue(indice >= 0.0 && indice <= 100.0, "El índice debe estar entre 0 y 100");
    }
}
