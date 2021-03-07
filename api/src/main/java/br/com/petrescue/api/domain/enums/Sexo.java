package br.com.petrescue.api.domain.enums;

public enum Sexo {
    MACHO('M', "Macho"), FEMEA('F', "Femea");

    private char abreviacao;
    private String sexo;

    Sexo(char abreviacao, String sexo) {
        this.abreviacao = abreviacao;
        this.sexo = sexo;
    }
}
