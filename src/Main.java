import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelos.Movie;
import modelos.MovieResponse;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String apiKey = "SUA-API";
        String link = "https://api.themoviedb.org/3/movie/top_rated?api_key=";

        String endereco = link + apiKey;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();

        Gson gson = new GsonBuilder().create();
        MovieResponse movieResponse = gson.fromJson(json, MovieResponse.class);

        if (movieResponse.getResults() != null) {

            Gson gsonBonitinho = new GsonBuilder().setPrettyPrinting().create(); //vai pegar o json e deixa-lo mais organizado.

            FileWriter escrita = new FileWriter("top-filmes-tmdb.json");
            escrita.write(gsonBonitinho.toJson(movieResponse.getResults()));
            escrita.close();

            // Print titles to console (optional)
            for (Movie movie : movieResponse.getResults()) {
                System.out.println(movie.getTitle());
            }
        } else {
            System.out.println("Error: Sem resultados");
        }
    }
}
