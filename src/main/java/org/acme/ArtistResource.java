package org.acme;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.acme.dto.ArtistRequest;
import org.acme.dto.ArtistResponse;
import org.acme.dto.SongRequest;
import org.acme.service.ArtistService;
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
    public String artistsSongs(@RestPath long artistId, SongRequest song) {
        return artistService.storeSong(artistId, song);
    }



}