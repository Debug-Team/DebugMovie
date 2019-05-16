package com.nju.movie.controller;

import com.nju.movie.domain.vo.MoviesInfoVO;
import com.nju.movie.domain.vo.MovieVO;
import com.nju.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class MovieInfoController {

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "getMovieInfo")
    public MoviesInfoVO getMovieInfo(){
        ArrayList<MovieVO> douban = new ArrayList<>();
        ArrayList<MovieVO> maoyan = new ArrayList<>();
        ArrayList<MovieVO> mtime = new ArrayList<>();

        douban.add(new MovieVO("监狱风云", 9.5));
        douban.add(new MovieVO("监狱风云2", 9.8));

        maoyan.add(new MovieVO("英雄本色", 9.1));
        maoyan.add(new MovieVO("英雄本色2", 9.2));

        mtime.add(new MovieVO("丛横四海", 9.9));
        mtime.add(new MovieVO("丛横四海2", 10.0));


        MoviesInfoVO moviesInfoVO = new MoviesInfoVO(douban,maoyan,mtime);
        return moviesInfoVO;

    }

}
