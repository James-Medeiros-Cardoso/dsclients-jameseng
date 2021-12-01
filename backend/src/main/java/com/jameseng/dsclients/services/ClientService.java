//CAMADA DE SERVIÇO
package com.jameseng.dsclients.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jameseng.dsclients.dto.ClientDTO;
import com.jameseng.dsclients.entities.Client;
import com.jameseng.dsclients.repositories.ClientRepository;

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
		
		return list.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());
	}
}
