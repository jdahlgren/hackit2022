package se.knowit.hackit2022.service;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import org.jetbrains.annotations.NotNull;
import se.knowit.hackit2022.dto.ArtistRequest;
import se.knowit.hackit2022.dto.ArtistResponse;
import se.knowit.hackit2022.dto.SongRequest;
import se.knowit.hackit2022.dto.SongResponse;
import se.knowit.hackit2022.model.Artist;
import se.knowit.hackit2022.model.Song;

@ApplicationScoped
public class ArtistService {

  @Transactional
  public ArtistResponse storeArtist(ArtistRequest artistRequest) {
    Artist artist = new Artist();
    artist.name = artistRequest.name();

    artist.persist();
    return new ArtistResponse(artist.id, artist.name, mapToSongResponse(artist));
  }

  public List<ArtistResponse> getAllArtists() {
    List<Artist> panacheEntityBases = Artist.listAll();

    return panacheEntityBases.stream().map(a -> new ArtistResponse(a.id, a.name, mapToSongResponse(a)))
        .toList();
  }

  public ArtistResponse getArtistById(long id) {
    Optional<Artist> artistOptional = Artist.findByIdOptional(id);
    Artist artist = artistOptional.orElseThrow(() -> new NotFoundException("Could not find " + id));
    List<SongResponse> songResponses = mapToSongResponse(artist);
    return new ArtistResponse(artist.id, artist.name, songResponses);
  }

  @Transactional
  public ArtistResponse storeSong(long artistId, SongRequest songRequest) {
    Optional<Artist> artistOptional = Artist.findByIdOptional(artistId);
    Artist artist = artistOptional.orElseThrow(() -> new NotFoundException("Could not find " + artistId));
    Song song = new Song();
    song.name = songRequest.name();
    song.artist = artist;
    song.persist();

    artist.songs.add(song);

    List<SongResponse> songResponses = mapToSongResponse(artist);
    return new ArtistResponse(artist.id, artist.name, songResponses);
  }

  @NotNull
  private static List<SongResponse> mapToSongResponse(Artist artist) {
    return artist.songs.stream().map(s -> new SongResponse(s.id, s.name)).toList();
  }
}
