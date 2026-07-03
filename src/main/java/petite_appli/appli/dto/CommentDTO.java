package petite_appli.appli.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String text;
    private String authorName;
    private String postTitle;  //pour le post commenter
    private Long postId;  //id post commenter
}
