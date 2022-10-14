package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

public class Song extends PanacheEntity {

  public String name;

}