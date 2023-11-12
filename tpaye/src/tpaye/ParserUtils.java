package tpaye;

class ParserUtils {
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
}