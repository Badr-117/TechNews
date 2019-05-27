package com.example.gamingnews;

public class News {
    private String title, date, imageUrl, url, source;

    public News(String title, String date, String imageUrl, String url, String source) {
        this.title = title;
        this.date = date;
        this.imageUrl = imageUrl;
        this.url = url;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() { return date; }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getSource() {
        return source;
    }
}
