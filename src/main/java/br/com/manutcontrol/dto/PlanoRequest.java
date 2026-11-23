package br.com.manutcontrol.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(name = "PlanoRequest", description = "Dados para cadastrar ou atualizar um plano preventivo")
public record PlanoRequest(
        @Schema(description = "Identificador da máquina já cadastrada", example = "1") @NotNull Long maquinaId,
        @Schema(description = "O que deve ser verificado", example = "Verificacao dos filtros") @NotBlank String descricao,
        @Schema(description = "Intervalo em dias entre execuções (mínimo 1)", example = "30") @Min(1) int periodicidadeDias,
        @Schema(description = "Próxima data prevista (ISO-8601)", example = "2026-12-15") @NotNull LocalDate proximaData
) {}
