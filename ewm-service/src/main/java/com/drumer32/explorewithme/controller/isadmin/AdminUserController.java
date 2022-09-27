package com.drumer32.explorewithme.controller.isadmin;

import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.model.user.User;
import com.drumer32.explorewithme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class AdminUserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@Validated @RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping
    Collection<User> getAll(@RequestParam(value = "ids") List<Integer> userId,
                            @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                            @RequestParam(value = "size", required = false, defaultValue = "10") int size) throws ObjectNotFoundException {
        return userService.getAllByIdWithPageable(userId, from, size);
    }

    @DeleteMapping(value = {"/users/{userId}"})
    public void deleteUser(@PathVariable Integer userId) throws ObjectNotFoundException {
        userService.delete(userId);
    }
}
