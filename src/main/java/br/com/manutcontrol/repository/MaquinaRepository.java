package br.com.manutcontrol.repository;
import br.com.manutcontrol.model.Maquina;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MaquinaRepository extends JpaRepository<Maquina,Long> {
 boolean existsByCodigo(String codigo);
}
