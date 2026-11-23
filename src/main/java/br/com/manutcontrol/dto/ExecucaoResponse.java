package br.com.manutcontrol.dto;

import br.com.manutcontrol.model.Execucao;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(name = "ExecucaoResponse", description = "Execução persistida")
public record ExecucaoResponse(
        @Schema(example = "1") Long id,
        @Schema(example = "1") Long planoId,
        @Schema(example = "1") Long tecnicoId,
        @Schema(example = "2026-11-23") LocalDate dataExecucao,
        @Schema(example = "Filtros inspecionados") String observacoes
) {
    public static ExecucaoResponse of(Execucao e) {
        return new ExecucaoResponse(e.id, e.plano.id, e.tecnico.id, e.dataExecucao, e.observacoes);
    }
}
