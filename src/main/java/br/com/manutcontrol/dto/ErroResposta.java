package br.com.manutcontrol.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "ErroResposta", description = "Corpo padrão de erro da API")
public record ErroResposta(
        @Schema(description = "Mensagem resumida", example = "Maquina nao encontrada") String erro,
        @Schema(description = "Código HTTP, quando aplicável", example = "404") Integer status,
        @Schema(description = "Detalhes de validação de campos, quando houver") List<String> detalhes
) {}
