package br.com.manutcontrol.controller;

import br.com.manutcontrol.dto.ErroResposta;
import br.com.manutcontrol.dto.MaquinaRequest;
import br.com.manutcontrol.model.Maquina;
import br.com.manutcontrol.service.MaquinaService;
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
@RequestMapping("/api/maquinas")
@Tag(name = "Máquinas")
public class MaquinaController {
    private final MaquinaService service;

    public MaquinaController(MaquinaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastrar máquina", description = "Cria um equipamento. O código não pode se repetir. O ID é gerado pelo banco.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Máquina criada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou JSON incorreto",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Dados invalidos\",\"detalhes\":[\"codigo: must not be blank\"]}"))),
            @ApiResponse(responseCode = "409", description = "Código já cadastrado",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Codigo de maquina ja existe\",\"status\":409}")))
    })
    public ResponseEntity<Maquina> criar(@Valid @RequestBody MaquinaRequest m) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(m));
    }

    @GetMapping
    @Operation(summary = "Listar máquinas", description = "Retorna todos os equipamentos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de máquinas")
    public List<Maquina> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar máquina", description = "Busca um equipamento pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Máquina encontrada"),
            @ApiResponse(responseCode = "404", description = "Máquina não encontrada",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Maquina nao encontrada\",\"status\":404}")))
    })
    public Maquina buscar(@Parameter(description = "ID da máquina", example = "1") @PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar máquina", description = "Substitui os dados do equipamento. O novo código também precisa ser único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Máquina atualizada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))),
            @ApiResponse(responseCode = "404", description = "Máquina não encontrada",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Maquina nao encontrada\",\"status\":404}"))),
            @ApiResponse(responseCode = "409", description = "Código já utilizado por outra máquina",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Codigo de maquina ja existe\",\"status\":409}")))
    })
    public Maquina atualizar(@Parameter(description = "ID da máquina", example = "1") @PathVariable Long id,
                             @Valid @RequestBody MaquinaRequest m) {
        return service.atualizar(id, m);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir máquina", description = "Remove o equipamento somente se ele não possuir planos vinculados.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Máquina excluída", content = @Content),
            @ApiResponse(responseCode = "404", description = "Máquina não encontrada",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Maquina nao encontrada\",\"status\":404}"))),
            @ApiResponse(responseCode = "409", description = "Máquina possui planos",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Maquina possui planos; remova os planos antes\",\"status\":409}")))
    })
    public void excluir(@Parameter(description = "ID da máquina", example = "1") @PathVariable Long id) {
        service.excluir(id);
    }
}
