package petite_appli.appli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petite_appli.appli.entity.Post;

public interface  PostRepository extends JpaRepository <Post, Long> {
}
