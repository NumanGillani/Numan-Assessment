package com.ny.articles.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticlesModel {

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String articleURL;

    @SerializedName("published_date")
    private String publishedDate;

    @SerializedName("section")
    private String articleSection;

    @SerializedName("byline")
    private String byLine;

    @SerializedName("abstract")
    private String articleDescription;
    @SerializedName("media")
    private List<Media> mediaThumbnails;

    public String getTitle() {
        return title;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getArticleSection() {
        return articleSection;
    }

    public String getByLine() {
        return byLine;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public List<Media> getMediaThumbnails() {
        return mediaThumbnails;
    }

    public class Media {
        @SerializedName("caption")
        private String mediaCaption;

        @SerializedName("copyright")
        private String mediaCopyright;

        @SerializedName("media-metadata")
        private List<MetaData> mediaMetadata;

        public String getMediaCaption() {
            return mediaCaption;
        }

        public String getMediaCopyright() {
            return mediaCopyright;
        }

        public List<MetaData> getMediaMetadata() {
            return mediaMetadata;
        }


        public class MetaData {
            @SerializedName("url")
            private String thumbnailURL;

            @SerializedName("format")
            private String mediaFormat;

            public String getThumbnailURL() {
                return thumbnailURL;
            }

            public String getMediaFormat() {
                return mediaFormat;
            }
        }

    }
}
