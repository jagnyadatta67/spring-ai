package com._minuteconcept.spring.ai.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Weather request")
public record WeatherRequest(String lat, String lon) {}
