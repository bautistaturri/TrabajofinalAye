package tpaye;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataFrame {
    List<List<Object>> columnas;
    Map<String, Integer> colLabels;
    Map<String, Integer> rowLabels;
    
    
    // Metodos constructores
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

    
    //Metodos Getter
    
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

    
    //Metodos Setter
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


    //Metodos funcionales de dataframe
    
    
    
    
    public static DataFrame crearDataFrame(String[] etiquetasColumnas, ArrayList<ArrayList<Object>> datos) {
        int CantDeFilas = datos.size();
        int CantDeColumnas = etiquetasColumnas.length;

        Object[][] array2D = new Object[CantDeFilas][CantDeColumnas];
        for (int i = 0; i < CantDeFilas; i++) {
            List<Object> fila = datos.get(i);
            for (int j = 0; j < fila.size(); j++) {
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
        // for (String elemento : colLabels.keySet()) {
        // etiquetas_columnas.add(elemento);
        // }
        for (int i = 0; i < colLabels.size(); i++) {
            etiquetas_columnas.add(obtenerKeyDeValor(colLabels, i));
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

    public List<String> obtenerTiposDeDatosColumnas() {
        List<String> tiposDeDatosColumnas = new ArrayList<>();
        for (List<Object> columna : columnas) {
            if (columna.isEmpty()) {
                tiposDeDatosColumnas.add("NA"); // Si la columna está vacía, consideramos "NA".
            } else {
                boolean encontrado = false;
                for (Object elemento : columna) {
                    if (elemento != null && !elemento.equals(NA)) {
                        // Verifica si el elemento es un número entero.
                        if (elemento instanceof String && ((String) elemento).matches("-?\\d+")) {
                            tiposDeDatosColumnas.add("Integer");
                        } else {
                            String tipo = elemento.getClass().toString();
                            tiposDeDatosColumnas.add(tipo);
                        }
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    tiposDeDatosColumnas.add("NA"); // Si no se encontró un elemento distinto de "NA", consideramos
                                                    // "NA".
                }
            }
        }
        return tiposDeDatosColumnas;
    }

   
    public static final Object NA = "NA";

    // Modificar para que en vez de devolverme la lista de filas, me devuelva una
    // matriz con las filas deseadas.
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

    // Similar arriba, solo que devuelva una matriz con las columnas deseadas.
    public List<Fila> SeleccionarColumnas(int[] ixColumnas) {

        List<Fila> filas = new ArrayList<>();
        for (int j = 0; j < this.cantDeFilas(); j++) 
        {
            List<Object> celdas = new ArrayList<>();
            for (int columnasDeseada : ixColumnas) { // {1,3}
                celdas.add(this.getElemento(j, columnasDeseada));
            }
            filas.add(new Fila(celdas));
        }

        System.out.println("Submatriz con columnas seleccionadas:");
        for (Fila fila : filas)
            System.out.println( fila.getCeldas());

        return filas;
    }
    
    
    
    
    
}