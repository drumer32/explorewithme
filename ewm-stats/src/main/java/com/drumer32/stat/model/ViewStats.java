package com.drumer32.stat.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewStats {
    String app;
    String uri;
    Integer hits;
}
