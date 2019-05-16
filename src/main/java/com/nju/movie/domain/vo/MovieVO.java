package com.nju.movie.domain.vo;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MovieVO {

    @NonNull
    private String name;

    @NonNull
    private double point;


}
