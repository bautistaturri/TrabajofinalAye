package tpaye;

import java.util.Arrays;

public class TablaSeleccionada {
    private DataFrame matriz; // La matriz original
    private int[] filasSeleccionadas;
    private int[] columnasSeleccionadas;

    public TablaSeleccionada(DataFrame matriz) {
        this.matriz = matriz;
    }

    public TablaSeleccionada filas(int[] indicesFilas) {
        TablaSeleccionada seleccion = new TablaSeleccionada(matriz);
        seleccion.filasSeleccionadas = Arrays.copyOf(indicesFilas, indicesFilas.length);
        seleccion.columnasSeleccionadas = this.columnasSeleccionadas;
        return seleccion;
    }

    public TablaSeleccionada columnas(int[] indicesColumnas) {
        TablaSeleccionada seleccion = new TablaSeleccionada(matriz);
        seleccion.columnasSeleccionadas = Arrays.copyOf(indicesColumnas, indicesColumnas.length);
        seleccion.filasSeleccionadas = this.filasSeleccionadas;
        return seleccion;
    }

    public TablaSeleccionada head(int x) {
        int[] indicesFilas = new int[x];
        for (int i = 0; i < x; i++) {
            indicesFilas[i] = i;
        }
        return filas(indicesFilas);
    }

    public TablaSeleccionada tail(int x) {
        int[] indicesFilas = new int[x];
        for (int i = 0; i < x; i++) {
            indicesFilas[i] = matriz.cantDeFilas() - x + i;
        }
        return filas(indicesFilas);
    }

    public DataFrame obtenerMatrizSeleccionada() {
        if (filasSeleccionadas == null || columnasSeleccionadas == null) {
            return null;
        }
    
        DataFrame matrizSeleccionada = new DataFrame(columnasSeleccionadas.length);
    
        for (int i = 0; i < filasSeleccionadas.length; i++) {
            for (int j = 0; j < columnasSeleccionadas.length; j++) {
                int fila = filasSeleccionadas[i];
                int columna = columnasSeleccionadas[j];
                if (fila >= 0 && fila < matriz.cantDeFilas() && columna >= 0 && columna < matriz.cantDeColumnas()) {
                    // Solo establece la celda si los índices son válidos
                    matrizSeleccionada.setCelda(i, j, matriz.getElemento(fila, columna));
                }
            }
        }
    
        return matrizSeleccionada;
    }
    
}