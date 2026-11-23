package br.com.manutcontrol.dto;

import br.com.manutcontrol.model.Plano;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(name = "PlanoResponse", description = "Plano preventivo com situação calculada")
public record PlanoResponse(
        @Schema(example = "1") Long id,
        @Schema(example = "1") Long maquinaId,
        @Schema(description = "Nome da máquina vinculada", example = "Extrusora 5 Camadas") String maquina,
        @Schema(example = "Verificacao dos filtros") String descricao,
        @Schema(example = "30") int periodicidadeDias,
        @Schema(example = "2026-12-15") LocalDate proximaData,
        @Schema(description = "VENCIDA se a próxima data já passou; senão EM_DIA", example = "EM_DIA") String situacao
) {
    public static PlanoResponse of(Plano p) {
        return new PlanoResponse(p.id, p.maquina.id, p.maquina.nome, p.descricao, p.periodicidadeDias, p.proximaData,
                p.proximaData.isBefore(LocalDate.now()) ? "VENCIDA" : "EM_DIA");
    }
}
