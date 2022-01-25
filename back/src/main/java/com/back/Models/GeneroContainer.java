package com.back.Models;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneroContainer {
    private List<Dupla> genres;

    public List<Dupla> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        StringBuilder data = new StringBuilder();
        for (Dupla genero : genres) {
            data.append(genero.toString() + ",\n");
        }
        return data.toString();
    }

}
