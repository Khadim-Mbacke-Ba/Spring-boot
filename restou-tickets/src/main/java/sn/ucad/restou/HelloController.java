package sn.ucad.restou;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String hello() {
        return "Bienvenue sur RestoU -Tickets !";
    }

    @GetMapping("/api/hello")
    public String helloApi() {
        return "Hello from SpringBoot API !";
    }

    @GetMapping("/api/info")
    public String infoApi() {
        return "Application : RestoU-Tickets | Version : 1.0 | Auteur : [Serigne khadim mbacke ba]";
    }

    @GetMapping("/api/date")
    public String dateApi() {
        return "Voila la Date et l'heure : " + java.time.LocalDateTime.now();
    }
}
