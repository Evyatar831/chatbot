package com.handson.chatbot.controller;


import com.handson.chatbot.service.ChuckNorrisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;


@RestController
@RequestMapping("/bot")
public class BotController {





    @Autowired
    ChuckNorrisService chuckNorrisService;

    @RequestMapping(value = "/chuck-norris", method = RequestMethod.GET)
    public ResponseEntity<?> getChuckNorrisJokes(@RequestParam String query) {
        try {
            String jokes = chuckNorrisService.searchJokes(query);
            return new ResponseEntity<>(jokes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error fetching Chuck Norris jokes: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "", method = { RequestMethod.POST})
    public ResponseEntity<?> getJokeResponse(@RequestBody JokeQuery query) throws IOException {
        HashMap<String, String> params = query.getQueryResult().getParameters();
        String res = "Chuck Norris doesn't tell jokes. Jokes tell Chuck Norris.";

        if (params.containsKey("word")) {
            res = chuckNorrisService.searchJokes(params.get("word"));
        }

        return new ResponseEntity<>(JokeResponse.of(res), HttpStatus.OK);
    }

    static class JokeQuery {
        QueryResult queryResult;
        public QueryResult getQueryResult() {
            return queryResult;
        }
    }

    static class QueryResult {
        HashMap<String, String> parameters;
        public HashMap<String, String> getParameters() {
            return parameters;
        }
    }

    static class JokeResponse {
        String fulfillmentText;
        String source = "CHUCK_NORRIS_BOT";

        public String getFulfillmentText() {
            return fulfillmentText;
        }

        public String getSource() {
            return source;
        }

        public static JokeResponse of(String fulfillmentText) {
            JokeResponse res = new JokeResponse();
            res.fulfillmentText = fulfillmentText;
            return res;
        }
    }
}
