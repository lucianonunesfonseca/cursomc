package nunes.luciano.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import nunes.luciano.cursomc.domain.Cliente;
import nunes.luciano.cursomc.domain.enums.TipoCliente;
import nunes.luciano.cursomc.dto.ClienteNewDTO;
import nunes.luciano.cursomc.repositories.ClienteRepository;
import nunes.luciano.cursomc.resources.exceptions.FildMessage;
import nunes.luciano.cursomc.service.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FildMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FildMessage("cpfOuCnpj","CPF invalido"));
		}
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FildMessage("cpfOuCnpj","CNPJ invalido"));
		}
		
		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		
		if (aux !=null) {
			list.add(new FildMessage("email", "O e-mail inserido ja existe"));
		}

		for (FildMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMsg()).addPropertyNode(e.getNome())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
