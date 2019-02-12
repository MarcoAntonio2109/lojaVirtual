package br.com.lojavirtual.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.lojavirtual.domain.Produto;

@Repository
@Transactional
public interface  ProdutoRepository  extends JpaRepository<Produto, Long>{

	Produto findByDescricaoIgnoreCase(String descricao);

}
