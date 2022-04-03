package com.bamdoliro.stupetition.domain.user.domain.repository;

import com.bamdoliro.stupetition.domain.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
