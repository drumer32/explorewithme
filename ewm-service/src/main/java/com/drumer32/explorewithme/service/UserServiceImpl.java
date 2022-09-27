package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.model.user.User;
import com.drumer32.explorewithme.storage.UserStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Override
    public List<User> getAllByIdWithPageable(List<Integer> userId, Integer from, Integer size) throws ObjectNotFoundException {
        log.info("Запрос на получение пользователей {}", userId);
        List<User> users = new ArrayList<>();
        for (Integer id : userId) {
            users.add(get(id));
        }
        PagedListHolder<User> page = new PagedListHolder<>(users);
        page.setPageSize(size);
        page.setPage(from);
        return page.getPageList();
    }

    @Override
    public User get(Integer id) throws ObjectNotFoundException {
        log.info("Запрос на получение пользователя с id - {}", id);
        try {
             return userStorage.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
    }

    @Override
    @Transactional
    public User save(User user) {
        log.info("Запрос на сохранение пользователя - {}", user.getEmail());
        return userStorage.save(user);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws ObjectNotFoundException {
        log.info("Запрос на удаление пользователя с id - {}", id);
        userStorage.delete(get(id));
    }
}
