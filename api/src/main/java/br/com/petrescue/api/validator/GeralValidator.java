package br.com.petrescue.api.validator;

import br.com.petrescue.api.domain.subClasses.Localizacao;
import br.com.petrescue.api.exceptions.NegocioException;
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
        if (localizacao.getLatitudee().equals(null) || localizacao.getLongitude().equals(0) || localizacao.getLatitudee().equals(0) || localizacao.getLongitude().equals(null)){
            throw new NegocioException("Localização não pode ser nullo!");
        }
    }
}
