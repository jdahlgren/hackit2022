package se.knowit.hackit2022.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Artist extends PanacheEntity {

  public Artist() {

  }

  public Artist(String name) {
    this.name = name;
  }

  private String name;

  @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
  private final List<Song> songs = new ArrayList<>();

  public String getName() {
    return name;
  }

  public long getId() {
    return id;
  }

  public List<Song> getSongs() {
    return List.copyOf(songs);
  }

  public void addSong(Song song) {
    songs.add(song);
  }


}
