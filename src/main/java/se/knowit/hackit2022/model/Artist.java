package se.knowit.hackit2022.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Artist extends PanacheEntity {

  public String name;

  @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
  public List<Song> songs = new ArrayList<>();

}
