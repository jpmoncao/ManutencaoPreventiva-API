package br.com.manutcontrol.controller;

import br.com.manutcontrol.dto.ErroResposta;
import br.com.manutcontrol.dto.ExecucaoRequest;
import br.com.manutcontrol.dto.ExecucaoResponse;
import br.com.manutcontrol.service.ExecucaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/execucoes")
@Tag(name = "Execuções")
public class ExecucaoController {
    private final ExecucaoService service;

    public ExecucaoController(ExecucaoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Registrar execução",
            description = """
                    Registra uma manutenção realizada e avança a próxima data do plano (data da execução + periodicidade).

                    Regras: a data não pode ser futura; a máquina do plano precisa estar ATIVA; a data não pode ser anterior ao início do ciclo atual.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Execução registrada e próxima data do plano atualizada"),
            @ApiResponse(responseCode = "400", description = "Data inválida ou dados obrigatórios ausentes",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(name = "dataFutura", value = "{\"erro\":\"Data de execucao nao pode ser futura\",\"status\":400}"))),
            @ApiResponse(responseCode = "404", description = "Plano ou técnico não encontrados",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Plano nao encontrado\",\"status\":404}"))),
            @ApiResponse(responseCode = "409", description = "Máquina não está ATIVA",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"A maquina precisa estar ATIVA para registrar manutencao preventiva\",\"status\":409}")))
    })
    public ResponseEntity<ExecucaoResponse> registrar(@Valid @RequestBody ExecucaoRequest d) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(d));
    }

    @GetMapping
    @Operation(summary = "Listar execuções", description = "Retorna o histórico de manutenções registradas.")
    @ApiResponse(responseCode = "200", description = "Histórico de execuções")
    public List<ExecucaoResponse> listar() {
        return service.listar();
    }
}
