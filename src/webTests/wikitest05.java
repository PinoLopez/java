package webTests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class wikitest05 
{
    public static void main(String[] args) throws IOException, InterruptedException 
    {
        pruebaCodigoDeEstado();
        pruebaTituloDeLaPagina();
        pruebaNumeroDeEnlaces();
        pruebaTextoEspecifico();
        pruebaElementoPorId();
    }
    public static void pruebaCodigoDeEstado() throws IOException, InterruptedException 
    {
        String descripcion = "Verificar que el código de estado de la página sea 200 (OK)";
        int statusCode = obtenerCodigoDeEstado("https://es.wikipedia.org/wiki/Wikipedia:Bienvenidos#Cómo_puedes_colaborar");
        System.out.println(descripcion);
        System.out.print("Resultado: ");
        if (statusCode == 200) 
        {
            printColor("OK\n", "verde");
        } else 
        {
            printColor("ERROR\n", "rojo");
            System.out.println("Código de estado obtenido: " + statusCode);
        }
    }
    public static void pruebaTituloDeLaPagina() throws IOException, InterruptedException 
    {
        String descripcion = "Verificar que el título de la página sea Wikipedia:Bienvenidos - Wikipedia, la enciclopedia libre";
        String titulo = obtenerTituloDeLaPagina("https://es.wikipedia.org/wiki/Wikipedia:Bienvenidos#Cómo_puedes_colaborar");
        System.out.println(descripcion);
        System.out.print("Resultado: ");
        if (titulo != null && !titulo.isEmpty() && titulo.equals("Wikipedia:Bienvenidos - Wikipedia, la enciclopedia libre")) 
        {
            printColor("OK\n", "verde");
            System.out.println("Título obtenido: " + titulo);
        } else 
        {
            printColor("ERROR\n", "rojo");
            System.out.println("Título obtenido: " + titulo);
        }
    }
    public static void pruebaNumeroDeEnlaces() throws IOException, InterruptedException 
    {
        String descripcion = "Verificar que la página tenga al menos 100 enlaces";
        int numeroDeEnlaces = obtenerNumeroDeEnlaces("https://es.wikipedia.org/wiki/Wikipedia:Bienvenidos#Cómo_puedes_colaborar");
        System.out.println(descripcion);
        System.out.print("Resultado: ");
        if (numeroDeEnlaces > 100) 
        {
            printColor("OK\n", "verde");
            System.out.println("Número de enlaces: " + numeroDeEnlaces);
        } else 
        {
            printColor("ERROR\n", "rojo");
            System.out.println("Número de enlaces: " + numeroDeEnlaces);
        }
    }
    public static void pruebaTextoEspecifico() 
    {
        String descripcion = "Verificar que la sección 'Cómo puedes colaborar' contenga el texto 'Sé valiente al editar páginas'";
        String html = obtenerHtml("https://es.wikipedia.org/wiki/Wikipedia:Bienvenidos#Cómo_puedes_colaborar");
        System.out.println(descripcion);
        System.out.print("Resultado: ");
        if (html.contains("Sé valiente al editar páginas")) 
        {
            printColor("OK\n", "verde");
        } else {
            printColor("ERROR\n", "rojo");
        }
    }
    public static void pruebaElementoPorId() 
    {
        String descripcion = "Verificar que la página contenga un elemento con ID 'Cómo_puedes_colaborar'";
        String html = obtenerHtml("https://es.wikipedia.org/wiki/Wikipedia:Bienvenidos#Cómo_puedes_colaborar");
        System.out.println(descripcion);
        System.out.print("Resultado: ");
        if (html.contains("id=\"Cómo_puedes_colaborar\"")) 
        {
            printColor("OK\n", "verde");
        } else 
        {
            printColor("ERROR\n", "rojo");
        }
    }
    public static int obtenerCodigoDeEstado(String url) throws IOException, InterruptedException 
    {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }
    public static String obtenerTituloDeLaPagina(String url) throws IOException, InterruptedException 
    {
        String html = obtenerHtml(url);
        Pattern pattern = Pattern.compile("<title>(.*?)</title>");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        } else 
        {
            return null;
        }
    }
    public static int obtenerNumeroDeEnlaces(String url) throws IOException, InterruptedException 
    {
        String html = obtenerHtml(url);
        Pattern linkPattern = Pattern.compile("<a href=\"(.*?)\"");
        Matcher linkMatcher = linkPattern.matcher(html);
        int linkCount = 0;
        while (linkMatcher.find()) 
        {
            linkCount++;
        }
        return linkCount;
    }
    public static String obtenerHtml(String url) 
    {
        try 
        {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) 
        {
            e.printStackTrace();
            return ""; // Devuelve una cadena vacía en caso de error
        }
    }
    public static void printColor(String text, String color) 
    {
        switch (color) 
        {
            case "rojo":
                System.out.print("\u001B[31m" + text + "\u001B[0m");
                break;
            case "verde":
                System.out.print("\u001B[32m" + text + "\u001B[0m");
                break;
            default:
                System.out.print(text);
        }
    }
}