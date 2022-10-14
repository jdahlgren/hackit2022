package org.acme.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.acme.model.Artist;

@ApplicationScoped
public class ArtistService {
  @Inject
  EntityManager entityManager;

  @Transactional
  public Artist storeArtist(Artist artist) {
    entityManager.persist(artist);
    return artist;
  }
}
