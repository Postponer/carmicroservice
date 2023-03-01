package ua.com.foxminded.carmicroservice.daolayer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.carmicroservice.models.User;


@Repository
public interface UserDAO extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}