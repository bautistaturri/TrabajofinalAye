package tpaye;
import tpaye.CSVInvalidException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVparser {
    public static DataFrame leerCSV(String filePath) throws CSVInvalidException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String[] etiquetasColumnas = reader.readLine().split(",");
            ArrayList<ArrayList<Object>> datos = new ArrayList<>();

            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] valores = linea.split(",");
                ArrayList<Object> fila = new ArrayList<>();
                for (String valor : valores) {
                    Object valorParseado = ParserUtils.parsearValor(valor);
                    fila.add(valorParseado);
                }
                datos.add(fila);
            }

            reader.close(); // Cierro el archivo

            return DataFrame.crearDataFrame(etiquetasColumnas, datos);
        } catch (IOException e) {
            // Si ocurre una IOException, la convertimos a CSVInvalidException
            throw new CSVInvalidException("Error al leer el archivo CSV: " + e.getMessage());
        }
    }
}

