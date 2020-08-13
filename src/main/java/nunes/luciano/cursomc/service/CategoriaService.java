package nunes.luciano.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import nunes.luciano.cursomc.domain.Categoria;
import nunes.luciano.cursomc.repositories.CategoriaRepository;
import nunes.luciano.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria Find(Integer id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id:"+ id
				+", Tipo: "+ Categoria.class.getName()));
	}
	
	public Categoria Insert(Categoria obj) {
		obj.setId(null);
		return categoriaRepository.save(obj);
	}
	
	public Categoria Update(Categoria obj) {
		Find(obj.getId());
		return categoriaRepository.save(obj);
		
	}
	
	public void Delete(Integer id) {
		Find(id); 
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new nunes.luciano.cursomc.service.exceptions.DataIntegrityViolationException("Não é possivel excluir uma categoria que possui produtos");
		}
		
	}
}
