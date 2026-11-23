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
@Service public class PlanoService {
 private final PlanoRepository planos; private final ExecucaoRepository execucoes; private final MaquinaService maquinas;
 public PlanoService(PlanoRepository planos,ExecucaoRepository execucoes,MaquinaService maquinas){this.planos=planos;this.execucoes=execucoes;this.maquinas=maquinas;}
 public Plano buscar(Long id){return planos.findById(id).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"Plano nao encontrado"));}
 public List<PlanoResponse> listar(){return planos.findAll().stream().map(PlanoResponse::of).toList();}
 public PlanoResponse consultar(Long id){return PlanoResponse.of(buscar(id));}
 public PlanoResponse criar(PlanoRequest d){Plano p=new Plano();preencher(p,d);return PlanoResponse.of(planos.save(p));}
 public PlanoResponse atualizar(Long id,PlanoRequest d){Plano p=buscar(id);preencher(p,d);return PlanoResponse.of(planos.save(p));}
 private void preencher(Plano p,PlanoRequest d){p.maquina=maquinas.buscar(d.maquinaId());p.descricao=d.descricao();p.periodicidadeDias=d.periodicidadeDias();p.proximaData=d.proximaData();}
 @Transactional public void excluir(Long id){buscar(id);if(execucoes.existsByPlanoId(id))throw new ApiException(HttpStatus.CONFLICT,"Plano com historico nao pode ser excluido");planos.deleteById(id);}
 public List<PlanoResponse> vencidos(){return planos.findAll().stream().filter(p->p.proximaData.isBefore(LocalDate.now())).map(PlanoResponse::of).toList();}
 public List<PlanoResponse> proximos(int dias){if(dias<0||dias>365)throw new ApiException(HttpStatus.BAD_REQUEST,"dias deve estar entre 0 e 365");LocalDate hoje=LocalDate.now();return planos.findAll().stream().filter(p->!p.proximaData.isBefore(hoje)&&!p.proximaData.isAfter(hoje.plusDays(dias))).map(PlanoResponse::of).toList();}
}
