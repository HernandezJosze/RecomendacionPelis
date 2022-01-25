package com.back.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import com.back.Models.*;

@CrossOrigin(origins = "http://localhost:3000")
@Controller // This means that this class is a Controller
@RequestMapping(path="/api") // This means URL's start with /api (after Application path)
public class MovieController {
  private ObjectMapper objectMapper;
  private String apy_key= "bb3fd8ab419097d83adf66e0d7a06ab2";

    public MovieController() {
        super();
        objectMapper = new ObjectMapper();
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
    , @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        return "Saved";
    }

    @GetMapping(path="/generos")
    public @ResponseBody Iterable<Dupla> getGeneros() {
        // This returns a JSON or XML with the users
        URL url = null;
        List<Dupla> generos = new ArrayList<>();
        try {
            url = new URL(String.format("https://api.themoviedb.org/3/genre/movie/list?api_key=%s&language=es-MX", apy_key));
            GeneroContainer container = objectMapper.readValue(url, GeneroContainer.class);
            generos = container.getGenres(); 
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StreamReadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DatabindException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return generos;
    }

    @GetMapping(path="/personas")
    public @ResponseBody Iterable<Dupla> getPersonas(@RequestParam String name) {
        // This returns a JSON or XML with the users
        URL url = null;
        int max_personas = 15;
        String src = String.format("https://api.themoviedb.org/3/search/person?api_key=%s&language=es-MX&query=%s&page=1&include_adult=false",apy_key, name.trim());
        System.out.println(src);
        List<Dupla> personas = new ArrayList<>();
        try {
            url = new URL(src);
            PersonaContainer container = objectMapper.readValue(url, PersonaContainer.class);
            personas = container.getResults(); 
            while (personas.size()>max_personas) {
                personas.remove(personas.size()-1);
            }
            System.out.println(container); 
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StreamReadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DatabindException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return personas;
    }
}