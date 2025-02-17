package webTests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SuiteDePruebas 
{

    public static void main(String[] args) 
    {
        int pruebasRealizadas = 0;
        int pruebasFallidas = 0;
        int pruebasCorrectas = 0;

        try {
            // Execute tests and capture results and output
            ResultadoPrueba resultado01 = ejecutarPruebas(wikitest01.class);
            ResultadoPrueba resultado02 = ejecutarPruebas(wikitest02.class);
            ResultadoPrueba resultado03 = ejecutarPruebas(wikitest03.class);
            ResultadoPrueba resultado04 = ejecutarPruebas(wikitest04.class);
            ResultadoPrueba resultado05 = ejecutarPruebas(wikitest05.class); 
            ResultadoPrueba resultado06 = ejecutarPruebas(wikitest06.class); 
            ResultadoPrueba resultado07 = ejecutarPruebas(wikitest06.class);

            // Print the output of each test
            System.out.println(resultado01.salida); // Print output 
            System.out.println(resultado02.salida);
            System.out.println(resultado03.salida);
            System.out.println(resultado04.salida);
            System.out.println(resultado05.salida); 
            System.out.println(resultado06.salida);
            System.out.println(resultado07.salida);

            // Add up the results
            pruebasRealizadas = resultado01.pruebasRealizadas + resultado02.pruebasRealizadas + resultado03.pruebasRealizadas + resultado04.pruebasRealizadas + resultado05.pruebasRealizadas + resultado06.pruebasRealizadas + resultado07.pruebasRealizadas;
            pruebasFallidas = resultado01.pruebasFallidas + resultado02.pruebasFallidas + resultado03.pruebasFallidas + resultado04.pruebasFallidas + resultado05.pruebasFallidas + resultado06.pruebasFallidas + resultado07.pruebasFallidas;
            pruebasCorrectas = pruebasRealizadas - pruebasFallidas;

        } catch (Exception e) 
        {
            e.printStackTrace();
        }

        // Display results summary
        System.out.println("\n--- Test Summary ---");
        System.out.println("Tests executed: " + pruebasRealizadas);
        System.out.print("Correct tests: ");
        printColor(pruebasCorrectas + "\n", "green");
        System.out.print("Failed tests: ");
        printColor(pruebasFallidas + "\n", "red");
    }

    // Class to store the results and output of a test
    static class ResultadoPrueba 
    {
        int pruebasRealizadas;
        int pruebasFallidas;
        String salida;

        public ResultadoPrueba(int pruebasRealizadas, int pruebasFallidas, String salida) {
            this.pruebasRealizadas = pruebasRealizadas;
            this.pruebasFallidas = pruebasFallidas;
            this.salida = salida;
        }
    }
    // Method to execute the tests of a class and capture the results and output
    public static ResultadoPrueba ejecutarPruebas(Class<?> clase) {
        int pruebasRealizadas = 0;
        int pruebasFallidas = 0;
        String salida = "";

        try {
            // Capture console output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream originalOut = System.out;
            System.setOut(ps);

            // Execute the main method of the test class
            clase.getMethod("main", String[].class).invoke(null, new Object[] { null });

            // Restore console output
            System.setOut(originalOut);

            // Get the output and analyze it to count tests
            salida = baos.toString();
            pruebasRealizadas = contarPruebasRealizadas(salida);
            pruebasFallidas = contarPruebasFallidas(salida);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResultadoPrueba(pruebasRealizadas, pruebasFallidas, salida);
    }

    // Methods to count tests (no changes)
    public static int contarPruebasRealizadas(String consola) 
    {
        return consola.split("Resultado:").length - 1;
    }

    public static int contarPruebasFallidas(String consola) 
    {
        return consola.split("ERROR").length - 1;
    }

    // Method to print text with color
    public static void printColor(String text, String color) 
    {
        switch (color) 
        {
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