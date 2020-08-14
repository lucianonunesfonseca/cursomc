package nunes.luciano.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import nunes.luciano.cursomc.domain.Categoria;
import nunes.luciano.cursomc.dto.CategoriaDTO;
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
		
		Categoria newObjt = Find(obj.getId());
		updateData(newObjt,obj);
		return categoriaRepository.save(newObjt);
		
	}
	
	public void Delete(Integer id) {
		Find(id); 
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new nunes.luciano.cursomc.service.exceptions.DataIntegrityViolationException("Não é possivel excluir uma categoria que possui produtos");
		}
		
	}
	
	public List<Categoria> FindAll(){
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> finPage(Integer page, Integer linesPerPage,String orderBy, String direction ){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(),objDto.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
		
	}
}
