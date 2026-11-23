package br.com.manutcontrol.controller;

import br.com.manutcontrol.dto.ErroResposta;
import br.com.manutcontrol.dto.TecnicoRequest;
import br.com.manutcontrol.model.Tecnico;
import br.com.manutcontrol.service.TecnicoService;
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

@RestController
@RequestMapping("/api/tecnicos")
@Tag(name = "Técnicos")
public class TecnicoController {
    private final TecnicoService service;

    public TecnicoController(TecnicoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastrar técnico", description = "Cria um técnico. A matrícula não pode se repetir. Use o ID retornado ao registrar execuções.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Técnico criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Dados invalidos\",\"detalhes\":[\"matricula: must not be blank\"]}"))),
            @ApiResponse(responseCode = "409", description = "Matrícula já cadastrada",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class),
                            examples = @ExampleObject(value = "{\"erro\":\"Matricula ja cadastrada\",\"status\":409}")))
    })
    public ResponseEntity<Tecnico> criar(@Valid @RequestBody TecnicoRequest t) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(t));
    }
}
