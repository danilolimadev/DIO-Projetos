package br.com.danilolima.apiinteligente.dto;

import jakarta.validation.constraints.NotBlank;

public record SinteseRequest(@NotBlank String texto) {
}
