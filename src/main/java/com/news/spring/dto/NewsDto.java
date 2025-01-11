package com.news.spring.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class NewsDto {

    private Long id;
    private String title;
    private String text;
    private Instant date;
}
