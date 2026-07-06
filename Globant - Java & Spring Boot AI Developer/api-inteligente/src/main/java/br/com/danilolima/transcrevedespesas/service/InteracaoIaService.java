package br.com.danilolima.apiinteligente.service;

import br.com.danilolima.apiinteligente.dto.InteracaoIaResponse;
import br.com.danilolima.apiinteligente.entity.InteracaoIa;
import br.com.danilolima.apiinteligente.enums.OrigemLancamento;
import br.com.danilolima.apiinteligente.repository.InteracaoIaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InteracaoIaService {

    private final InteracaoIaRepository repository;

    public InteracaoIaService(InteracaoIaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void registrar(String conversationId, OrigemLancamento origem, String textoOriginal,
                          String transcricao, String resposta) {
        repository.save(new InteracaoIa(conversationId, origem, textoOriginal, transcricao, resposta));
    }

    @Transactional(readOnly = true)
    public List<String> contextoRecente(String conversationId) {
        var recentes = new ArrayList<>(repository.findTop10ByConversationIdOrderByCreatedAtDesc(conversationId));
        Collections.reverse(recentes);
        return recentes.stream()
                .flatMap(interacao -> List.of("Usuario: " + interacao.getTextoOriginal(),
                        "Assistente: " + interacao.getResposta()).stream())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<InteracaoIaResponse> listar(String conversationId) {
        return repository.findByConversationIdOrderByCreatedAtAsc(conversationId).stream()
                .map(InteracaoIaResponse::from).toList();
    }
}
