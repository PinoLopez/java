package webTests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class wikitest10 {
    private static final String URL = "https://en.wikipedia.org/wiki/Technics_SL-1200";

    public static void main(String[] args) throws IOException, InterruptedException {
        pruebaCodigoDeEstado();
        pruebaTituloDeLaPagina();
        pruebaNumeroDeEnlaces();
        pruebaTextoEspecifico("turntable");
        pruebaTextoEspecifico("direct-drive");
        pruebaElementoPorId("firstHeading");
    }

    public static void pruebaCodigoDeEstado() throws IOException, InterruptedException {
        int statusCode = obtenerCodigoDeEstado(URL);
        verificarCondicion(statusCode == 200, "Código de estado 200", statusCode);
    }

    public static void pruebaTituloDeLaPagina() throws IOException, InterruptedException {
        String titulo = obtenerTituloDeLaPagina(URL);
        verificarCondicion(titulo != null && titulo.equals("Technics SL-1200 - Wikipedia"), "Título correcto", titulo);
    }

    public static void pruebaNumeroDeEnlaces() throws IOException, InterruptedException {
        int numeroDeEnlaces = obtenerNumeroDeEnlaces(URL);
        verificarCondicion(numeroDeEnlaces > 50, "Más de 50 enlaces", numeroDeEnlaces);
    }

    public static void pruebaTextoEspecifico(String texto) {
        String html = obtenerHtml(URL);
        verificarCondicion(html.contains(texto), "Contiene texto '" + texto + "'", texto);
    }

    public static void pruebaElementoPorId(String id) {
        String html = obtenerHtml(URL);
        verificarCondicion(html.contains("id=\"" + id + "\""), "Contiene ID '" + id + "'", id);
    }

    public static int obtenerCodigoDeEstado(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    public static String obtenerTituloDeLaPagina(String url) throws IOException, InterruptedException {
        String html = obtenerHtml(url);
        Pattern pattern = Pattern.compile("<title>(.*?)</title>");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public static int obtenerNumeroDeEnlaces(String url) throws IOException, InterruptedException {
        String html = obtenerHtml(url);
        Pattern linkPattern = Pattern.compile("<a href=\"(.*?)\"");
        Matcher linkMatcher = linkPattern.matcher(html);
        int linkCount = 0;
        while (linkMatcher.find()) {
            linkCount++;
        }
        return linkCount;
    }

    public static String obtenerHtml(String url) {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void verificarCondicion(boolean condicion, String descripcion, Object valorEsperado) {
        System.out.println("Verificando: " + descripcion);
        System.out.print("Resultado: ");
        if (condicion) {
            printColor("OK\n", "verde");
        } else {
            printColor("ERROR\n", "rojo");
            System.out.println("Valor obtenido: " + valorEsperado);
        }
    }

    public static void printColor(String text, String color) {
        switch (color) {
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