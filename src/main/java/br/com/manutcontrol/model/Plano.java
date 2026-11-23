package br.com.manutcontrol.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
@Entity @Table(name="planos") public class Plano {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @NotBlank public String descricao;
 @Min(1) public int periodicidadeDias;
 @NotNull public LocalDate proximaData;
 @ManyToOne(optional=false,fetch=FetchType.EAGER) @JoinColumn(name="maquina_id",nullable=false) public Maquina maquina;
}
