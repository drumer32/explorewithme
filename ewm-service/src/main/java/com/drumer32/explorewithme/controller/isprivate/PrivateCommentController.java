package com.drumer32.explorewithme.controller.isprivate;

import com.drumer32.explorewithme.model.event.EventFullDto;
import com.drumer32.explorewithme.model.exception.BadConditionException;
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
    public EventFullDto createComment(@PathVariable Integer eventId,
                                     @RequestParam Integer userId,
                                     @RequestParam String text) throws ObjectNotFoundException, ConflictException, BadConditionException {
        return commentService.createComment(eventId, userId, text);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@RequestParam Integer eventId,
                              @RequestParam Integer userId,
                              @PathVariable Integer commentId) throws ObjectNotFoundException, ConflictException, BadConditionException {
        commentService.deleteComment(eventId, userId, commentId);
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    public EventFullDto changeComment(@RequestParam String text,
                                      @PathVariable Integer userId,
                                      @PathVariable Integer commentId) throws ConflictException, ObjectNotFoundException {
        return commentService.changeComment(text, userId, commentId);
    }

    @PatchMapping("/comments/{commentId}/like")
    public void likeComment(@RequestParam Integer userId,
                            @PathVariable Integer commentId) throws ConflictException, ObjectNotFoundException {
        commentService.likeComment(commentId, userId);
    }

    @PatchMapping("/comments/{commentId}/dislike")
    public void dislikeComment(@RequestParam Integer userId,
                               @PathVariable Integer commentId) throws ConflictException, ObjectNotFoundException {
        commentService.dislikeComment(commentId, userId);
    }
}
