//CAMADA DE SERVIÇO
package com.jameseng.dsclients.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jameseng.dsclients.dto.ClientDTO;
import com.jameseng.dsclients.entities.Client;
import com.jameseng.dsclients.repositories.ClientRepository;
import com.jameseng.dsclients.services.exceptions.EntityNotFoundException;

@Service //registar esa classe como um componente que participa de um sistema de injeção de dependencia automatizado do Spring
public class ClientService {

	@Autowired //injeta uma instância gerenciada pelo Spring
	private ClientRepository repository;
	
	@Transactional(readOnly=true) //garante integridade na transação com o banco de dados | readOnly=true = evita looking do banco de dados.
	public List<ClientDTO> findAll() {
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
		
		List<Client> list=repository.findAll();
		//repository = objeto responsável por acessar o banco de dados.
		
		return list.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly=true)
	public ClientDTO findById(Long id) {
		
		//Optional = evitar trabalhar com valor nulo
		Optional<Client> obj=repository.findById(id);
		
		//obter a entidade do obj
		//Se o Client não existir, o orElseThrow vai instanciar uma exceção
		Client entity=obj.orElseThrow(() -> new EntityNotFoundException("Entity not Found"));
		
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
}