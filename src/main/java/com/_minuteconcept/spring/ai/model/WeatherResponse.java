package com._minuteconcept.spring.ai.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record WeatherResponse(
        @JsonProperty("cloud_pct") int cloudPct,
        @JsonProperty("temp") int temp,
        @JsonProperty("feels_like") int feelsLike,
        @JsonProperty("humidity") int humidity,
        @JsonProperty("min_temp") int minTemp,
        @JsonProperty("max_temp") int maxTemp,
        @JsonProperty("wind_speed") double windSpeed,
        @JsonProperty("wind_degrees") int windDegrees,
        @JsonProperty("sunrise") Instant sunrise,
        @JsonProperty("sunset") Instant sunset
) {}