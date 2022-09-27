package com.drumer32.explorewithme.controller.isprivate;

import com.drumer32.explorewithme.model.event.EventFullDto;
import com.drumer32.explorewithme.model.exception.ConflictException;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/events")
@AllArgsConstructor
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping("/{eventId}/comments")
    public EventFullDto leaveComment(@PathVariable Integer eventId,
                                     @RequestParam Integer userId,
                                     @RequestParam String text) throws ObjectNotFoundException, ConflictException {
        return commentService.leaveComment(eventId, userId, text);
    }

    @DeleteMapping("/{eventId}/comments/{commentId}")
    public void deleteComment(@PathVariable Integer eventId,
                              @RequestParam Integer userId,
                              @PathVariable Integer commentId) throws ObjectNotFoundException, ConflictException {
        commentService.deleteComment(eventId, userId, commentId);
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    public EventFullDto changeComment(@RequestParam String text,
                                      @PathVariable Integer userId,
                                      @PathVariable Integer commentId) throws ConflictException, ObjectNotFoundException {
        return commentService.changeComment(text, userId, commentId);
    }

    @PatchMapping("/{userId}/comments/{commentId}/like")
    public void likeComment(@PathVariable Integer userId,
                            @PathVariable Integer commentId) throws ConflictException, ObjectNotFoundException {
        commentService.likeComment(commentId, userId);
    }

    @PatchMapping("/{userId}/comments/{commentId}/dislike")
    public void dislikeComment(@PathVariable Integer userId,
                               @PathVariable Integer commentId) throws ConflictException, ObjectNotFoundException {
        commentService.dislikeComment(commentId, userId);
    }
}
