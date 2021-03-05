//package br.com.petrescue.api.domain;
//
//import br.com.cwi.crescer.api.domain.enums.Status;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "fb_feedback")
//public class Feedback {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @JoinColumn(name = "id_avaliado")
//    @ManyToOne
//    private Usuario avaliado;
//
//    @JoinColumn(name = "id_autor")
//    @ManyToOne
//    private Usuario autor;
//
//    @Column(name = "data_prevista")
//    @JsonFormat(pattern = "dd/MM/yyyy")
//    private LocalDateTime dataPrevista;
//
//    @Column(name = "data_realizada")
//    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//    private LocalDateTime dataRealizada;
//
//    @Column
//    @Enumerated(EnumType.STRING)
//    private Status status;
//
//    @Column(columnDefinition = "NUMBER(1)")
//    private boolean anual;
//
//    @Column
//    private String melhoria;
//
//    @Column
//    private String excelencia;
//
//    @Column
//    private String desenvolvimento;
//
//}
