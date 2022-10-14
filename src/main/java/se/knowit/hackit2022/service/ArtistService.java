package se.knowit.hackit2022.service;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
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
    artist.setName(artistRequest.name());
    artist.persist();

    return mapToArtistResponse(artist);
  }

  public List<ArtistResponse> getAllArtists() {
    List<Artist> panacheEntityBases = Artist.listAll();

    return panacheEntityBases.stream()
        .map(ArtistService::mapToArtistResponse)
        .toList();
  }

  public ArtistResponse getArtistById(long id) {
    Optional<Artist> artistOptional = Artist.findByIdOptional(id);
    Artist artist = artistOptional.orElseThrow(() -> new NotFoundException("Could not find " + id));
    return mapToArtistResponse(artist);
  }

  public ArtistResponse storeSong(long artistId, SongRequest songRequest) {
    Optional<Artist> artistOptional = Artist.findByIdOptional(artistId);
    Artist artist = artistOptional.orElseThrow(() -> new NotFoundException("Could not find " + artistId));
    Song song = new Song();
    song.setName(songRequest.name());
    song.setArtist(artist);
    song.persist();

    artist.getSongs().add(song);

    return mapToArtistResponse(artist);
  }

  private static ArtistResponse mapToArtistResponse(Artist artist) {
    List<SongResponse> songResponses = mapToSongResponse(artist.getSongs());
    return new ArtistResponse(artist.getId(), artist.getName(), songResponses);
  }

  private static List<SongResponse> mapToSongResponse(List<Song> songs) {
    return songs.stream()
        .map(song -> new SongResponse(song.getId(), song.getName()))
        .toList();
  }
}
