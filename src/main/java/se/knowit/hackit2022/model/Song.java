package se.knowit.hackit2022.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Song extends PanacheEntity {

  public String name;
  @ManyToOne(cascade = CascadeType.ALL)
  public Artist artist;

}