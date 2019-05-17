package com.nju.movie.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nju.movie.domain.Movie;
import com.nju.movie.util.Crawl;
import com.nju.movie.util.TransferUtil;
import com.nju.movie.util.XMLUtil;
import org.jdom2.JDOMException;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.*;

@RestController
@CrossOrigin
public class MovieInfoController {

    @GetMapping(value = "getMovieInfo")
    public static Object getMovieInfo() throws IllegalAccessException {
        Crawl.start();
        TransferUtil.startXML();
        return merge();
    }

    public static Object merge() {
        String root = "src/main/java/com/nju/movie/py/resources/";

        String[] files = { root+"time_after.xml", root+"douban_after.xml", root+"maoyan_after.xml"};
        Map<String, Movie> map0 = formatMovie(files[0]);
        Map<String, Movie> map1 = formatMovie(files[1]);
        Map<String, Movie> map2 = formatMovie(files[2]);

        Set<String> names = findNames(map0, map1, map2);
        Map<String, Movie> res = new HashMap<>();
        for (String name : names){
            res.put(name, mergeSingleMovie(name, map0, map1, map2));
        }
        countRank(res);

        return JSONObject.toJSON(res);
    }

    public static void countRank(Map<String, Movie> res){
        for (Movie movie : res.values()){
            String r1 = movie.getRank_douban() == null ? "101" : movie.getRank();
            String r2 = movie.getRank_time() == null ? "101" : movie.getRank();
            String r3 = movie.getRank_maoyan() == null ? "101" : movie.getRank();
            int rank = Integer.parseInt(r1) + Integer.parseInt(r2) + Integer.parseInt(r3);
            movie.setRank((rank/3) + "");
        }
    }

    public static Movie mergeSingleMovie(String name, Map<String, Movie>... maps){
        Movie movie = maps[0].get(name);

        //time空，载入douban
        if (movie == null){
            movie = maps[1].get(name);
        }
        //time非空，补充douban
        else{
            try {
                movie = sup(movie, maps[1].get(name));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //douban空，载入maoyan
        if (movie == null) {
            movie = maps[2].get(name);
        }
        else{
            try {
                movie = sup(movie, maps[2].get(name));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //载入3平台评分和排名
        movie.setRate_time(Optional.ofNullable(maps[0].get(name)).orElse(new Movie()).getRate());
        movie.setRank_time(Optional.ofNullable(maps[0].get(name)).orElse(new Movie()).getRank());
        movie.setRate_douban(Optional.ofNullable(maps[1].get(name)).orElse(new Movie()).getRate());
        movie.setRank_douban(Optional.ofNullable(maps[1].get(name)).orElse(new Movie()).getRank());
        movie.setRate_maoyan(Optional.ofNullable(maps[2].get(name)).orElse(new Movie()).getRate());
        movie.setRank_maoyan(Optional.ofNullable(maps[2].get(name)).orElse(new Movie()).getRank());

        return movie;
    }

    public static Movie sup(Movie movie, Movie sup) throws IllegalAccessException {
        if (sup == null) return movie;
        Field[] fields = sup.getClass().getDeclaredFields();
        for (Field f : fields){
            f.setAccessible(true);
            if (f.get(movie) == null){
                f.set(movie, f.get(sup));
            }
        }
        return movie;
    }

    public static Set<String> findNames(Map<String, Movie>... maps){
        Set<String> names = new HashSet<>();
        for (Map<String, Movie> map : maps){
            names.addAll(map.keySet());
        }
        return names;
    }

    public static Map<String, Movie> formatMovie(String file){
        Map<String, Movie> map = new HashMap<>();
        JSONObject json = null;
        try {
            json = XMLUtil.xml2JSON(XMLUtil.getBytes(file));
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray array = json.getJSONObject("movies").getJSONArray("movie");
        for (int i=0; i<array.size(); i++){
            String title = array.getJSONObject(i).getJSONArray("title").getString(0);
            if (file.startsWith("time")){
                title = title.split("/")[0];
            }
            map.put(title, transSingle(array.getJSONObject(i)));
        }

        return map;
    }

    public static Movie transSingle(JSONObject json){
        Movie movie = new Movie();
        movie.setTitle(nullable(json, "title"));
        movie.setRank(nullable(json, "rank"));
        movie.setDirector(nullable(json, "director"));
        movie.setActors(nullable(json, "actors"));
        movie.setDate(nullable(json, "date"));
        movie.setRate(nullable(json, "rate"));
        movie.setCountry(nullable(json, "country"));
        movie.setGeners(nullable(json, "geners"));
        movie.setComCount(nullable(json, "comCount"));
        movie.setImg(nullable(json, "img"));
        movie.setContent(nullable(json, "content"));

        return movie;
    }

    public static String nullable(JSONObject json, String key){
        if (json.getJSONArray(key) == null){
            return null;
        }
        else{
            return json.getJSONArray(key).getString(0);
        }
    }
}
