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

        try 
        {
            // Ejecutar pruebas y capturar resultados y salida
            ResultadoPrueba resultado01 = ejecutarPruebas(wikitest01.class);
            ResultadoPrueba resultado02 = ejecutarPruebas(wikitest02.class);
            ResultadoPrueba resultado03 = ejecutarPruebas(wikitest03.class);
            ResultadoPrueba resultado04 = ejecutarPruebas(wikitest04.class);

            // Imprimir la salida de cada prueba
            System.out.println(resultado01.salida);
            System.out.println(resultado02.salida);
            System.out.println(resultado03.salida);
            System.out.println(resultado04.salida);

            // Sumar resultados
            pruebasRealizadas = resultado01.pruebasRealizadas + resultado02.pruebasRealizadas + resultado03.pruebasRealizadas + resultado04.pruebasRealizadas;
            pruebasFallidas = resultado01.pruebasFallidas + resultado02.pruebasFallidas + resultado03.pruebasFallidas + resultado04.pruebasFallidas;
            pruebasCorrectas = pruebasRealizadas - pruebasFallidas;

        } catch (Exception e) 
        {
            e.printStackTrace();
        }

        // Mostrar resumen de resultados
        System.out.println("\n--- Resumen de pruebas ---");
        System.out.println("Pruebas realizadas: " + pruebasRealizadas);
        System.out.println("Pruebas correctas: " + pruebasCorrectas);
        System.out.println("Pruebas fallidas: " + pruebasFallidas);
    }

    // Clase para almacenar los resultados y la salida de una prueba
    static class ResultadoPrueba 
    {
        int pruebasRealizadas;
        int pruebasFallidas;
        String salida;

        public ResultadoPrueba(int pruebasRealizadas, int pruebasFallidas, String salida) 
        {
            this.pruebasRealizadas = pruebasRealizadas;
            this.pruebasFallidas = pruebasFallidas;
            this.salida = salida;
        }
    }

    // Método para ejecutar las pruebas de una clase y capturar los resultados y la salida
    public static ResultadoPrueba ejecutarPruebas(Class<?> clase) 
    {
        int pruebasRealizadas = 0;
        int pruebasFallidas = 0;
        String salida = "";

        try 
        {
            // Capturar la salida de la consola
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream originalOut = System.out;
            System.setOut(ps);

            // Ejecutar el método main de la clase de prueba
            clase.getMethod("main", String[].class).invoke(null, new Object[] { null });

            // Restaurar la salida de la consola
            System.setOut(originalOut);

            // Obtener la salida y analizarla para contar pruebas
            salida = baos.toString();
            pruebasRealizadas = contarPruebasRealizadas(salida);
            pruebasFallidas = contarPruebasFallidas(salida);

        } catch (Exception e) 
        {
            e.printStackTrace();
        }

        return new ResultadoPrueba(pruebasRealizadas, pruebasFallidas, salida);
    }

    // Métodos para contar pruebas (sin cambios)
    public static int contarPruebasRealizadas(String consola) 
    {
        return consola.split("Resultado:").length - 1;
    }

    public static int contarPruebasFallidas(String consola) 
    {
        return consola.split("ERROR").length - 1;
    }
}