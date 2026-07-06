package br.com.danilolima.apiinteligente.dto;

import java.util.List;

public record MeResponse(
        String username,
        List<String> authorities
) {
}
