package com.drumer32.explorewithme.model.event;

import com.drumer32.explorewithme.model.category.CategoryDto;
import com.drumer32.explorewithme.model.user.UserShortDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventShortDto {
    String annotation;
    CategoryDto category;
    Integer confirmedRequests;
    LocalDateTime eventDate;
    Integer id;
    UserShortDto initiator;
    boolean paid;
    String title;
    Integer views;
    List<Integer> comments;
}
