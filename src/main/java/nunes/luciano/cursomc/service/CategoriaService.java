package nunes.luciano.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nunes.luciano.cursomc.domain.Categoria;
import nunes.luciano.cursomc.repositories.CategoriaRepository;
import nunes.luciano.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id:"+ id
				+", Tipo: "+ Categoria.class.getName()));
	}
	
	public Categoria Insert(Categoria obj) {
		obj.setId(null);
		return categoriaRepository.save(obj);
	}
}
