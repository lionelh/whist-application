package be.lionelh.whist.score.backend.data.dao;

import be.lionelh.whist.score.backend.data.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDAO extends JpaRepository<Role, Long> {

    boolean existsRoleByName(String name);

    Optional<Role> findRoleByNameEqualsIgnoreCase(String inName);
}
