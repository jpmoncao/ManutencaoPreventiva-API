package br.com.manutcontrol.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "TecnicoRequest", description = "Dados para cadastrar um técnico")
public record TecnicoRequest(
        @Schema(description = "Nome completo", example = "Joao Silva") @NotBlank String nome,
        @Schema(description = "Matrícula única", example = "TEC-001") @NotBlank String matricula,
        @Schema(description = "Área de atuação", example = "Mecanica") @NotBlank String especialidade
) {}
