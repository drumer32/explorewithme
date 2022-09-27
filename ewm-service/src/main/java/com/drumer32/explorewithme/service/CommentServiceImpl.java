package com.drumer32.explorewithme.service;

import com.drumer32.explorewithme.model.comment.Comment;
import com.drumer32.explorewithme.model.event.Event;
import com.drumer32.explorewithme.model.event.EventFullDto;
import com.drumer32.explorewithme.model.exception.ConflictException;
import com.drumer32.explorewithme.model.exception.ObjectNotFoundException;
import com.drumer32.explorewithme.model.user.Status;
import com.drumer32.explorewithme.storage.CommentStorage;
import com.drumer32.explorewithme.tools.EventDtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final EventService eventService;
    private final CommentStorage commentStorage;
    private final EventDtoMapper mapper;
    private final UserService userService;

    private final int HOURS_LIMIT = 24; //Переменная задает, через сколько часов после публикации
                                        // нельзя удалить и редактировать комменты.
                                        //Не знаю, насколько это нужно в реальной жизни, но я решил с этим поиграть

    @Override
    @Transactional
    public EventFullDto leaveComment(Integer eventId, Integer userId, String text)
            throws ObjectNotFoundException, ConflictException {
        log.info("Запрос на комментарий. Пользователь {}. Событие {}.", userId, eventId);
        if (userService.get(userId).getStatus().equals(Status.NOTAUTHORIZED)) {
            throw new ConflictException("Оставлять комментарии могу только зарегистрированные пользователи");
        } else {
            Event event = eventService.get(eventId);    //Проверка на существование события
            Comment comment = Comment.builder()
                    .likes(0)
                    .created(LocalDateTime.now())
                    .author(userId)
                    .eventId(eventId)
                    .text(text)
                    .build();
            commentStorage.save(comment);               //сохраняем коммент в базу, у него генерится id
            event.getComments().add(comment.getId());   //сохраняем id коммента в тело события
            return mapper.eventToFullDto(event);
        }
    }

    @Override
    @Transactional
    public void deleteComment(Integer eventId, Integer userId, Integer commentId)
            throws ObjectNotFoundException, ConflictException {
        log.info("Запрос на удаление комментария {}. Пользователь {}. Событие {}.", commentId, userId, eventId);
        Comment comment = commentStorage.findById(commentId).orElseThrow(() -> new ObjectNotFoundException("Комментарий" +
                " не найден"));
        if (userService.get(userId).getStatus().equals(Status.ADMIN)) {
            //если ты админ, то без лишних вопросов можешь удалить комментарий
            eventService.get(eventId).getComments().remove(commentId);
            commentStorage.deleteById(commentId);
        } else {
            if (LocalDateTime.now().isAfter(comment.getCreated().plusHours(HOURS_LIMIT))) {
                throw new ConflictException("Удалить комментарий можно только в течение " + HOURS_LIMIT
                        + " часов после публикации");
            } else {
                if (!comment.getAuthor().equals(userId)) {
                    throw new ConflictException("Удалять комментарий может только его автор или админ");
                } else {
                    eventService.get(eventId).getComments().remove(commentId);
                    commentStorage.deleteById(commentId);
                }
            }
        }
    }

    @Override
    public EventFullDto changeComment(String text, Integer userId, Integer commentId)
            throws ObjectNotFoundException, ConflictException {
        log.info("Запрос на редактирование комментария {} пользователем {}", commentId, userId);
        Comment comment = commentStorage.findById(commentId).orElseThrow(() -> new ObjectNotFoundException("Комментарий" +
                " не найден"));
        if (userService.get(userId).getStatus().equals(Status.ADMIN)) {
            //если ты админ, то без лишних вопросов можешь редактировать комментарий
            comment.setText(text);
            commentStorage.save(comment);
        } else {
            if (LocalDateTime.now().isAfter(comment.getCreated().plusHours(HOURS_LIMIT))) {
                throw new ConflictException("Редактировать комментарий можно только в течение " + HOURS_LIMIT
                        + " часов после публикации");
            } else {
                if (!comment.getAuthor().equals(userId)) {
                    throw new ConflictException("Редактировать комментарий может только его автор или админ");
                } else {
                    comment.setText(text);
                    commentStorage.save(comment);
                }
            }
        }
        return mapper.eventToFullDto(eventService.get(comment.getEventId()));
    }

    /*
        Лайки и дизлайки реализованы по принципу YouTube. Просто два счетчика для соперничества добра и зла =)
     */

    @Override
    public void likeComment(Integer commentId, Integer userId) throws ConflictException, ObjectNotFoundException {
        log.info("Пользователь {} поставил лайк комментарию {}", userId, commentId);
        if (!userService.get(userId).getStatus().equals(Status.NOTAUTHORIZED)) {
            throw new ConflictException("Лайк может поставить только авторизованный пользователь или админ");
        } else {
            Comment comment = commentStorage.findById(commentId).orElseThrow(() -> new ObjectNotFoundException("Комментарий" +
                    " не найден"));
            Integer likes = comment.getLikes();
            likes++;
            comment.setLikes(likes);
            commentStorage.save(comment);
        }
    }

    @Override
    public void dislikeComment(Integer commentId, Integer userId) throws ConflictException, ObjectNotFoundException {
        log.info("Пользователь {} поставил дизлайк комментарию {}", userId, commentId);
        if (!userService.get(userId).getStatus().equals(Status.NOTAUTHORIZED)) {
            throw new ConflictException("Лайк может поставить только авторизованный пользователь или админ");
        } else {
            Comment comment = commentStorage.findById(commentId).orElseThrow(() -> new ObjectNotFoundException("Комментарий" +
                    " не найден"));
            Integer dislikes = comment.getDislikes();
            dislikes++;
            comment.setDislikes(dislikes);
            commentStorage.save(comment);
        }
    }
}
