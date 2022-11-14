package ru.timotege.vk.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import ru.timotege.vk.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}