package br.com.lojavirtual.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lojavirtual.domain.Produto;
import br.com.lojavirtual.dto.ProdutoCarrinhoDTO;
import br.com.lojavirtual.dto.ProdutoDTO;
import br.com.lojavirtual.message.Mensagens;
import br.com.lojavirtual.service.ProdutoService;

@RestController
@RequestMapping("produto")
public class ProdutoController {
	
	List<ProdutoCarrinhoDTO> listaCarrinho = null;

	@Autowired
	ProdutoService produtoService;
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody ProdutoDTO produtoDTO) throws Exception {
		
		try {
			return new ResponseEntity<Produto>(produtoService.add(produtoDTO),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Mensagens.ERRO_INCLUIR_PRODUTO +e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 
	@PostMapping("/addCarrinho")
	public ResponseEntity<?> addCarrinho(@RequestBody String descricao) throws Exception {
		
		try {
			Produto produto = produtoService.findByDescricao(descricao);
			if(produto != null) {
			if(listaCarrinho == null) {
				listaCarrinho = new ArrayList<ProdutoCarrinhoDTO>();
			}
			
			ProdutoCarrinhoDTO produtoCarrinho = new ProdutoCarrinhoDTO();			
			
				produtoCarrinho.setId(produto.getId());
				produtoCarrinho.setDescricao(produto.getDescricao());
				produtoCarrinho.setMarca(produto.getMarca());
				produtoCarrinho.setQuantidade(1);
				listaCarrinho.add(produtoCarrinho);

			}else {
				return new ResponseEntity<String>(Mensagens.NAO_TEM_ESTOQUE,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return new ResponseEntity<List<ProdutoCarrinhoDTO>>(listaCarrinho,HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<String>(Mensagens.ERRO_INCLUIR_PRODUTO +e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}	


	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() throws Exception {
		try {
			return new ResponseEntity<List<Produto>>(produtoService.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Mensagens.ERRO_INESPERADO,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/remove")
	public  ResponseEntity<?> remove(@RequestBody Long id) throws Exception {
		
		try {
			produtoService.delete(id);
			return new ResponseEntity<Boolean>(true,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/findById/{id}")
	public  ResponseEntity<?> findById(@PathVariable Long id) throws Exception {
		
		try {
			return new ResponseEntity<Produto>(produtoService.findById(id).get(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Mensagens.ERRO_BUSCA_PRODUTO,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/findByDescricao")
	public  ResponseEntity<?> findByDescricao(@RequestParam String descricao) throws Exception {
		
		try {
			return new ResponseEntity<Produto>(produtoService.findByDescricao(descricao),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(Mensagens.ERRO_BUSCA_PRODUTO,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/comprarProduto")
	public ResponseEntity<?> updateProduto(@RequestParam String descricao) throws Exception {
		
		try {
			return new ResponseEntity<String>(produtoService.updateProduto(descricao),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/finalizarCarrinho")
	public ResponseEntity<?> finalizarCarrinho() throws Exception {
		
		try {
			for (int i = 0; i < listaCarrinho.size(); i++) {			
				produtoService.finalizarCarrinho(listaCarrinho.get(i));
			}
			return new ResponseEntity<String>(Mensagens.CARRINHO_SUCESSO,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/limparCarrinho")
	public ResponseEntity<?> limparCarrinho() throws Exception {
		String msg = "";
		try {
			if(listaCarrinho != null) {
				listaCarrinho.clear();	
				msg = Mensagens.CARRINHO_LIMPO;
			}else {
				msg = Mensagens.CARRINHO_SEM_ITENS;
			}
			
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public List<ProdutoCarrinhoDTO> getListaCarrinho() {
		return listaCarrinho;
	}

	public void setListaCarrinho(List<ProdutoCarrinhoDTO> listaCarrinho) {
		this.listaCarrinho = listaCarrinho;
	}
	
	}
