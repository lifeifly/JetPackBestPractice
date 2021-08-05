package com.yele.jetpackbestpractice.data.bean;

import java.io.Serializable;
import java.util.List;

public class BaseAlbumItem<M extends BaseMusicItem<A>, A extends BaseArtistItem> implements Serializable {
    private String albumId;
    private String title;
    private String summary;
    private A artist;
    private String coverImg;
    private List<M> music;

    public BaseAlbumItem() {
    }

    public BaseAlbumItem(String albumId, String title, String summary, A artist, String coverImg, List<M> music) {
        this.albumId = albumId;
        this.title = title;
        this.summary = summary;
        this.artist = artist;
        this.coverImg = coverImg;
        this.music = music;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public A getArtist() {
        return artist;
    }

    public void setArtist(A artist) {
        this.artist = artist;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public List<M> getMusic() {
        return music;
    }

    public void setMusic(List<M> music) {
        this.music = music;
    }
}
