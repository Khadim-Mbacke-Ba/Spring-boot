package sn.ucad.restou.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Tests d'intégration - Etudiant")
class EtudiantIntegrationTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .defaultHeaders(headers -> headers.setBasicAuth("test", "test"))
                .build();
    }

    /**
     * Extrait l'ID depuis une réponse JSON
     */
    private Long extractId(String json) {
        Pattern pattern = Pattern.compile("\"id\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return Long.parseLong(matcher.group(1));
        }

        throw new RuntimeException("ID non trouvé dans: " + json);
    }

    @Test
    @DisplayName("Scénario CRUD complet")
    void scenarioComplet_CRUD() {

        // 1. CREATE
        String json = """
                {
                    "nom": "Diallo",
                    "prenom": "Fatou",
                    "email": "fatou.diallo@ucad.edu.sn",
                    "numeroCarte": "ETU-2024-001"
                }
                """;

        String createResponse = restClient.post()
                .uri("/api/etudiants")
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .retrieve()
                .body(String.class);

        Long id = extractId(createResponse);
        assertTrue(createResponse.contains("Diallo"));

        // 2. READ
        String getResponse = restClient.get()
                .uri("/api/etudiants/" + id)
                .retrieve()
                .body(String.class);

        assertTrue(getResponse.contains("Diallo"));
        assertTrue(getResponse.contains("Fatou"));

        // 3. UPDATE
        String updateJson = """
                {
                    "nom": "Diallo",
                    "prenom": "Fatou Bintou",
                    "email": "fatou.diallo@ucad.edu.sn",
                    "numeroCarte": "ETU-2024-001"
                }
                """;

        String updateResponse = restClient.put()
                .uri("/api/etudiants/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updateJson)
                .retrieve()
                .body(String.class);

        assertTrue(updateResponse.contains("Fatou Bintou"));

        // 4. DELETE
        restClient.delete()
                .uri("/api/etudiants/" + id)
                .retrieve()
                .toBodilessEntity();

        // 5. VERIFY DELETION
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restClient.get()
                    .uri("/api/etudiants/" + id)
                    .retrieve()
                    .body(String.class);
        });
    }

    @Test
    @DisplayName("Email dupliqué retourne 409")
    void emailDuplique_retourne409() {

        // First student
        String json1 = """
                {
                    "nom": "Diallo",
                    "prenom": "Fatou",
                    "email": "fatou@ucad.edu.sn",
                    "numeroCarte": "ETU-2024-001"
                }
                """;

        restClient.post()
                .uri("/api/etudiants")
                .contentType(MediaType.APPLICATION_JSON)
                .body(json1)
                .retrieve()
                .body(String.class);

        // Second student with same email
        String json2 = """
                {
                    "nom": "Sow",
                    "prenom": "Aminata",
                    "email": "fatou@ucad.edu.sn",
                    "numeroCarte": "ETU-2024-002"
                }
                """;

        assertThrows(HttpClientErrorException.Conflict.class, () -> {
            restClient.post()
                    .uri("/api/etudiants")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(json2)
                    .retrieve()
                    .body(String.class);
        });
    }
}