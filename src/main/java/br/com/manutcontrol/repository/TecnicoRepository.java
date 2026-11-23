package br.com.manutcontrol.repository;
import br.com.manutcontrol.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TecnicoRepository extends JpaRepository<Tecnico,Long> {
 boolean existsByMatricula(String matricula);
}
