package br.com.petrescue.api.domain.subClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Localizacao {
    private Double latitudee;
    private Double longitude;
}
