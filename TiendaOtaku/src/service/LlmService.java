package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import config.ConfigLoader;

public class LlmService {
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions"; // URL de la API del LLM

    // Método para enviar un prompt al modelo de lenguaje y obtener la respuesta
    public String consultarLLM(String prompt) {
        try {
            // Obtener clave API y modelo desde archivo de configuración
            String apiKey = ConfigLoader.getApiKey();
            String model = ConfigLoader.getModel();

            if (apiKey == null || apiKey.isEmpty()) {
                throw new RuntimeException("API Key no configurada"); // Validar que exista la API key
            }

            // Construir el cuerpo JSON de la solicitud usando Gson
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", model); // Modelo a usar
            
            // Crear mensaje con rol "user" y contenido del prompt
            JsonObject message = new JsonObject();
            message.addProperty("role", "user");
            message.addProperty("content", prompt);
            
            // Construir array de mensajes, que solo contiene el mensaje creado
            JsonObject messagesArray = new JsonObject();
            messagesArray.add("messages", new JsonArray());
            messagesArray.get("messages").getAsJsonArray().add(message);
            
            // Agregar mensajes al cuerpo de la solicitud
            requestBody.add("messages", messagesArray.get("messages"));

            // Crear cliente HTTP y construir la solicitud POST con cabeceras necesarias
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .timeout(Duration.ofSeconds(30)) // Tiempo máximo de espera
                    .header("Content-Type", "application/json") // Tipo de contenido JSON
                    .header("Authorization", "Bearer " + apiKey) // Autenticación con Bearer token
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString())) // Cuerpo de la solicitud
                    .build();

            // Enviar la solicitud y obtener la respuesta como String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parsear la respuesta JSON para extraer el contenido generado por el modelo
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            return jsonResponse.getAsJsonArray("choices")           // Acceder al arreglo "choices"
                             .get(0)                                  // Tomar el primer elemento
                             .getAsJsonObject()
                             .getAsJsonObject("message")             // Obtener el objeto "message"
                             .get("content")                         // Obtener el contenido del mensaje
                             .getAsString();
        } catch (Exception e) {
            // En caso de error lanzar excepción con mensaje detallado
            throw new RuntimeException("Error al consultar el LLM: " + e.getMessage(), e);
        }
    }
}
