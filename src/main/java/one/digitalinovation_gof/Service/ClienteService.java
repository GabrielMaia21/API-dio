package one.digitalinovation_gof.Service;

import one.digitalinovation_gof.Model.Cliente;
import org.springframework.stereotype.Service;


public interface ClienteService {
    Iterable<Cliente> buscarTodos();

    Cliente  buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);
}
