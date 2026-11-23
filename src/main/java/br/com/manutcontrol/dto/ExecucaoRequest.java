package br.com.manutcontrol.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(name = "ExecucaoRequest", description = "Registro de uma manutenção realizada")
public record ExecucaoRequest(
        @Schema(description = "Plano executado", example = "1") @NotNull Long planoId,
        @Schema(description = "Técnico responsável", example = "1") @NotNull Long tecnicoId,
        @Schema(description = "Data da execução (não pode ser futura)", example = "2026-11-23") @NotNull LocalDate dataExecucao,
        @Schema(description = "Anotações da intervenção", example = "Filtros inspecionados") String observacoes
) {}
