package br.com.petrescue.api.utils;

import br.com.petrescue.api.controller.dto.AnimalPINDTO;
import br.com.petrescue.api.domain.subClasses.Localizacao;
import br.com.petrescue.api.exceptions.NegocioException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GeralValidator {

    public void string(String string, String campo) {
        if (string.isEmpty()) {
            throw new NegocioException(campo + " não pode ser vazio!");
        }
    }

    public void stringTamanho(String string, String campo, int tamanho) {
        if (string.isEmpty()) {
            throw new NegocioException(campo + " não pode ser vazio!");
        }
        if (string.length() < tamanho) {
            throw new NegocioException(campo + " deve ter no mínimo " + tamanho + " caracteres!");
        }
    }

    public void localizacao(Localizacao localizacao){
        if (localizacao == null || localizacao.getLatitude()==null || localizacao.getLongitude() == null || localizacao.getLongitude().equals(0.0) || localizacao.getLatitude().equals(0.0)){
            throw new NegocioException("Localização não pode ser nullo!");
        }
    }

    public List<AnimalPINDTO> distancia(Localizacao localizacao) {
        return null;
    }
}
