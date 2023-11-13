package tpaye;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFrame {
    List<List<Object>> columnas;
    Map<String, Integer> colLabels;
    Map<String, Integer> rowLabels;

    public static final Object NA = "NA";


    public DataFrame() {
        columnas = new ArrayList<>();
        colLabels = new HashMap<>();
        rowLabels = new HashMap<>();
    }

    public DataFrame(int cantidadColumnas) {
        if (cantidadColumnas < 1)
            throw new IllegalArgumentException("La longitud de columnas debe ser mayor a 0.");

        columnas = new ArrayList<>();
        colLabels = new HashMap<>();
        rowLabels = new HashMap<>();
        for (int j = 0; j < cantidadColumnas; j++) {
            columnas.add(j, new ArrayList<>());
            colLabels.put(String.valueOf(j), j);
        }
    }

    public DataFrame(int cantidadColumnas, String[] etiquetas) {
        this(cantidadColumnas);
        if (cantidadColumnas != etiquetas.length)
            throw new IllegalArgumentException("La longitud de etiquetas no coincide.");
        setEtiquetasColumnas(etiquetas);
    }

    public DataFrame(Object[][] matriz) {
        this(matriz[0].length);
        inicializarDesdeMatriz(matriz);
    }

    public DataFrame(Object[][] matriz, String[] etiquetasColumnas) {
        this(matriz[0].length, etiquetasColumnas);
        inicializarDesdeMatriz(matriz);
    }

    public DataFrame(Object[][] matriz, String[] etiquetasColumnas, String[] etiquetasFilas) {
        this(matriz, etiquetasColumnas);
        if (columnas.get(0).size() != etiquetasFilas.length)
            throw new IllegalArgumentException("La longitud de etiquetas de filas no coincide.");
        setEtiquetasFilas(etiquetasFilas);
    }

    // Métodos Getter y Setter

    public Object getCelda(String fila, String columna) {
        return columnas.get(colLabels.get(columna)).get(rowLabels.get(fila));
    }

    public int cantDeFilas() {
        return rowLabels.size();
    }

    public int cantDeColumnas() {
        return colLabels.size();
    }

    public Object getElemento(int fila, int columna) {
        if (fila >= 0 && fila < columnas.get(0).size() && columna >= 0 && columna < columnas.size()) {
            return columnas.get(columna).get(fila);
        } else {
            return null;
        }
    }

    public void setEtiquetasFilas(String[] etiquetas) {
        rowLabels.clear();
        for (int i = 0; i < columnas.get(0).size(); i++) {
            rowLabels.put(etiquetas[i], i);
        }
    }

    public void setEtiquetasColumnas(String[] etiquetas) {
        colLabels.clear();
        for (int j = 0; j < columnas.size(); j++) {
            colLabels.put(etiquetas[j], j);
        }
    }

    public void setCelda(int fila, int columna, Object valor) {
        columnas.get(columna).set(fila, valor);
    }

    // Métodos funcionales

    public static DataFrame crearDataFrame(String[] etiquetasColumnas, ArrayList<ArrayList<Object>> datos) throws CSVInvalidException {
        int CantDeFilas = datos.size();
        int CantDeColumnas = etiquetasColumnas.length;

        Object[][] array2D = new Object[CantDeFilas][CantDeColumnas];
        for (int i = 0; i < CantDeFilas; i++) {
            List<Object> fila = datos.get(i);

            // Verificar si la fila tiene la misma cantidad de columnas que etiquetasColumnas
            if (fila.size() != CantDeColumnas) {
                throw new CSVInvalidException("La cantidad de columnas en el archivo CSV no coincide con las etiquetas. Error en la fila " + (i + 2));
            }

            for (int j = 0; j < CantDeColumnas; j++) {
                array2D[i][j] = fila.get(j);
            }
        }

        DataFrame retorno = new DataFrame(array2D, etiquetasColumnas);
        return retorno;
    }

    private void inicializarDesdeMatriz(Object[][] matriz) {
        for (int j = 0; j < columnas.size(); j++) {
            for (int i = 0; i < matriz.length; i++) {
                columnas.get(j).add(i, matriz[i][j]);
            }
        }
        for (int i = 0; i < columnas.get(0).size(); i++) {
            rowLabels.put(String.valueOf(i), i);
        }
    }

    public static DataFrame crearDesdeMatriz(Object[][] matriz) {
        return new DataFrame(matriz);
    }

    public List<String> etiquetaDeFilas() {
        List<String> etiquetas_filas = new ArrayList<>();
        for (String elemento : rowLabels.keySet()) {
            etiquetas_filas.add(elemento);
        }
        return etiquetas_filas;
    }

    public List<String> etiquetaDeColumnas() {
        List<String> etiquetas_columnas = new ArrayList<>();
        for (String elemento : colLabels.keySet()) {
            etiquetas_columnas.add(elemento);
        }
        return etiquetas_columnas;
    }

    public String obtenerKeyDeValor(Map<String, Integer> map, int valueToFind) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == valueToFind) {
                return entry.getKey();
            }
        }
        return null;
    }


    public List<Fila> SeleccionarFilas(int[] ixFilas) {
        List<Fila> filas = new ArrayList<>();
        for (int filaDeseada : ixFilas) {
            List<Object> celdas = new ArrayList<>();
            for (int j = 0; j < this.cantDeColumnas(); j++) {
                celdas.add(this.getElemento(filaDeseada, j));
            }
            filas.add(new Fila(celdas));
        }

        System.out.println("Submatriz con filas seleccionadas:");
        for (Fila fila : filas)
            System.out.println(fila.getCeldas());

        return filas;
    }

    public List<Fila> SeleccionarColumnas(int[] ixColumnas) {

        List<Fila> filas = new ArrayList<>();
        for (int j = 0; j < this.cantDeFilas(); j++) {
            List<Object> celdas = new ArrayList<>();
            for (int columnasDeseada : ixColumnas) { // {1,3}
                celdas.add(this.getElemento(j, columnasDeseada));
            }
            filas.add(new Fila(celdas));
        }

        System.out.println("Submatriz con columnas seleccionadas:");
        for (Fila fila : filas)
            System.out.println(fila.getCeldas());

        return filas;
    }
}
