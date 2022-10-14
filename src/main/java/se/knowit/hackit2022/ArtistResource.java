package se.knowit.hackit2022;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import se.knowit.hackit2022.dto.ArtistRequest;
import se.knowit.hackit2022.dto.ArtistResponse;
import se.knowit.hackit2022.dto.SongRequest;
import se.knowit.hackit2022.service.ArtistService;
import org.jboss.resteasy.reactive.RestPath;

@Path("/artists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArtistResource {

    @Inject
    ArtistService artistService;

    @GET
    public List<ArtistResponse> artists() {
        return artistService.getAllArtists();
    }

    @POST
    public ArtistResponse artists(ArtistRequest artist) {
        return artistService.storeArtist(artist);
    }


    @Path("/{id:\\d+}")
    @GET
    public ArtistResponse artistById(@RestPath long id) {
        return artistService.getArtistById(id);
    }

    @Path("/{artistId}/songs")
    @POST
    public ArtistResponse artistsSongs(@RestPath long artistId, SongRequest song) {
        return artistService.storeSong(artistId, song);
    }

}