package br.com.petrescue.api.utils;

import br.com.petrescue.api.domain.subClasses.Localizacao;
import br.com.petrescue.api.exceptions.NegocioException;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class GeralValidator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public void string(String string, String campo) {
        if (string == null || string.isEmpty()) {
            throw new NegocioException(campo + " não pode ser vazio!");
        }
    }

    public void stringTamanho(String string, String campo, int tamanho) {
        if (string == null || string.isEmpty()) {
            throw new NegocioException(campo + " não pode ser vazio!");
        }
        if (string.length() < tamanho) {
            throw new NegocioException(campo + " deve ter no mínimo " + tamanho + " caracteres!");
        }
    }

    public void localizacao(Localizacao localizacao) {
        if (localizacao == null || localizacao.getLatitude() == null || localizacao.getLongitude() == null || localizacao.getLongitude().equals(0.0) || localizacao.getLatitude().equals(0.0)) {
            throw new NegocioException("Localização não pode ser nullo!");
        }
    }

    public void validarEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new NegocioException("Email inválido: " + email);
        }
    }

    public void validarCpfCnpj(String codigo) {
        boolean valido = true;
        this.string(codigo, "Cpf/Cnpj");

        if (codigo.length() == 11) {
            if (codigo.equals("00000000000") ||
                    codigo.equals("11111111111") ||
                    codigo.equals("22222222222") || codigo.equals("33333333333") ||
                    codigo.equals("44444444444") || codigo.equals("55555555555") ||
                    codigo.equals("66666666666") || codigo.equals("77777777777") ||
                    codigo.equals("88888888888") || codigo.equals("99999999999")) {
                valido = false;
            }

            char dig10, dig11;
            int sm, i, r, num, peso;
            try {
                sm = 0;
                peso = 10;
                for (i = 0; i < 9; i++) {
                    num = codigo.charAt(i) - 48;
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11))
                    dig10 = '0';
                else dig10 = (char) (r + 48);

                sm = 0;
                peso = 11;
                for (i = 0; i < 10; i++) {
                    num = codigo.charAt(i) - 48;
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11))
                    dig11 = '0';
                else dig11 = (char) (r + 48);

                if (!(dig10 == codigo.charAt(9)) || !(dig11 == codigo.charAt(10))) {
                    valido = false;
                }
            } catch (InputMismatchException erro) {
                valido = false;
            }
        } else if (codigo.length() == 14) {
            if (codigo.equals("00000000000000") || codigo.equals("11111111111111") ||
                    codigo.equals("22222222222222") || codigo.equals("33333333333333") ||
                    codigo.equals("44444444444444") || codigo.equals("55555555555555") ||
                    codigo.equals("66666666666666") || codigo.equals("77777777777777") ||
                    codigo.equals("88888888888888") || codigo.equals("99999999999999")) {
                valido = false;
            }

            char dig13, dig14;
            int sm, i, r, num, peso;

            try {
                sm = 0;
                peso = 2;
                for (i = 11; i >= 0; i--) {
                    num = codigo.charAt(i) - 48;
                    sm = sm + (num * peso);
                    peso = peso + 1;
                    if (peso == 10)
                        peso = 2;
                }

                r = sm % 11;
                if ((r == 0) || (r == 1))
                    dig13 = '0';
                else dig13 = (char) ((11 - r) + 48);

                sm = 0;
                peso = 2;
                for (i = 12; i >= 0; i--) {
                    num = codigo.charAt(i) - 48;
                    sm = sm + (num * peso);
                    peso = peso + 1;
                    if (peso == 10)
                        peso = 2;
                }

                r = sm % 11;
                if ((r == 0) || (r == 1))
                    dig14 = '0';
                else dig14 = (char) ((11 - r) + 48);

                if (!(dig13 == codigo.charAt(12)) || !(dig14 == codigo.charAt(13))) {
                    valido = false;
                }
            } catch (InputMismatchException erro) {
                valido = false;
            }
        } else {
            valido = false;
        }
        if (valido == false) {
            throw new NegocioException("Cpf/Cnpj inválido!");
        }
    }
}
