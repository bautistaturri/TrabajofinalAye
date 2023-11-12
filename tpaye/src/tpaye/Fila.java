package tpaye;

import java.util.List;

public class Fila {
    private final List<Object> celdas;

    public Fila(List<Object> celdas) {
        this.celdas = celdas;
    }

    public List<Object> getCeldas() {
        return celdas;
    }

    // Agrega un método personalizado para comparar dos filas
    public int compararCon(Fila otraFila) {
        if (otraFila == null)
            throw new NullPointerException();

        int comparacion = 0;
        int i = 0;

        while (comparacion == 0 && i < celdas.size() && i < otraFila.getCeldas().size()) {
            Object celdaActual = celdas.get(i);
            Object otraCelda = otraFila.getCeldas().get(i);

            if (celdaActual == null && otraCelda != null) {
                comparacion = -1;
            } else if (celdaActual != null && otraCelda == null) {
                comparacion = 1;
            } else if (celdaActual != null) {
                comparacion = celdaActual.equals(otraCelda) ? 0 : celdaActual.toString().compareTo(otraCelda.toString());
            }

            i++;
        }

        return comparacion;
    }
}