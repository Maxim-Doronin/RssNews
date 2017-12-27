package com.maximdoronin.rssnews;

import java.util.Date;

/**
 * Created by maximdoronin on 12/26/17.
 */

public class RssItem {
    private String mTitle;
    private String mLink;
    private String mDescription;
    private Date mPubDate;
    private NewsSubject mNewsSubject;

    public RssItem(String title, String link, String description, Date pubDate,
                   NewsSubject newsSubject) {
        mTitle = title;
        mLink = link;
        mDescription = description;
        mPubDate = pubDate;
        mNewsSubject = newsSubject;
    }


    public String getTitle() {
        return mTitle;
    }

    public String getLink() {
        return mLink;
    }

    public String getDescription() {
        return mDescription;
    }

    public Date getPubDate() {
        return mPubDate;
    }

    public NewsSubject getNewsSubject() {
        return mNewsSubject;
    }
}
