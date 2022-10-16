package se.knowit.hackit2022.service;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import se.knowit.hackit2022.dto.ArtistRequest;
import se.knowit.hackit2022.dto.ArtistResponse;
import se.knowit.hackit2022.dto.SongRequest;
import se.knowit.hackit2022.dto.SongResponse;
import se.knowit.hackit2022.model.Artist;
import se.knowit.hackit2022.model.Song;
import se.knowit.hackit2022.model.TopSongsResponse;

@ApplicationScoped
public class ArtistService {

  @RestClient
  LastFmService lastFmService;

  @Transactional
  public ArtistResponse storeArtist(ArtistRequest artistRequest) {
    Artist artist = new Artist(artistRequest.name());

    MultivaluedMap<String, String> queryParams = getQueryParams(artistRequest);

    TopSongsResponse topSongsResponse = lastFmService.getTopSongsForArtist(queryParams);
    if (topSongsResponse != null) {
      topSongsResponse.toptracks().track().forEach(
              track -> artist.addSong(new Song(track.name(), artist))
      );
    }

    artist.persist();

    return mapToArtistResponse(artist);
  }

  private static MultivaluedMap<String, String> getQueryParams(ArtistRequest artistRequest) {
    MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<>();
    queryParams.add("method", "artist.gettoptracks");
    queryParams.add("artist", artistRequest.name());
    queryParams.add("api_key", "770966cf75acda6b1712e4d5de8693a6");
    queryParams.add("format", "json");
    return queryParams;
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
