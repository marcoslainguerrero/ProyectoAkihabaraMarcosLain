package test;
import service.LlmService;

import org.junit.Test;
import static org.junit.Assert.*;

public class LlmServiceTest {

    @Test
    public void testConsultarLLM_respuestaNoVacia() {
        LlmService service = new LlmService();

        // Prompt simple para la prueba
        String prompt = "Hola, ¿Qué IA estoy utilizando?";

        // Llamar al método real (requiere API key válida en ConfigLoader)
        String respuesta = service.consultarLLM(prompt);

        assertNotNull(respuesta);  // sin mensaje
        assertFalse(respuesta.trim().isEmpty());


        System.out.println("Respuesta LLM: " + respuesta);
    }
}
