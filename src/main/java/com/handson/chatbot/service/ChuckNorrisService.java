package com.handson.chatbot.service;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChuckNorrisService {

    //
    public static final Pattern JOKE_PATTERN = Pattern.compile("\"value\":\"([^\"]+)\"");

    public  String searchJokes(String query) throws IOException {
        String jsonResponse = getChuckNorrisJokes(query);
        return parseJokesJson(jsonResponse, query);
    }

    private String parseJokesJson(String json, String query) {
        String result = "";
        Matcher matcher = JOKE_PATTERN.matcher(json);
        int jokeCount = 1;

        while (matcher.find()) {
            String joke = matcher.group(1);
            // Decode HTML entities
            joke = joke.replace("&quot;", "\"")
                    .replace("&amp;", "&")
                    .replace("&lt;", "<")
                    .replace("&gt;", ">")
                    .replace("&#39;", "'");

            result += "Joke " + jokeCount + ": " + joke + "<br><br>\n";
            jokeCount++;
        }

        if (result.isEmpty()) {
            return "No jokes found for query: " + query;
        }

        return result;
    }

    private String getChuckNorrisJokes(String query) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        // Encode the query parameter
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        String url = "https://api.chucknorris.io/jokes/search?query=" + encodedQuery;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}