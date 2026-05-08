package sn.ucad.restou.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<FieldError> details;

    // Constructeur sans détails
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Constructeur avec détails (validation champs)
    public ErrorResponse(int status, String error, String message, String path, List<FieldError> details) {
        this(status, error, message, path);
        this.details = details;
    }

    // Classe interne pour les erreurs de champs
    public static class FieldError {

        private String champ;
        private String message;

        public FieldError(String champ, String message) {
            this.champ = champ;
            this.message = message;
        }

        public String getChamp() {
            return champ;
        }

        public String getMessage() {
            return message;
        }
    }

    // Getters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public List<FieldError> getDetails() {
        return details;
    }
}