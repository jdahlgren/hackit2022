package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;

@Entity
public class Artist extends PanacheEntity {

  public String name;

}
