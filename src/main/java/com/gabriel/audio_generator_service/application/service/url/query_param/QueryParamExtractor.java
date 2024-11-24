package com.gabriel.audio_generator_service.application.service.url.query_param;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class QueryParamExtractor {

    public Map<String, String> getQueryParams(String url) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap<>();

        // Parse the URL string into a URI
        URI uri = new URI(url);
        String query = uri.getQuery();

        if (query != null && !query.isEmpty()) {
            // Split the query string by "&"
            String[] pairs = query.split("&");

            for (String pair : pairs) {
                // Split each pair into key and value
                int idx = pair.indexOf("=");
                if (idx > 0) {
                    String key = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
                    String value = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
                    queryParams.put(key, value);
                }
            }
        }

        return queryParams;
    }
}
