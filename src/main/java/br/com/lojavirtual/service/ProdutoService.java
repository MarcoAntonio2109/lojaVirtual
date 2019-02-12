package br.com.lojavirtual.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import br.com.lojavirtual.domain.Produto;
import br.com.lojavirtual.dto.ProdutoCarrinhoDTO;
import br.com.lojavirtual.dto.ProdutoDTO;
import br.com.lojavirtual.message.Mensagens;
import br.com.lojavirtual.repository.ProdutoRepository;

@Service
public class ProdutoService extends AbstractService<Produto, Long>   {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	ProdutoRepository produtoRepository;
	@Override
	public PagingAndSortingRepository<Produto, Long> getRepository() {
		// TODO Auto-generated method stub
		return produtoRepository;
	}



	public void carga() {
		List<Produto> lstProdutoCarga = new ArrayList<Produto>();
		
		Produto produto = new Produto();
		produto.setDescricao("Camiseta Azul");
		produto.setMarca("Marca1");
		produto.setQuantidade(2);
		
		lstProdutoCarga.add(produto);
		Produto produto2 = new Produto();
		produto2.setDescricao("Camiseta Verde");
		produto2.setMarca("Marca1");
		produto2.setQuantidade(0);
		
		lstProdutoCarga.add(produto2);
		Produto produto3 = new Produto();
		produto3.setDescricao("Camiseta Vermelha");
		produto3.setMarca("Marca3");
		produto3.setQuantidade(1);
		
		lstProdutoCarga.add(produto3);
		Produto produto4 = new Produto();
		produto4.setDescricao("Bermuda Azul");
		produto4.setMarca("Marca4");
		produto4.setQuantidade(2);
		
		lstProdutoCarga.add(produto4);
		Produto produto5 = new Produto();
		produto5.setDescricao("Bermuda Preto");
		produto5.setMarca("Marca5");
		produto5.setQuantidade(3);
		
		lstProdutoCarga.add(produto5);
		Produto produto6 = new Produto();
		produto6.setDescricao("Bermuda Vermelha");
		produto6.setMarca("Marca6");
		produto6.setQuantidade(3);
		
		lstProdutoCarga.add(produto6);
		Produto produto7 = new Produto();
		produto7.setDescricao("Bone Azul");
		produto7.setMarca("Marca7");
		produto7.setQuantidade(6);
		
		lstProdutoCarga.add(produto7);		
		Produto produto8 = new Produto();
		produto8.setDescricao("Bone Verde");
		produto8.setMarca("Marca7");
		produto8.setQuantidade(5);
		lstProdutoCarga.add(produto8);
		
		Produto produto9 = new Produto();
		produto9.setDescricao("Bone Preto");
		produto9.setMarca("Marca8");
		produto9.setQuantidade(2);
		lstProdutoCarga.add(produto9);

		saveAll(lstProdutoCarga);

	}

	public Produto findByDescricao(String descricao) {
		// TODO Auto-generated method stub
		 return produtoRepository.findByDescricaoIgnoreCase(descricao);
	}



	public Produto add(ProdutoDTO produtoDTO) {
		Produto produto = new Produto();
		
		produto.setDescricao(produtoDTO.getDescricao());
		produto.setMarca(produtoDTO.getMarca());
		produto.setQuantidade(produtoDTO.getQuantidade());
		
		save(produto);
		return produto;
	}

	public String updateProduto(String descricao) {
		Produto produto = findByDescricao(descricao);
		if(produto != null) {
			if(produto.getQuantidade() > 0) {
				produto.setQuantidade(produto.getQuantidade() -1);
				update(produto);
			}else {
				return new Exception(Mensagens.ESTOQUE_ZERADO).getMessage() ;
			}
			}else {
				return new Exception(Mensagens.NAO_TEM_ESTOQUE).getMessage() ;	
			}
			
		return Mensagens.COMPRA_SUCESSO;
	}

	public String finalizarCarrinho(ProdutoCarrinhoDTO produtoCarrinhoDTO) {
		return updateProduto(produtoCarrinhoDTO.getDescricao());
		
	}

	
}
