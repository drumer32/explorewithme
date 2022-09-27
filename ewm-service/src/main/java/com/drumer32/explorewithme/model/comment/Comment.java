package com.drumer32.explorewithme.model.comment;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String text;
    Integer author;
    Integer eventId;
    Integer likes;
    Integer dislikes;
    LocalDateTime created;
}
