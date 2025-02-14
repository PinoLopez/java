package webTests;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Pattern; 
import java.util.regex.Matcher; 

public class wikitest01 
{

    public static void main(String[] args) throws IOException, InterruptedException 
    {
        // 1. Crear cliente HTTP
        HttpClient client = HttpClient.newBuilder().build();

        // 2. Crear solicitud HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://es.wikipedia.org/wiki/Wikipedia"))
                .GET() // solicitud GET
                .build();

        // 3. Enviar solicitud y recibir respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 4. Analizar código de estado
        int statusCode = response.statusCode();
        System.out.println("Código de estado: " + statusCode);
        if (statusCode != 200) 
        {
            System.out.println("Error al obtener la página Wikipedia");
            return;
        }

        // 5. Obtener cuerpo de la respuesta (HTML)
        String html = response.body();

        // 6. Buscar título de la página con expresión regular
        Pattern pattern = Pattern.compile("<title>(.*?)</title>");
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) 
        {
            String title = matcher.group(1);
            System.out.println("El título de la página es: " + title);
        } else {
            System.out.println("ERROR: No se encontró el título de la página");
        }

        // 7. Buscar número de enlaces (<a>) en la página
        Pattern linkPattern = Pattern.compile("<a href=\"(.*?)\">");
        Matcher linkMatcher = linkPattern.matcher(html);

        int linkCount = 0;
        while (linkMatcher.find()) {
            linkCount++;
        }
        System.out.println("Número de enlaces: " + linkCount);
    }
}