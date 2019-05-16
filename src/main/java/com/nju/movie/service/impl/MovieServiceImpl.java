package com.nju.movie.service.impl;

import com.nju.movie.dao.MovieDao;
import com.nju.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieDao movieDao;



}
