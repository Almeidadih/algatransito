package com.example.algatransito.domain.service;

import com.example.algatransito.domain.excepiton.NegocioExcepiton;
import com.example.algatransito.domain.model.Proprietario;
import com.example.algatransito.domain.model.StatusVeiculo;
import com.example.algatransito.domain.model.Veiculo;
import com.example.algatransito.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Service
public class RegistroVeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final RegistroProprietarioService registroProprietarioService;

    public Veiculo buscar(Long veiculoId){
        return veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new NegocioExcepiton("Veiculo não encontrado"));

    }

    @Transactional
    public Veiculo cadastrar(Veiculo novoVeiculo) {
        if (novoVeiculo.getId() != null) {
            throw new NegocioExcepiton("Veiculo a ser cadastrado não deve possuir um código");
        }
        boolean placaEmUso = veiculoRepository.findByPlaca(novoVeiculo.getPlaca())
                .filter(veiculo -> !veiculo.equals(novoVeiculo))
                .isPresent();

        if (placaEmUso) {
            throw new NegocioExcepiton("Já existe um veiculo cadastrado com essa placa");
        }
        Proprietario proprietario = registroProprietarioService.buscar(novoVeiculo.getProprietario().getId());

        novoVeiculo.setProprietario(proprietario);
        novoVeiculo.setStatus(StatusVeiculo.REGULAR);
        novoVeiculo.setDataCadastro(OffsetDateTime.now());

        return veiculoRepository.save(novoVeiculo);

    }

}
