package br.com.manutcontrol.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "maquinas", uniqueConstraints = @UniqueConstraint(columnNames = "codigo"))
@Schema(name = "Maquina", description = "Equipamento industrial acompanhado pela manutenção preventiva")
public class Maquina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador gerado pelo banco", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    public Long id;
    @NotBlank
    @Column(nullable = false, unique = true, length = 40)
    @Schema(description = "Código único", example = "EXT-005")
    public String codigo;
    @NotBlank
    @Column(nullable = false)
    @Schema(example = "Extrusora 5 Camadas")
    public String nome;
    @NotBlank
    @Column(nullable = false)
    @Schema(example = "Extrusao")
    public String setor;
    @Schema(example = "Windmoller")
    public String fabricante;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "ATIVA, EM_MANUTENCAO ou INATIVA", example = "ATIVA")
    public StatusMaquina status = StatusMaquina.ATIVA;
}
