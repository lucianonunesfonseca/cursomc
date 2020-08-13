package nunes.luciano.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import nunes.luciano.cursomc.domain.Categoria;
import nunes.luciano.cursomc.domain.Cidade;
import nunes.luciano.cursomc.domain.Cliente;
import nunes.luciano.cursomc.domain.Endereco;
import nunes.luciano.cursomc.domain.Estado;
import nunes.luciano.cursomc.domain.Produto;
import nunes.luciano.cursomc.domain.enums.TipoCliente;
import nunes.luciano.cursomc.repositories.CategoriaRepository;
import nunes.luciano.cursomc.repositories.CidadeRepository;
import nunes.luciano.cursomc.repositories.ClienteRepository;
import nunes.luciano.cursomc.repositories.EnderecoRepository;
import nunes.luciano.cursomc.repositories.EstadoRepository;
import nunes.luciano.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria  cat1 = new Categoria(null,"Informatica");
		Categoria  cat2 = new Categoria(null,"Escritorio");
		
		Produto p1 = new Produto(null, "computador", 2000.00);
		Produto p2 = new Produto(null, "impressora", 800.00);
		Produto p3 = new Produto(null, "mouse", 80.00);
		
		Estado estado1 = new Estado(null,"Minas Gerais");
		Estado estado2 = new Estado(null,"São Paulo");
		
		Cidade cidade1 = new Cidade(null,"Uberlândia",estado1);
		Cidade cidade2 = new Cidade(null,"São Paulo",estado2);
		Cidade cidade3 = new Cidade(null,"Campinas",estado2);
		
		Cliente cliente1 = new Cliente(null,"Luciano Nunes","lucianonunesfonseca@gmail.com","012.389.341-02",TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("(62)99845-2828","(62)3375-7089"));
		
		Endereco endereco1 = new Endereco(null,"rua cordeiro Qd B lote 12","s/n","Fundo guirin","Jardim Cabral", "76630-000",cliente1,cidade1);
		Endereco endereco2 = new Endereco(null, "rua A ", "18", "Apartamento", "Feliz", "74024-000", cliente1, cidade2);
		
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1,endereco2));
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1,cidade2, cidade3));
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
		
	}
	
	

}
