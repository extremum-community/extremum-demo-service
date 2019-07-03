package com.cybernation.testservice.models.jpa.basic;

import com.extremum.common.models.annotation.ModelName;
import com.extremum.jpa.models.PostgresBasicModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author rpuch
 */
@Entity
@Table(name = "fly")
@Getter
@Setter
@ModelName(Fly.MODEL_NAME)
public class Fly extends PostgresBasicModel {
    public static final String MODEL_NAME = "Fly";

    public Fly() {
    }

    public Fly(String name) {
        this.name = name;
    }

    private String name;
    @Getter(onMethod_ = {@ManyToOne(fetch = FetchType.LAZY), @JoinColumn(name = "swarm_id")})
    private Swarm swarm;
}