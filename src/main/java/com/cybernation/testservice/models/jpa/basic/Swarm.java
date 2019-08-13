package com.cybernation.testservice.models.jpa.basic;

import io.extremum.authentication.api.constants.RolesConstants;
import io.extremum.common.models.annotation.ModelName;
import io.extremum.jpa.models.PostgresBasicModel;
import io.extremum.security.ExtremumRequiredRoles;
import io.extremum.security.NoDataSecurity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rpuch
 */
@Entity
@Table(name = "swarm")
@Getter
@Setter
@ModelName(Swarm.MODEL_NAME)
@ExtremumRequiredRoles(defaultAccess = RolesConstants.ANONYMOUS)
@NoDataSecurity
public class Swarm extends PostgresBasicModel {
    public static final String MODEL_NAME = "Swarm";
    public static final String CUSTOM_FLIES = "customFlies";

    private String name;
    @Getter(onMethod_ = {@OneToMany(mappedBy = "swarm", cascade = CascadeType.ALL)})
    private List<Fly> flies = new ArrayList<>();

    public void addFly(Fly fly) {
        flies.add(fly);
        fly.setSwarm(this);
    }
}
