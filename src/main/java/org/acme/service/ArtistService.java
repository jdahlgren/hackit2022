package org.acme.service;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import org.acme.dto.ArtistRequest;
import org.acme.dto.ArtistResponse;
import org.acme.dto.SongRequest;
import org.acme.model.Artist;

@ApplicationScoped
public class ArtistService {

  @Transactional
  public ArtistResponse storeArtist(ArtistRequest artistRequest) {
    Artist artist = new Artist();
    artist.name = artistRequest.name();

    artist.persist();
    return new ArtistResponse(artist.id, artist.name);
  }

  public List<ArtistResponse> getAllArtists() {
    List<Artist> panacheEntityBases = Artist.listAll();
    return panacheEntityBases.stream().map(a -> new ArtistResponse(a.id, a.name)).toList();
  }

  public ArtistResponse getArtistById(long id) {
    Optional<Artist> artistOptional = Artist.findByIdOptional(id);
    Artist artist = artistOptional.orElseThrow(() -> new NotFoundException("Could not find " + id));
    return new ArtistResponse(artist.id, artist.name);
  }

  public String storeSong(long artistId, SongRequest song) {
    return "null";
  }
}
