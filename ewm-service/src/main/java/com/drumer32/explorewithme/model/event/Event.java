package com.drumer32.explorewithme.model.event;

import com.drumer32.explorewithme.tools.Location;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String annotation;
    Integer category;
    Integer confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;

    String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    @ManyToOne(cascade = {CascadeType.ALL})
    Location location;

    Integer initiator;
    boolean paid;
    Integer participantLimit;
    String publishedOn;
    boolean requestModeration;
    State state;
    String title;
    Integer views;

    @ElementCollection
    List<Integer> comments;
}
