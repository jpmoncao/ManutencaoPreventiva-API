package br.com.manutcontrol.dto;

import br.com.manutcontrol.model.StatusMaquina;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "MaquinaRequest", description = "Dados para cadastrar ou atualizar uma máquina")
public record MaquinaRequest(
        @Schema(description = "Código único da máquina", example = "EXT-005") @NotBlank String codigo,
        @Schema(description = "Nome do equipamento", example = "Extrusora 5 Camadas") @NotBlank String nome,
        @Schema(description = "Setor da planta", example = "Extrusao") @NotBlank String setor,
        @Schema(description = "Fabricante, se conhecido", example = "Windmoller") String fabricante,
        @Schema(description = "Situação operacional", example = "ATIVA") @NotNull StatusMaquina status
) {}
