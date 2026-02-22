package com.example.algatransito.domain.service;

import com.example.algatransito.domain.excepiton.NegocioExcepiton;
import com.example.algatransito.domain.model.Proprietario;
import com.example.algatransito.domain.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RegistroProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

    public Proprietario buscar(Long proprietarioId){
     return proprietarioRepository.findById(proprietarioId)
             .orElseThrow(()-> new NegocioExcepiton("Proprietario não encontrado"));
    }

    @Transactional
    public Proprietario salvar(Proprietario proprietario) {
        boolean emailEmUso = proprietarioRepository.findByEmail(proprietario.getEmail())
                .filter(p -> !p.equals(proprietario))
                .isPresent();
        if (emailEmUso) {
            throw new NegocioExcepiton("Já existe um proprietario cadastrado com este e-mail");
        }
        return proprietarioRepository.save(proprietario);
    }

    @Transactional
    public void excluir(Long proprietarioId){
        proprietarioRepository.deleteById(proprietarioId);
    }
}
