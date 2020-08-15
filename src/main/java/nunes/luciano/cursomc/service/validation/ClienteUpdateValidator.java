package nunes.luciano.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import nunes.luciano.cursomc.domain.Cliente;
import nunes.luciano.cursomc.dto.ClienteDTO;
import nunes.luciano.cursomc.repositories.ClienteRepository;
import nunes.luciano.cursomc.resources.exceptions.FildMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		
		List<FildMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista
		
		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		
		if (aux !=null && !aux.getId().equals(uriId)) {
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
