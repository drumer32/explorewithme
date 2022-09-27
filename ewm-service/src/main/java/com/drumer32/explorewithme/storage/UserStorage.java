package com.drumer32.explorewithme.storage;

import com.drumer32.explorewithme.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStorage extends JpaRepository<User, Integer> {
}
