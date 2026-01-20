package com.EventManagementSystem.service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;

@Service
public class LocationIQGeocodingService {

    private final RestTemplate restTemplate;

    @Value("${locationiq.api.key}")
    private String apiKey;

    public LocationIQGeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

   public double[] getLatLong(String address) {

    try {
//        String url = "https://us1.locationiq.com/v1/autocomplete.php"
//                + "?key=" + apiKey
//                + "&q=" + URLEncoder.encode(address, StandardCharsets.UTF_8)
//                + "&format=json"
//                + "&limit=1";
        
        String url = "https://us1.locationiq.com/v1/search.php"
                + "?key=" + apiKey
                + "&q=" + URLEncoder.encode(address, StandardCharsets.UTF_8)
                + "&format=json"
                + "&limit=1";

        System.out.println("Calling LocationIQ: " + url);

        List<Map<String, Object>> response =
                restTemplate.getForObject(url, List.class);

        if (response == null || response.isEmpty()) {
            System.out.println("LocationIQ: No results found");
            return new double[]{0.0, 0.0};
        }

        Map<String, Object> result = response.get(0);

        double lat = Double.parseDouble(result.get("lat").toString());
        double lon = Double.parseDouble(result.get("lon").toString());

        return new double[]{lat, lon};

    } catch (Exception e) {
        System.out.println("LocationIQ error: " + e.getMessage());
        return new double[]{0.0, 0.0};
    }
}

}
