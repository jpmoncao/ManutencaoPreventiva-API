package br.com.manutcontrol.service;
import br.com.manutcontrol.model.*;
import br.com.manutcontrol.dto.MaquinaRequest;
import br.com.manutcontrol.repository.*;
import br.com.manutcontrol.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service public class MaquinaService {
 private final MaquinaRepository maquinas; private final PlanoRepository planos;
 public MaquinaService(MaquinaRepository maquinas,PlanoRepository planos){this.maquinas=maquinas;this.planos=planos;}
 public List<Maquina> listar(){return maquinas.findAll();}
 public Maquina buscar(Long id){return maquinas.findById(id).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"Maquina nao encontrada"));}
 public Maquina criar(MaquinaRequest dados){Maquina m=new Maquina();m.codigo=dados.codigo();m.nome=dados.nome();m.setor=dados.setor();m.fabricante=dados.fabricante();m.status=dados.status();if(maquinas.existsByCodigo(m.codigo))throw new ApiException(HttpStatus.CONFLICT,"Codigo de maquina ja existe");return maquinas.save(m);}
 public Maquina atualizar(Long id,MaquinaRequest dados){Maquina m=buscar(id);if(!m.codigo.equals(dados.codigo())&&maquinas.existsByCodigo(dados.codigo()))throw new ApiException(HttpStatus.CONFLICT,"Codigo de maquina ja existe");m.codigo=dados.codigo();m.nome=dados.nome();m.setor=dados.setor();m.fabricante=dados.fabricante();m.status=dados.status();return maquinas.save(m);}
 @Transactional public void excluir(Long id){buscar(id);if(planos.existsByMaquinaId(id))throw new ApiException(HttpStatus.CONFLICT,"Maquina possui planos; remova os planos antes");maquinas.deleteById(id);}
}
