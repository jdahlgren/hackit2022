package se.knowit.hackit2022.dto;

import java.util.List;

public record ArtistResponse(long id, String name, List<SongResponse> songs) {

}
