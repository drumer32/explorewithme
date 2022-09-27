package com.drumer32.explorewithme.model.event;

import com.drumer32.explorewithme.model.category.CategoryDto;
import com.drumer32.explorewithme.model.user.UserShortDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventFullDto {
    String annotation;
    CategoryDto category;
    Integer confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;
    String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    Integer id;
    UserShortDto initiator;
    boolean paid;
    Integer participantLimit;
    String publishedOn;
    boolean requestModeration;
    State state;
    String title;
    Integer views;
    List<Integer> comments;
}
