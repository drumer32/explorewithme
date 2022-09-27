package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.event.EventFullDto;
import com.drumer32.explorewithme.model.exception.ConflictException;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    EventFullDto leaveComment(Integer eventId, Integer userId, String text) throws ObjectNotFoundException, ConflictException;

    void deleteComment(Integer eventId, Integer userId, Integer commentId) throws ObjectNotFoundException, ConflictException;

    void dislikeComment(Integer commentId, Integer userId) throws ConflictException, ObjectNotFoundException;

    void likeComment(Integer commentId, Integer userId) throws ConflictException, ObjectNotFoundException;

    EventFullDto changeComment(String text, Integer userId, Integer commentId) throws ObjectNotFoundException, ConflictException;
}
