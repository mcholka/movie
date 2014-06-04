package com.film.project.db;

import javax.persistence.*;

/**
 * Created by mcholka on 2014-05-17. Enjoy!
 */
@Entity
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "counterGenerator")
    @SequenceGenerator(name = "counterGenerator", sequenceName = "counter_id_seq", allocationSize = 1)
    private Long id;

    private Integer count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
