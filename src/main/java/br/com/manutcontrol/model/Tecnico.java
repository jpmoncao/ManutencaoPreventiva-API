package br.com.manutcontrol.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tecnicos", uniqueConstraints = @UniqueConstraint(columnNames = "matricula"))
@Schema(name = "Tecnico", description = "Profissional habilitado a registrar execuções")
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador gerado pelo banco", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    public Long id;
    @NotBlank
    @Schema(example = "Joao Silva")
    public String nome;
    @NotBlank
    @Column(nullable = false, unique = true)
    @Schema(description = "Matrícula única", example = "TEC-001")
    public String matricula;
    @NotBlank
    @Schema(example = "Mecanica")
    public String especialidade;
}
