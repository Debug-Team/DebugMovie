package com.nju.movie.controller;

import com.alibaba.fastjson.JSONObject;
import com.nju.movie.util.XMLUtil;
import org.jdom2.JDOMException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@CrossOrigin
public class MovieInfoController {

    @GetMapping(value = "getMovieInfo")
    public static JSONObject getMovieInfo() throws IOException, JDOMException {
        JSONObject json = XMLUtil.xml2JSON(XMLUtil.getBytes("test.xml"));
        return json;
    }

}
