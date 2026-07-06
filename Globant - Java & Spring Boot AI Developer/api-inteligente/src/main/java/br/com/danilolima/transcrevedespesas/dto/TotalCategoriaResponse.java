package br.com.danilolima.apiinteligente.dto;

import br.com.danilolima.apiinteligente.enums.CategoriaDespesa;

import java.math.BigDecimal;

public record TotalCategoriaResponse(CategoriaDespesa categoria, BigDecimal total) {
}
