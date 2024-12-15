package org.tj.tjmovies.DAO;

public interface MovieProjection {
    //这里使用的是movie实体类中的元素
    String getId();
    String getTitle();
    String getRating();
    String getReleaseDate();
    String getImageSrc();
}
