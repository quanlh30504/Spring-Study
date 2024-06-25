package com.example.studySpring.DTOs.Response;

import co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
    private boolean authenticated;

}
