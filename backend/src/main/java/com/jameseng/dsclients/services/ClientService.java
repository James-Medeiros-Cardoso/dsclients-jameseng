//CAMADA DE SERVIÇO
package com.jameseng.dsclients.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jameseng.dsclients.dto.ClientDTO;
import com.jameseng.dsclients.entities.Client;
import com.jameseng.dsclients.repositories.ClientRepository;
import com.jameseng.dsclients.services.exceptions.DataBaseException;
import com.jameseng.dsclients.services.exceptions.ResourceNotFoundException;

@Service //registar esa classe como um componente que participa de um sistema de injeção de dependencia automatizado do Spring
public class ClientService {

	@Autowired //injeta uma instância gerenciada pelo Spring
	private ClientRepository repository;
	
	@Transactional(readOnly=true) //garante integridade na transação com o banco de dados | readOnly=true = evita looking do banco de dados.
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		/*PRIMEIRA IMPLEMENTAÇÃO:
		public List<Client> findAll()
		return repository.findAll();*/
		
		/*SEGUNDA IMPLEMENTAÇÃO:
		//declara um list do tipo Client que busca todos os clientes
		List<Client> list=repository.findAll();
		
		//declara uma list de ClientDTO
		List<ClientDTO> listDto=new ArrayList<>();
		
		//converte a list de Client em list de ClientDTO:
		for (Client cli : list) {
			listDto.add(new ClientDTO(cli));
		}
		return listDto;*/
		
		/*TERCEIRA IMPLEMENTAÇÃO:
		//declara um list do tipo Client que busca todos os clientes
		List<Client> list=repository.findAll();
		//declara uma list de ClientDTO
		List<ClientDTO> listDTO=list.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());
		//.stream() = transforma para stream
		//.map() = aplica uma função em cada elemento da lista (ou vetor)
		//.collect(Collectors.toList()) = transforma o stream novamente em uma lista
		return listDTO;*/
		
		Page<Client> list=repository.findAll(pageRequest);
		//repository = objeto responsável por acessar o banco de dados.
		
		return list.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly=true)
	public ClientDTO findById(Long id) {
		
		//Optional = evitar trabalhar com valor nulo
		Optional<Client> obj=repository.findById(id);
		
		//obter a entidade do obj
		//Se o Client não existir, o orElseThrow vai instanciar uma exceção
		Client entity=obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
		
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		
		//converter o objeto dto para entidade:
		Client entity=new Client();
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	
		//salvar a entidade (nela mesmo)
		entity=repository.save(entity);
		
		//transformar em um ClientDTO e retornar
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity=repository.getOne(id);
			entity.setName(dto.getName());
			entity.setCpf(dto.getCpf());
			entity.setIncome(dto.getIncome());
			entity.setBirthDate(dto.getBirthDate());
			entity.setChildren(dto.getChildren());
			
			//salvar a entidade (nela mesmo)
			entity=repository.save(entity);
			
			//transformar em um ClientDTO e retornar
			return new ClientDTO(entity);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found "+id);
		}
	}

	//método DELETE = não precisa do Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found "+id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation.");
		}
	}
}