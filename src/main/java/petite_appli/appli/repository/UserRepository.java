package petite_appli.appli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petite_appli.appli.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}