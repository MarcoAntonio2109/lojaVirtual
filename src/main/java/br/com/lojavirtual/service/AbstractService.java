package br.com.lojavirtual.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;


public abstract class AbstractService<T, PK extends Serializable> implements Serializable {
	@Transactional(readOnly = true)
	public List<T> findAll() throws ServiceException {
		try {
			return (List<T>) getRepository().findAll();

		} catch (Exception ex) {
			throw new ServiceException("Erro ao Buscar Todos", ex);

		}
	}

 
	@Transactional(readOnly = true) 
	public Page<T> findAll(int page, int pageSize, Sort sort) throws ServiceException {
		try {
			return (Page<T>) getRepository().findAll( );
		} catch (Exception ex) {
			throw new ServiceException("Erro ao Buscar Todos", ex);

		}
	}
 
	public Optional<T> findById(PK id) throws ServiceException {
		try {

			return getRepository().findById(id);

		} catch (Exception ex) {
			throw new ServiceException("Erro ao Buscar", ex);
		}
	}
 
	public void save(T entity) throws ServiceException {
		try {
			getRepository().save(entity);
		} catch (Exception ex) {
			throw new ServiceException("Erro ao gravar"+ex.getMessage(), ex);
		}
	}

    
    public void saveAll( List<T> entities ) throws ServiceException {
        try {
            getRepository().saveAll(entities);
        } catch (Exception ex) {
            throw new ServiceException("Erro ao gravar", ex);
        }
    }

	 
	public void update(T entity) throws ServiceException {
		try {
			getRepository().save(entity);
		} catch (Exception ex) {
			throw new ServiceException("Erro ao alterar", ex);
		}
	}

 
	public void delete(T entity) throws ServiceException {
		try {
			getRepository().delete(entity);
		} catch (Exception ex) {
			throw new ServiceException("Erro ao Deletar", ex);
		}
	}

 
    public void deleteAll( List<T> entities ) throws ServiceException {
        try {
            getRepository().deleteAll(entities);
        } catch (Exception ex) {
            throw new ServiceException("Erro ao deletar ", ex);
        }
    }
 
	public void delete(PK id) throws ServiceException {
		try {
			getRepository().deleteById(id);
		} catch (Exception ex) {
			throw new ServiceException("Erro ao deletar", ex);
		}
	}

	public abstract PagingAndSortingRepository<T, PK> getRepository();
}
