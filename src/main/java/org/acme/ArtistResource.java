package org.acme;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.acme.model.Artist;
import org.acme.service.ArtistService;
import org.jboss.resteasy.reactive.RestPath;

@Path("/artists")
public class ArtistResource {

    @Inject
    ArtistService artistService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String artists() {
        return "All artists";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String artists(Artist artist) {
        Artist storedArtist = artistService.storeArtist(artist);
        return storedArtist.id() + storedArtist.name();
    }

    @Path("/{name}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String artist(@RestPath String name) {
        return name;
    }

    @Path("/{name}/songs")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String artistsSongs(@RestPath String name) {
        return "All songs by " + name;
    }



}