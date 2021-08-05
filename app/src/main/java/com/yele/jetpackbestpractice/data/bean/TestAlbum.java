package com.yele.jetpackbestpractice.data.bean;

public class TestAlbum extends BaseAlbumItem<TestAlbum.TestMusic,TestAlbum.TestArtist>{
    private String albumId;

    @Override
    public String getAlbumId() {
        return albumId;
    }

    @Override
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public static class TestMusic extends BaseMusicItem<TestArtist>{
        private String songId;

        public String getSongId() {
            return songId;
        }

        public void setSongId(String songId) {
            this.songId = songId;
        }
    }

    public static class TestArtist extends BaseArtistItem{
        private String birthDay;

        public String getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(String birthDay) {
            this.birthDay = birthDay;
        }
    }
}
