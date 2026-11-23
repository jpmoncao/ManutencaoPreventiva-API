package br.com.manutcontrol.repository;
import br.com.manutcontrol.model.Execucao;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ExecucaoRepository extends JpaRepository<Execucao,Long> {
 boolean existsByPlanoId(Long planoId); boolean existsByTecnicoId(Long tecnicoId);
}
