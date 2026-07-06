package br.com.danilolima.apiinteligente.dto;

public record LoginResponse(
        String token,
        String type,
        long expiresInMinutes
) {
}
