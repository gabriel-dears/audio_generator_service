package com.gabriel.audio_generator_service.application.service.url.query_param;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class QueryParamExtractor {

    public Map<String, String> getQueryParams(String url) {
        Map<String, String> queryParams = new HashMap<>();

        try {
            // Parse the URL string into a URI
            URI uri = new URI(url);
            String query = uri.getQuery();

            // Check if the query string is not null
            if (query != null) {
                // Split the query string by "&"
                String[] pairs = query.split("&");

                for (String pair : pairs) {
                    // Split each pair into key and value
                    int idx = pair.indexOf("=");
                    String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                    String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                    queryParams.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return queryParams;
    }
}

