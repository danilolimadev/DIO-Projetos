package br.com.danilolima.apiinteligente.dto;

import br.com.danilolima.apiinteligente.entity.Despesa;
import br.com.danilolima.apiinteligente.enums.CategoriaDespesa;
import br.com.danilolima.apiinteligente.enums.OrigemLancamento;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record DespesaResponse(
        Long id,
        String descricao,
        BigDecimal valor,
        CategoriaDespesa categoria,
        LocalDate dataDespesa,
        String formaPagamento,
        String observacao,
        OrigemLancamento origemLancamento,
        String textoOriginal,
        Instant createdAt,
        Instant updatedAt
) {
    public static DespesaResponse from(Despesa despesa) {
        return new DespesaResponse(despesa.getId(), despesa.getDescricao(), despesa.getValor(),
                despesa.getCategoria(), despesa.getDataDespesa(), despesa.getFormaPagamento(),
                despesa.getObservacao(), despesa.getOrigemLancamento(), despesa.getTextoOriginal(),
                despesa.getCreatedAt(), despesa.getUpdatedAt());
    }
}
