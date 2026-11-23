package br.com.manutcontrol.controller;

import br.com.manutcontrol.dto.ErroResposta;
import br.com.manutcontrol.dto.PlanoRequest;
import br.com.manutcontrol.dto.PlanoResponse;
import br.com.manutcontrol.service.PlanoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/planos")
@Tag(name = "Planos")
public class PlanoController {
    private final PlanoService service;

    public PlanoController(PlanoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastrar plano", description = "Vincula um plano preventivo a uma máquina existente. A periodicidade mínima é 1 dia.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Plano criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Dados invalidos\",\"detalhes\":[\"periodicidadeDias: must be greater than or equal to 1\"]}"))),
            @ApiResponse(responseCode = "404", description = "Máquina informada não existe",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Maquina nao encontrada\",\"status\":404}")))
    })
    public ResponseEntity<PlanoResponse> criar(@Valid @RequestBody PlanoRequest d) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(d));
    }

    @GetMapping
    @Operation(summary = "Listar planos", description = "Lista todos os planos. O campo situacao é VENCIDA ou EM_DIA, conforme a próxima data.")
    @ApiResponse(responseCode = "200", description = "Lista de planos")
    public List<PlanoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/vencidos")
    @Operation(summary = "Listar planos vencidos", description = "Retorna planos cuja próxima data é anterior a hoje.")
    @ApiResponse(responseCode = "200", description = "Planos vencidos")
    public List<PlanoResponse> vencidos() {
        return service.vencidos();
    }

    @GetMapping("/proximos")
    @Operation(summary = "Listar próximos vencimentos", description = "Retorna planos com vencimento entre hoje e hoje + N dias (N de 0 a 365).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Planos no intervalo"),
            @ApiResponse(responseCode = "400", description = "Parâmetro dias fora do intervalo 0–365",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"dias deve estar entre 0 e 365\",\"status\":400}")))
    })
    public List<PlanoResponse> proximos(
            @Parameter(description = "Janela em dias a partir de hoje", example = "7")
            @RequestParam(defaultValue = "7") int dias) {
        return service.proximos(dias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar plano", description = "Busca um plano pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plano encontrado"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Plano nao encontrado\",\"status\":404}")))
    })
    public PlanoResponse consultar(@Parameter(description = "ID do plano", example = "1") @PathVariable Long id) {
        return service.consultar(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar plano", description = "Altera descrição, periodicidade, próxima data ou máquina vinculada.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plano atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(responseCode = "404", description = "Plano ou máquina não encontrados",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Plano nao encontrado\",\"status\":404}")))
    })
    public PlanoResponse atualizar(@Parameter(description = "ID do plano", example = "1") @PathVariable Long id,
                                   @Valid @RequestBody PlanoRequest d) {
        return service.atualizar(id, d);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir plano", description = "Remove o plano somente se não houver execuções no histórico.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Plano excluído", content = @Content),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Plano nao encontrado\",\"status\":404}"))),
            @ApiResponse(responseCode = "409", description = "Plano possui histórico de execuções",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Plano com historico nao pode ser excluido\",\"status\":409}")))
    })
    public void excluir(@Parameter(description = "ID do plano", example = "1") @PathVariable Long id) {
        service.excluir(id);
    }
}
