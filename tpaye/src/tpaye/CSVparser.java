package tpaye;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVparser {
    public static DataFrame leerCSV(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String[] etiquetasColumnas = reader.readLine().split(",");
        ArrayList<ArrayList<Object>> datos = new ArrayList<>();

        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] valores = linea.split(",");
            ArrayList<Object> fila = new ArrayList<>();
            for (String valor : valores) {
                Object valorParseado = parsearValor(valor);
                fila.add(valorParseado);
            }
            datos.add(fila);
        }

        reader.close(); // Cierro el archivo

        return crearMatriz(etiquetasColumnas, datos);
    }

    private static Object parsearValor(String valor) {
        if (valor.equals("NA")) {
            return null;
        } else if (esEntero(valor)) {
            return Integer.parseInt(valor);
        } else if (esDecimal(valor)) {
            return Double.parseDouble(valor);
        } else if (esBooleano(valor)) {
            return Boolean.parseBoolean(valor);
        } else {
            return valor;
        }
    }

    private static boolean esEntero(String valor) {
        try {
            Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean esDecimal(String valor) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean esBooleano(String valor) {
        return valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("false");
    }

    private static DataFrame crearMatriz(String[] etiquetasColumnas, ArrayList<ArrayList<Object>> datos) {
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
}