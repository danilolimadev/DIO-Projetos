package br.com.danilolima.apiinteligente.dto;

public record AssistenteResponse(String conversationId, String textoOriginal, String transcricao, String resposta) {
}
