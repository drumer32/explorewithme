package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllByIdWithPageable(List<Integer> userId, Integer from, Integer size) throws ObjectNotFoundException;

    User get(Integer id) throws ObjectNotFoundException;

    User save(User user);

    void delete(Integer id) throws ObjectNotFoundException;
}
