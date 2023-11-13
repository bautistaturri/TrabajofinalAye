package tpaye;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            DataFrame miMatriz = new DataFrame();
            
            miMatriz = CSVparser.leerCSV("C:\\Users\\M\\Downloads\\hola.csv");
            VisualizadorDatos visualizador = new VisualizadorDatos(miMatriz);
            
            System.out.println("Bienvenido");
            
            visualizador.visualizarDatos();
            System.out.println(); //salto de linea
            
            int CantDeFilas = miMatriz.cantDeFilas();
            int CantDeColumnas = miMatriz.cantDeColumnas();

            //%%%%%%%%%%%%%% PRUEBO LA FUNCIONALIDAD VisualizarDATOS %%%%%%%%%%%%%%    
  
            
            

            // Uso la clase Fila:
            int filaDeseada = 0;  // El índice de la tercera fila (los índices comienzan en 0 SIN CONTAR LOS ENCABEZADOS)
            List<Object> celdas = new ArrayList<>();
            for (int j = 0; j < miMatriz.cantDeColumnas(); j++) {
                celdas.add(miMatriz.getElemento(filaDeseada, j));
            }

            //%%%%%%%%%%%%%% PRUEBO LA FUNCIONALIDAD Fila %%%%%%%%%%%%%%
            Fila fila = new Fila(celdas);

            // Imprimir la tercera fila
            System.out.println("La fila solicitada es: " + fila.getCeldas());

            miMatriz.SeleccionarFilas(new int[] { 1, 3 });
            miMatriz.SeleccionarColumnas(new int[] { 1, 3 });

        } catch (CSVInvalidException e) {
            // Manejar excepción de CSV inválido
            e.printStackTrace();
        }
    }
}






















    /*
    System.out.println("El nro. de filas es: " + CantDeFilas);
    System.out.println("Las etiquetas de las columnas son: " + miMatriz.etiquetaDeColumnas());
    System.out.println("El nro. de columnas es: " + CantDeColumnas);

    if (miMatriz != null) {
        int filaDeseada = 0;  // Cambia aquí el índice de la fila deseada
        int columnaDeseada = 0;  // Cambia aquí el índice de la columna deseada

        if (filaDeseada >= 0 && filaDeseada < CantDeFilas && columnaDeseada >= 0 && columnaDeseada < CantDeColumnas) {
            Object elemento = miMatriz.getElemento(filaDeseada, columnaDeseada);
            if (elemento != null) {
                System.out.println("En la posición especificada tenemos: " + elemento);
            } else {
                System.out.println("No se encontró ninguna fila en la posición especificada.");
            }
        } else {
            System.out.println("Índices de fila y/o columna fuera de los límites.");
        }
    }

    // SALIDAS FUNCIONALES:
    System.out.println("Los tipos de datos de las columnas son:");
    List<String> tiposDeDatos = miMatriz != null ? miMatriz.obtenerTiposDeDatosColumnas() : null;

    if (tiposDeDatos != null) {
        for (int i = 0; i < tiposDeDatos.size(); i++) {
            String tipoDato = tiposDeDatos.get(i);
            if (tipoDato != null) {
                int lastIndex = tipoDato.lastIndexOf('.');
                if (lastIndex != -1) {
                    tipoDato = tipoDato.substring(lastIndex + 1);
                } else {
                    tipoDato = "NA";
                }
                System.out.println("El tipo de dato de la columna " + i + " es " + tipoDato);
            }
        }
    }
*/




















