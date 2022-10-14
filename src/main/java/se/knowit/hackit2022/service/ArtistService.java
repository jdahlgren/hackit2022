package se.knowit.hackit2022.service;

import java.util.List;
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
    Artist artist = new Artist(artistRequest.name());
    artist.persist();

    return mapToArtistResponse(artist);
  }

  public List<ArtistResponse> getAllArtists() {
    return Artist.listAll().stream()
        .map(Artist.class::cast)
        .map(this::mapToArtistResponse)
        .toList();
  }

  public ArtistResponse getArtistById(long id) {
    return Artist.findByIdOptional(id)
        .map(Artist.class::cast)
        .map(this::mapToArtistResponse)
        .orElseThrow(() -> new NotFoundException("Could not find " + id));
  }

  @Transactional
  public ArtistResponse storeSong(long artistId, SongRequest songRequest) {
    Artist artist = Artist.findByIdOptional(artistId)
        .map(Artist.class::cast)
        .orElseThrow(() -> new NotFoundException("Could not find " + artistId));

    Song song = new Song(songRequest.name(), artist);
    song.persist();

    artist.addSong(song);

    return mapToArtistResponse(artist);
  }

  private ArtistResponse mapToArtistResponse(Artist artist) {
    List<SongResponse> songResponses = mapToSongResponse(artist.getSongs());
    return new ArtistResponse(artist.getId(), artist.getName(), songResponses);
  }

  private List<SongResponse> mapToSongResponse(List<Song> songs) {
    return songs.stream()
        .map(song -> new SongResponse(song.getId(), song.getName()))
        .toList();
  }
}
