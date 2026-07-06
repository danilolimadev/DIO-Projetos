package br.com.danilolima.apiinteligente.dto;

import jakarta.validation.constraints.NotBlank;

public record AssistenteTextoRequest(String conversationId, @NotBlank String mensagem) {
}
