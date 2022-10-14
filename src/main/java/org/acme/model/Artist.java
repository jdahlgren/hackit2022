package org.acme.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public final class Artist {

  public Artist() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;

  public Long id() {
    return id;
  }

  public String name() {
    return name;
  }
}
