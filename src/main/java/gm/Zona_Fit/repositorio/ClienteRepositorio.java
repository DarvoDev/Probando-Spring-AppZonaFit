package gm.Zona_Fit.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import gm.Zona_Fit.modelo.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {

}
