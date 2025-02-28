package webTests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SuiteDePruebas {

    // Método para mostrar el resultado de una prueba individual (MOVIDO ARRIBA)
    public static void mostrarResultadoPrueba(ResultadoPrueba resultado, String nombrePrueba) {
        System.out.println("\n--- Resultado de " + nombrePrueba + " ---");
        System.out.println(resultado.salida);
    }

    public static void main(String[] args) {
        int pruebasRealizadas = 0;
        int pruebasFallidas = 0;
        int pruebasCorrectas = 0;

        try {
            // Ejecutar pruebas y mostrar resultados individuales
            ResultadoPrueba resultado01 = ejecutarPruebas(wikitest01.class);
            mostrarResultadoPrueba(resultado01, "wikitest01");
            pruebasRealizadas += resultado01.pruebasRealizadas;
            pruebasFallidas += resultado01.pruebasFallidas;

            ResultadoPrueba resultado02 = ejecutarPruebas(wikitest02.class);
            mostrarResultadoPrueba(resultado02, "wikitest02");
            pruebasRealizadas += resultado02.pruebasRealizadas;
            pruebasFallidas += resultado02.pruebasFallidas;

            ResultadoPrueba resultado03 = ejecutarPruebas(wikitest03.class);
            mostrarResultadoPrueba(resultado03, "wikitest03");
            pruebasRealizadas += resultado03.pruebasRealizadas;
            pruebasFallidas += resultado03.pruebasFallidas;

            ResultadoPrueba resultado04 = ejecutarPruebas(wikitest04.class);
            mostrarResultadoPrueba(resultado04, "wikitest04");
            pruebasRealizadas += resultado04.pruebasRealizadas;
            pruebasFallidas += resultado04.pruebasFallidas;

            ResultadoPrueba resultado05 = ejecutarPruebas(wikitest05.class);
            mostrarResultadoPrueba(resultado05, "wikitest05");
            pruebasRealizadas += resultado05.pruebasRealizadas;
            pruebasFallidas += resultado05.pruebasFallidas;

            ResultadoPrueba resultado06 = ejecutarPruebas(wikitest06.class);
            mostrarResultadoPrueba(resultado06, "wikitest06");
            pruebasRealizadas += resultado06.pruebasRealizadas;
            pruebasFallidas += resultado06.pruebasFallidas;

            ResultadoPrueba resultado07 = ejecutarPruebas(wikitest07.class);
            mostrarResultadoPrueba(resultado07, "wikitest07");
            pruebasRealizadas += resultado07.pruebasRealizadas;
            pruebasFallidas += resultado07.pruebasFallidas;

            ResultadoPrueba resultado08 = ejecutarPruebas(wikitest08.class);
            mostrarResultadoPrueba(resultado08, "wikitest08");
            pruebasRealizadas += resultado08.pruebasRealizadas;
            pruebasFallidas += resultado08.pruebasFallidas;

            ResultadoPrueba resultado09 = ejecutarPruebas(wikitest09.class);
            mostrarResultadoPrueba(resultado09, "wikitest09");
            pruebasRealizadas += resultado09.pruebasRealizadas;
            pruebasFallidas += resultado09.pruebasFallidas;

            ResultadoPrueba resultado10 = ejecutarPruebas(wikitest10.class);
            mostrarResultadoPrueba(resultado10, "wikitest10");
            pruebasRealizadas += resultado10.pruebasRealizadas;
            pruebasFallidas += resultado10.pruebasFallidas;

            ResultadoPrueba resultado11 = ejecutarPruebas(wikitest11.class);
            mostrarResultadoPrueba(resultado11, "wikitest11");
            pruebasRealizadas += resultado11.pruebasRealizadas;
            pruebasFallidas += resultado11.pruebasFallidas;

            ResultadoPrueba resultado12 = ejecutarPruebas(wikitest12.class);
            mostrarResultadoPrueba(resultado12, "wikitest12");
            pruebasRealizadas += resultado12.pruebasRealizadas;
            pruebasFallidas += resultado12.pruebasFallidas;

            ResultadoPrueba resultado13 = ejecutarPruebas(wikitest13.class);
            mostrarResultadoPrueba(resultado13, "wikitest13");
            pruebasRealizadas += resultado13.pruebasRealizadas;
            pruebasFallidas += resultado13.pruebasFallidas;

            ResultadoPrueba resultado14 = ejecutarPruebas(wikitest14.class);
            mostrarResultadoPrueba(resultado14, "wikitest14");
            pruebasRealizadas += resultado14.pruebasRealizadas;
            pruebasFallidas += resultado14.pruebasFallidas;

            ResultadoPrueba resultado15 = ejecutarPruebas(wikitest15.class);
            mostrarResultadoPrueba(resultado15, "wikitest15");
            pruebasRealizadas += resultado15.pruebasRealizadas;
            pruebasFallidas += resultado15.pruebasFallidas;

            pruebasCorrectas = pruebasRealizadas - pruebasFallidas;

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mostrar resumen final
        System.out.println("\n--- Resumen de Pruebas ---");
        System.out.println("Pruebas ejecutadas: " + pruebasRealizadas);
        System.out.print("Pruebas correctas: ");
        printColor(pruebasCorrectas + "\n", "green");
        System.out.print("Pruebas fallidas: ");
        printColor(pruebasFallidas + "\n", "red");
    }

    // Clase para almacenar los resultados y la salida de una prueba
    static class ResultadoPrueba {
        int pruebasRealizadas;
        int pruebasFallidas;
        String salida;

        public ResultadoPrueba(int pruebasRealizadas, int pruebasFallidas, String salida) {
            this.pruebasRealizadas = pruebasRealizadas;
            this.pruebasFallidas = pruebasFallidas;
            this.salida = salida;
        }
    }

    // Método para ejecutar las pruebas de una clase y capturar los resultados y la salida
    public static ResultadoPrueba ejecutarPruebas(Class<?> clase) {
        int pruebasRealizadas = 0;
        int pruebasFallidas = 0;
        String salida = "";

        try {
            // Capturar la salida de la consola
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream originalOut = System.out;
            System.setOut(ps);

            // Ejecutar el método main de la clase de prueba
            clase.getMethod("main", String[].class).invoke(null, new Object[]{null});

            // Restaurar la salida de la consola
            System.setOut(originalOut);

            // Obtener la salida y analizarla para contar las pruebas
            salida = baos.toString();
            pruebasRealizadas = contarPruebasRealizadas(salida);
            pruebasFallidas = contarPruebasFallidas(salida);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResultadoPrueba(pruebasRealizadas, pruebasFallidas, salida);
    }

    // Métodos para contar pruebas (sin cambios)
    public static int contarPruebasRealizadas(String consola) {
        return consola.split("Resultado:").length - 1;
    }

    public static int contarPruebasFallidas(String consola) {
        return consola.split("ERROR").length - 1;
    }

    // Método para imprimir texto con color
    public static void printColor(String text, String color) {
        switch (color) {
            case "red":
                System.out.print("\u001B[31m" + text + "\u001B[0m");
                break;
            case "green":
                System.out.print("\u001B[32m" + text + "\u001B[0m");
                break;
            default:
                System.out.print(text);
        }
    }
}