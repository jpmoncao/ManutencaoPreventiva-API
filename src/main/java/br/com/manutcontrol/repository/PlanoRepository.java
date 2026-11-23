package br.com.manutcontrol.repository;
import br.com.manutcontrol.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PlanoRepository extends JpaRepository<Plano,Long> {
 boolean existsByMaquinaId(Long maquinaId);
}
