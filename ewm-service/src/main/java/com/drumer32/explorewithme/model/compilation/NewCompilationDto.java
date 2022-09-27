package com.drumer32.explorewithme.model.compilation;

import lombok.Data;

import java.util.List;

@Data
public class NewCompilationDto {

    List<Integer> events;
    boolean pinned;
    String title;
}
