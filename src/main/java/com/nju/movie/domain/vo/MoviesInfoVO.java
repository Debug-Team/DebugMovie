package com.nju.movie.domain.vo;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@RequiredArgsConstructor
public class MoviesInfoVO {

    @NonNull
    private ArrayList<MovieVO> douban;

    @NonNull
    private ArrayList<MovieVO> maoyan;

    @NonNull
    private ArrayList<MovieVO> mtime;



}
