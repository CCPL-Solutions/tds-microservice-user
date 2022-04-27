package co.com.viveres.susy.microserviceuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.viveres.susy.microserviceuser.entity.RoleEntity;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

}
