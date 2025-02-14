package webTests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class wikitest02 
{ 

    public static void main(String[] args) throws IOException, InterruptedException 
    {
        // 1. Crear cliente HTTP
        HttpClient client = HttpClient.newBuilder().build();

        // 2. Crear solicitud HTTP - URL 
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://es.wikipedia.org/wiki/Wikipedia:Bienvenidos"))
                .GET() // solicitud GET
                .build();

        // 3. Enviar solicitud y recibir respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 4. Analizar código de estado
        int statusCode = response.statusCode();
        System.out.println("Código de estado correcto: " + statusCode);
        if (statusCode != 200) 
        {
            System.out.println("Error al obtener la página Wikipedia:Bienvenidos");
            return;
        }

        // 5. Obtener cuerpo de la respuesta (HTML)
        String html = response.body();

        // 6. Buscar título de la página con expresión regular 
        Pattern titlePattern = Pattern.compile("<title>(.*?)</title>"); 
        Matcher titleMatcher = titlePattern.matcher(html);

        if (titleMatcher.find())
        {
            String title = titleMatcher.group(1);
            System.out.println("El título de la página es: " + title);
        } else 
        {
            System.out.println("ERROR: falta el título de la página");
        }

        // 7. Buscar número de enlaces (<a>) en la página 
        Pattern linkPattern = Pattern.compile("<a href=\"(.*?)\""); // Patrón para enlaces con href
        Matcher linkMatcher = linkPattern.matcher(html);

        int linkCount = 0;
        while (linkMatcher.find()) 
        {
            linkCount++;
        }
        System.out.println("Número de enlaces (con href): " + linkCount);

        // 8. Buscar texto específico en la página
        String textoBusqueda = "Te damos la bienvenida"; // Texto a buscar
        if (html.contains(textoBusqueda)) 
        {
            System.out.println("Se encontró el texto: " + textoBusqueda);
        } else 
        {
            System.out.println("No se encontró el texto: " + textoBusqueda);
        }

        // 9. Buscar elementos específicos por ID 
        String idElemento = "mw-content-text"; // ID de un elemento
        if (html.contains("id=\"" + idElemento + "\"")) 
        {
            System.out.println("Se encontró el elemento con ID: " + idElemento);
        } else 
        {
            System.out.println("No se encontró el elemento con ID: " + idElemento);
        }
    }
}