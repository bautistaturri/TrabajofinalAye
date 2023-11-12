package tpaye;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisualizadorDatos {
    private DataFrame matriz; // Se agrega una referencia a la matriz

    public VisualizadorDatos(DataFrame matriz) {
        this.matriz = matriz;
    }

    public void visualizarDatos() {
        List<String> etiquetasColumnas = matriz.etiquetaDeColumnas();
        int numRows = matriz.cantDeFilas();
    //    int numCols = matriz.CantDeColumnas();

        // Obtener el ancho máximo de cada columna
        Map<String, Integer> anchosColumnas = new HashMap<>();
        for (String etiqueta : etiquetasColumnas) {
            int anchoMaximo = etiqueta.length();
            for (int fila = 0; fila < numRows; fila++) {
                Object valor = matriz.getElemento(fila, etiquetasColumnas.indexOf(etiqueta));
                if (valor != null) {
                    anchoMaximo = Math.max(anchoMaximo, valor.toString().length());
                }
            }
            anchosColumnas.put(etiqueta, anchoMaximo);
        }

        // Imprimir las etiquetas de columna con separadores y barras horizontales
        for (String etiquetaColumna : etiquetasColumnas) {
            System.out.printf("%-" + (anchosColumnas.get(etiquetaColumna) + 1) + "s|", etiquetaColumna);
        }
        System.out.println();

        for (String etiquetaColumna : etiquetasColumnas) {
            for (int i = 0; i < anchosColumnas.get(etiquetaColumna) + 1; i++) {
                System.out.print("-");
            }
            System.out.print("+");
        }
        System.out.println();

        for (int fila = 0; fila < numRows; fila++) {
            for (String etiquetaColumna : etiquetasColumnas) {
                Object valor = matriz.getElemento(fila, etiquetasColumnas.indexOf(etiquetaColumna));
                System.out.printf("%-" + (anchosColumnas.get(etiquetaColumna) + 1) + "s|", valor);
            }
            System.out.println();
        }
    }
}