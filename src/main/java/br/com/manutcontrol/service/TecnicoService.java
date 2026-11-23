package br.com.manutcontrol.service;
import br.com.manutcontrol.model.Tecnico;
import br.com.manutcontrol.dto.TecnicoRequest;
import br.com.manutcontrol.repository.*;
import br.com.manutcontrol.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
@Service public class TecnicoService {
 private final TecnicoRepository tecnicos;
 public TecnicoService(TecnicoRepository tecnicos){this.tecnicos=tecnicos;}
 public Tecnico buscar(Long id){return tecnicos.findById(id).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"Tecnico nao encontrado"));}
 public Tecnico criar(TecnicoRequest dados){Tecnico t=new Tecnico();t.nome=dados.nome();t.matricula=dados.matricula();t.especialidade=dados.especialidade();if(tecnicos.existsByMatricula(t.matricula))throw new ApiException(HttpStatus.CONFLICT,"Matricula ja cadastrada");return tecnicos.save(t);}
}
