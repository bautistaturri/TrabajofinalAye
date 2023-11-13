package tpaye;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ParserUtils {
    public static Object parsearValor(String valor) {
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

    public static boolean esEntero(String valor) {
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

    public static List<String> obtenerTiposDeDatosColumnas(List<List<Object>> columnas) {
        List<String> tiposDeDatosColumnas = new ArrayList<>();
        for (List<Object> columna : columnas) {
            String tipoColumna = obtenerTipoColumna(columna);
            tiposDeDatosColumnas.add(tipoColumna);
        }
        return tiposDeDatosColumnas;
    }

    private static String obtenerTipoColumna(List<Object> columna) {
        if (columna.isEmpty()) {
            return "NA"; // Si la columna está vacía, consideramos "NA".
        }

        for (Object elemento : columna) {
            if (elemento != null) {
                if (esNumero(elemento)) {
                    return "Number";
                } else if (elemento instanceof Integer) {
                    return "Integer";
                } else if (elemento instanceof Double) {
                    return "Double";
                } else if (elemento instanceof Boolean) {
                    return "Boolean";
                } else {
                    return "String";
                }
            }
        }

        return "NA"; // Si no se encontró un elemento distinto de "NA", consideramos "NA".
    }

    private static boolean esNumero(Object elemento) {
        NumberFormat numberFormat = NumberFormat.getInstance();

        try {
            numberFormat.parse(elemento.toString());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
