package petite_appli.appli.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private int status;           // code HTTP (404, 400, 500...)
    private String error;         // type d'erreur
    private String message;       // message lisible
    private String path;          // l'URL qui a causé l'erreur
    private LocalDateTime timestamp; // quand c'est arrivé

    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}