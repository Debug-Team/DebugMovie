package com.nju.movie.domain;

public class Movie {
    private String title;
    private String rank;
    private String director;
    private String actors;
    private String date;
    private String rate;
    private String country;
    private String geners;
    private String comCount;
    private String img;
    private String content;
    private String rate_douban;
    private String rate_maoyan;
    private String rate_time;
    private String rank_douban;
    private String rank_maoyan;
    private String rank_time;

    public Movie() {
    }

    public String getRank_douban() {
        return rank_douban;
    }

    public void setRank_douban(String rank_douban) {
        this.rank_douban = rank_douban;
    }

    public String getRank_maoyan() {
        return rank_maoyan;
    }

    public void setRank_maoyan(String rank_maoyan) {
        this.rank_maoyan = rank_maoyan;
    }

    public String getRank_time() {
        return rank_time;
    }

    public void setRank_time(String rank_time) {
        this.rank_time = rank_time;
    }

    public String getRate_douban() {
        return rate_douban;
    }

    public String getRate_maoyan() {
        return rate_maoyan;
    }

    public String getRate_time() {
        return rate_time;
    }

    public void setRate_douban(String rate_douban) {
        this.rate_douban = rate_douban;
    }

    public void setRate_maoyan(String rate_maoyan) {
        this.rate_maoyan = rate_maoyan;
    }

    public void setRate_time(String rate_time) {
        this.rate_time = rate_time;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGeners() {
        return geners;
    }

    public void setGeners(String geners) {
        this.geners = geners;
    }

    public String getComCount() {
        return comCount;
    }

    public void setComCount(String comCount) {
        this.comCount = comCount;
    }
}
