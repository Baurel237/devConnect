package petite_appli.appli.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Dit à Spring de retourner un 404 quand cette exception est lancée
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Constructeur pratique : "User non trouvé avec l'id : 5"
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " non trouvé avec l'id : " + id);
    }
}