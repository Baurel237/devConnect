package petite_appli.appli.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String authorName; // nom de l'auteur du post
    private int commentCount;  // nombre de commentaires
}