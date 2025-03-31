package com.emagalha.desafio_api.dto.mapper;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

import com.emagalha.desafio_api.dto.output.ServidorEfetivoUnidadeOutputDTO;
import com.emagalha.desafio_api.entity.Lotacao;
import com.emagalha.desafio_api.entity.ServidorEfetivo;

@Component
public class ServidorEfetivoUnidadeMapper {
    
    public ServidorEfetivoUnidadeOutputDTO toDTO(ServidorEfetivo servidor) {
        ServidorEfetivoUnidadeOutputDTO dto = new ServidorEfetivoUnidadeOutputDTO();
        dto.setNome(servidor.getPessoa().getNome());
        dto.setIdade(calcularIdade(servidor.getPessoa().getDataNascimento()));
        
        if (servidor.getPessoa().getLotacoes() != null && !servidor.getPessoa().getLotacoes().isEmpty()) {
            Lotacao lotacaoAtiva = servidor.getPessoa().getLotacoes().stream()
                .filter(Lotacao::isAtiva)
                .findFirst()
                .orElse(null);
                
            if (lotacaoAtiva != null) {
                dto.setUnidadeLotacao(lotacaoAtiva.getUnidade().getNome());
            }
        }
        
        return dto;
    }
    
    private Integer calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}