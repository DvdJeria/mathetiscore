package com.mathetiscore.api.domain.port;

import java.util.Optional;
import java.util.UUID;
import com.mathetiscore.api.domain.model.User;

public interface UserRepositoryPort {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    User save(User user);
}
