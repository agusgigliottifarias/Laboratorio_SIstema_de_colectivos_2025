package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import Trabajo_final.modelo.*;
import Trabajo_final.logica.GestorSimulacion;

import net.datastructures.ArrayList;
import net.datastructures.List;
import net.datastructures.UnsortedTableMap;

/**
 * Pruebas unitarias para validar el comportamiento real de los pasajeros
 * en el proceso de subir y bajar del colectivo durante la simulación.
 */
public class TestPasajeroReal {

    /**
     * Test que simula un pasajero que espera en una parada y verifica que
     * se suba al colectivo en la parada correcta y luego se baje en su destino.
     */
    @Test
    public void testPasajero() {
        
        Parada p1 = new Parada(1, "1 De Marzo, 405");
        Parada p2 = new Parada(2, "1 De Marzo, 499");
        Parada p3 = new Parada(3, "25 De Mayo, 299");

        List<Parada> paradasLinea = new ArrayList<>();
        paradasLinea.add(paradasLinea.size(), p1);
        paradasLinea.add(paradasLinea.size(), p2);
        paradasLinea.add(paradasLinea.size(), p3);

        Linea linea = new Linea("Línea Real", paradasLinea);
        Colectivo colectivo = new Colectivo(1, linea);

        Pasajero pasajero = new Pasajero(1, p3);
        p1.agregarPasajero(pasajero); 

        GestorSimulacion gestor = new GestorSimulacion(new ArrayList<>(), new UnsortedTableMap<>(), 0);

        gestor.procesarParada(p1, colectivo); 
        gestor.procesarParada(p2, colectivo); 
        gestor.procesarParada(p3, colectivo);

        assertTrue(pasajero.isSubio(), "El pasajero debería haberse subido.");
        assertEquals(0, colectivo.getPasajeros().size(), "El colectivo debería estar vacío si el pasajero bajó.");
    }
}
