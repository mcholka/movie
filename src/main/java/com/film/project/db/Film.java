package com.film.project.db;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by mcholka on 2014-05-17. Enjoy!
 */
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filmGenerator")
    @SequenceGenerator(name = "filmGenerator", sequenceName = "film_id_seq", allocationSize = 1)
    private Long id;
    @Column(length = 10000)
    private String title;
    @Column(length = 10000)
    private String originalTitle;
    private BigDecimal buget;
    @Column(unique = true)
    private Integer apiId;
    @Column(length = 10000)
    private String genre;
    @Column(length = 10000)
    private String overview;
    private BigDecimal popularity;
    private Date releaseDate;
    private BigDecimal revenue;
    private BigDecimal voteAverage;
    private Integer voteCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public BigDecimal getBuget() {
        return buget;
    }

    public void setBuget(BigDecimal buget) {
        this.buget = buget;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public BigDecimal getPopularity() {
        return popularity;
    }

    public void setPopularity(BigDecimal popularity) {
        this.popularity = popularity;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public BigDecimal getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(BigDecimal voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
