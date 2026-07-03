package petite_appli.appli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petite_appli.appli.entity.Profile;

public interface ProfilRepository extends JpaRepository<Profile, Long> {
}
