package petite_appli.appli.repository;

import petite_appli.appli.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Trouve tous les commentaires d'un post
    // Spring génère automatiquement la requête SQL à partir du nom de la méthode
    List<Comment> findByPostId(Long postId);

    // Trouve tous les commentaires contenant un mot clé
    // @Query permet d'écrire du HQL (Hibernate Query Language)
    @Query("SELECT c FROM Comment c WHERE c.text LIKE %:keyword%")
    List<Comment> searchByKeyword(@Param("keyword") String keyword);

    // Compte le nombre de commentaires d'un post
    long countByPostId(Long postId);
}