package petite_appli.appli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petite_appli.appli.entity.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
