package com.gerus.themovie.models.DB;

import com.gerus.themovie.models.Detail;
import com.gerus.themovie.models.Genre;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by gerus-mac on 24/03/17.
 */

public class GenreRelationship {

    @DatabaseField(foreign = true, columnName = Detail.ID_DB)
    private int idDetail;

    @DatabaseField(foreign = true, columnName = Genre.ID_DB)
    private int idGenre;

    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }
}
