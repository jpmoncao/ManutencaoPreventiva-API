package br.com.manutcontrol.service;
import br.com.manutcontrol.dto.*;
import br.com.manutcontrol.model.*;
import br.com.manutcontrol.repository.*;
import br.com.manutcontrol.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
@Service public class ExecucaoService {
 private final ExecucaoRepository execucoes;private final PlanoRepository planos;private final PlanoService planoService;private final TecnicoService tecnicoService;
 public ExecucaoService(ExecucaoRepository execucoes,PlanoRepository planos,PlanoService planoService,TecnicoService tecnicoService){this.execucoes=execucoes;this.planos=planos;this.planoService=planoService;this.tecnicoService=tecnicoService;}
 public List<ExecucaoResponse> listar(){return execucoes.findAll().stream().map(ExecucaoResponse::of).toList();}
 @Transactional public ExecucaoResponse registrar(ExecucaoRequest d){
  if(d.dataExecucao().isAfter(LocalDate.now()))throw new ApiException(HttpStatus.BAD_REQUEST,"Data de execucao nao pode ser futura");
  Plano p=planoService.buscar(d.planoId()); Tecnico t=tecnicoService.buscar(d.tecnicoId());
  if(p.maquina.status!=StatusMaquina.ATIVA)throw new ApiException(HttpStatus.CONFLICT,"A maquina precisa estar ATIVA para registrar manutencao preventiva");
  if(d.dataExecucao().isBefore(p.proximaData.minusDays(p.periodicidadeDias)))throw new ApiException(HttpStatus.BAD_REQUEST,"Data anterior ao inicio do ciclo preventivo atual");
  Execucao e=new Execucao();e.plano=p;e.tecnico=t;e.dataExecucao=d.dataExecucao();e.observacoes=d.observacoes();
  p.proximaData=d.dataExecucao().plusDays(p.periodicidadeDias);
  planos.save(p);return ExecucaoResponse.of(execucoes.save(e));
 }
}
