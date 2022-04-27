package co.com.viveres.susy.microserviceuser.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.viveres.susy.microserviceuser.entity.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsername(String username);

}
