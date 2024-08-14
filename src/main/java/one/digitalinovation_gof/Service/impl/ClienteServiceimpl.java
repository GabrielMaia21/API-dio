package one.digitalinovation_gof.Service.impl;

import one.digitalinovation_gof.Model.Cliente;
import one.digitalinovation_gof.Model.ClienteRepository;
import one.digitalinovation_gof.Model.Endereco;
import one.digitalinovation_gof.Model.EnderecoRepository;
import one.digitalinovation_gof.Service.ClienteService;
import one.digitalinovation_gof.Service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceimpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;


    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if(clienteBd.isPresent()){
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        Endereco endereco = enderecoRepository.findById(cliente.getEndereco().getCep()).orElseGet(()->{
            Endereco novoEndereco = viaCepService.consultarCep(cliente.getEndereco().getCep());
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}
