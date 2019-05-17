package com.nju.movie.util;


import java.io.*;

public class Crawl {

    private static final String sep = System.getProperty("file.separator");

    public static void start(){
        crawl("crawl_douban_movie_top250.py");
        crawl("crawl_maoyan_movie_top100.py");
        crawl("crawl_time_movie_top100.py");

    }

    public static void crawl(String fileName) {

        String path = System.getProperty("user.dir") + sep + "src" + sep + "main" + sep + "java" + sep + "com" + sep + "nju" + sep + "movie" + sep + "py" + sep + fileName;
        String[] arguments = new String[]{"python", path};
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GB2312"));
            String line = "";
            while ((line = in.readLine()) != null) {
                result += line;
            }
            in.close();
            int re = process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
