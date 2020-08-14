package nunes.luciano.cursomc.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import nunes.luciano.cursomc.domain.Cidade;
import nunes.luciano.cursomc.domain.Cliente;
import nunes.luciano.cursomc.domain.Endereco;
import nunes.luciano.cursomc.domain.enums.TipoCliente;
import nunes.luciano.cursomc.dto.ClienteDTO;
import nunes.luciano.cursomc.dto.ClienteNewDTO;
import nunes.luciano.cursomc.repositories.ClienteRepository;
import nunes.luciano.cursomc.repositories.EnderecoRepository;
import nunes.luciano.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente Find(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente Insert(Cliente obj) {
		obj.setId(null);
		clienteRepository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());

		return obj;

	}

	public Cliente Update(Cliente obj) {

		Cliente newObjt = Find(obj.getId());
		updateData(newObjt, obj);
		return clienteRepository.save(newObjt);

	}

	public void Delete(Integer id) {
		Find(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new nunes.luciano.cursomc.service.exceptions.DataIntegrityViolationException(
					"Não é possivel excluir um Cliente que possui pedidos");
		}

	}

	public List<Cliente> FindAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> finPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) {

		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cliente = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()));
		Cidade cidade = new Cidade(objDto.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objDto.getTelefone());
		if (objDto.getTelefone1() != null) {
			cliente.getTelefones().add(objDto.getTelefone1());
		}
		if (objDto.getTelefone2() != null) {
			cliente.getTelefones().add(objDto.getTelefone2());
		}
		return cliente;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
