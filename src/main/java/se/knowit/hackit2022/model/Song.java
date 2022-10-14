package se.knowit.hackit2022.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Song extends PanacheEntity {

  private String name;
  @ManyToOne(cascade = CascadeType.ALL)
  private Artist artist;

  public Song(String name, Artist artist) {
    this.name = name;
    this.artist = artist;
  }

  public Song() {

  }

  public long getId() {
    return id;
  }
  public String getName() {
    return name;
  }
  public Artist getArtist() {
    return artist;
  }

}