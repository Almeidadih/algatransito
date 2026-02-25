package com.example.algatransito.api.assembler;

import com.example.algatransito.api.model.AutuacaoModel;
import com.example.algatransito.api.model.input.AutucaoInput;
import com.example.algatransito.domain.model.Autuacao;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class AutuacaoAssembler {

    private final ModelMapper modelMapper;

    public Autuacao toEntity(AutucaoInput autucaoInput) {
        return modelMapper.map(autucaoInput, Autuacao.class);
    }

    public AutuacaoModel toModel(Autuacao autuacao) {
        return  modelMapper.map(autuacao, AutuacaoModel.class);
    }

    public List<AutuacaoModel> toCollectionModel(List<Autuacao > atuacoes) {
        return  atuacoes.stream()
                .map(this::toModel)
                .toList();
    }
}
