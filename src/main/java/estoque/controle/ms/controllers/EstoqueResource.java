package estoque.controle.ms.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import estoque.controle.ms.entity.Estoque;
import estoque.controle.ms.repository.service.EstoqueService;

@RestController
@RequestMapping(value="/api/estoque")
public class EstoqueResource {

	private final static Logger log = LoggerFactory.getLogger(EstoqueResource.class);
	private EstoqueService estoqueService;

	EstoqueResource(EstoqueService estoqueService) {
       this.estoqueService = estoqueService;
	}

	@GetMapping
	public ResponseEntity<List<Estoque>> getEstoque() {
		log.info("EstoqueResource: iniciando processamento getEstoque()");
		
		try {
			List<Estoque> estoques = estoqueService.getAll();
			return new ResponseEntity<List<Estoque>>(estoques, HttpStatus.ACCEPTED);
			
		} catch (Exception e) {
			log.info("EstoqueResource: Erro ao executar getEstoque({})", e.getCause().toString());
			return new ResponseEntity<List<Estoque>>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		} finally {
			log.info("EstoqueResource: finalizando processamento getEstoque()");
		}
	}
	
	@GetMapping(path = {"/{id}"})
	public ResponseEntity<Estoque> getEstoquePorId(@PathVariable Integer id){
		log.info("EstoqueResource: iniciando processamento getEstoque(/{})", id);
		try {
			Estoque estoque = estoqueService.getById(id);
			if (estoque != null) 
				return new ResponseEntity<Estoque>(estoque, HttpStatus.ACCEPTED);
			else
				return new ResponseEntity<Estoque>(HttpStatus.NOT_FOUND);
						
		} catch (Exception e) {
			log.info("EstoqueResource: Erro ao executar putEstoque({})", e.getCause().toString());
			return new ResponseEntity<Estoque>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		} finally {
			log.info("EstoqueResource: finalizando processamento getEstoque(/{})", id);
		}
	}	
	
	@GetMapping(path = {"/empresa/{idEmpresa}"})
	public ResponseEntity<List<Estoque>> getEstoquePorEmpresa(@PathVariable Integer idEmpresa){
		log.info("EstoqueResource: iniciando processamento getEstoquePorEmpresa(/empresa/{})", idEmpresa);
		
		try {
			List<Estoque> estoquesPorEmpresa = estoqueService.getEstoquePorEmpresaId(idEmpresa);
			if(estoquesPorEmpresa == null || !estoquesPorEmpresa.isEmpty())
				return new ResponseEntity<List<Estoque>>(estoquesPorEmpresa, HttpStatus.ACCEPTED);
			else
				return new ResponseEntity<List<Estoque>>(HttpStatus.NOT_FOUND);
			
		} catch (Exception e) {
			log.info("EstoqueResource: Erro ao executar getEstoquePorEmpresa(/empresa/{})", e.getCause().toString());
			return new ResponseEntity<List<Estoque>>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		} finally {
			log.info("EstoqueResource: finalizando processamento getEstoquePorEmpresa(/empresa/{})", idEmpresa);			
		}
	}	
	
	@GetMapping(path = {"/produto/{idProduto}"})
	public ResponseEntity<List<Estoque>> getEstoquePorProduto(@PathVariable Integer idProduto){
		log.info("EstoqueResource: iniciando processamento getEstoquePorProduto(/produto/{})", idProduto);
		try {
			List<Estoque> estoques = estoqueService.getEstoquePorProdutoId(idProduto);
			
			if (estoques.isEmpty())
				return new ResponseEntity<List<Estoque>>(HttpStatus.NOT_FOUND); 
			
			return new ResponseEntity<List<Estoque>>(estoques, HttpStatus.ACCEPTED);
			
		} catch (Exception e) {
			log.info("EstoqueResource: Erro ao executar getEstoquePorProduto(/produto/{} - {})",idProduto, 
																								e.getCause().toString());
			return new ResponseEntity<List<Estoque>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			log.info("EstoqueResource: finalizando processamento getEstoquePorProduto(/produto/{})", idProduto);
		} 
	}	
	
	@GetMapping(path = {"/{idEmpresa}/{idProduto}"})
	public ResponseEntity<List<Estoque>> getEstoquePorEmpresaProduto(@PathVariable Integer idEmpresa,@PathVariable Integer idProduto){
		log.info("EstoqueResource: iniciando processamento getEstoquePorEmpresaProduto(/{}/{})", idProduto, idEmpresa);
		
		try {
			List<Estoque> estoques = estoqueService.getEstoquePorEmpresaProduto(idEmpresa, idProduto);
			
			if (estoques.isEmpty())
				return new ResponseEntity<List<Estoque>>(HttpStatus.NOT_FOUND); 
			
			return new ResponseEntity<List<Estoque>>(estoques, HttpStatus.ACCEPTED);
			
		} catch (Exception e) {
			log.info("EstoqueResource: Erro ao executar getEstoquePorEmpresaProduto(/{}/{} - {})",idProduto, idEmpresa, 
																								e.getCause().toString());
			return new ResponseEntity<List<Estoque>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			log.info("EstoqueResource: finalizando processamento getEstoquePorEmpresaProduto(/{}/{})", idProduto, idEmpresa);
		}  
	}	
	
	@PutMapping
	public ResponseEntity<Estoque> putEstoque(@RequestBody Estoque estoque){
		log.info("EstoqueResource: iniciando processamento putEstoque()");
		try {
			Estoque novoEstoque = estoqueService.save(estoque);
			return new ResponseEntity<Estoque>(novoEstoque, HttpStatus.ACCEPTED);
			
		} catch (Exception e) {
			log.info("EstoqueResource: Erro ao salvar estoque putEstoque({})", e.getCause().toString());
			return new ResponseEntity<Estoque>(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			log.info("EstoqueResource: finalizando processamento putEstoque()");
		}
	}
	
	@DeleteMapping(path = {"/{id}"})
	public ResponseEntity<?> deleteEstoque(@PathVariable Integer id){
		log.info("EstoqueResource: iniciando processamento deleteEstoque({})", id);
		
		try {
			if(estoqueService.delete(id))
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.info("EstoqueResource: Erro ao deletar estoque deleteEstoque({})", e.getCause().toString());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		} finally {
			log.info("EstoqueResource: finalizando processamento deleteEstoque({})", id);
		}
	}
}