package com.example.demoFirebase.domain.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public abstract class AbstractEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
}
