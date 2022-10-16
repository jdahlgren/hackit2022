package se.knowit.hackit2022.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;
import se.knowit.hackit2022.model.TopSongsResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;

@Path("/2.0")
@RegisterRestClient(configKey = "last-fm")
public interface LastFmService {
    @GET
    TopSongsResponse getTopSongsForArtist(@RestQuery final MultivaluedMap<String, String> filters);
}
