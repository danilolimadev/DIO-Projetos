package br.com.danilolima.apiinteligente.repository;

import br.com.danilolima.apiinteligente.entity.InteracaoIa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InteracaoIaRepository extends JpaRepository<InteracaoIa, Long> {
    List<InteracaoIa> findTop10ByConversationIdOrderByCreatedAtDesc(String conversationId);
    List<InteracaoIa> findByConversationIdOrderByCreatedAtAsc(String conversationId);
}
