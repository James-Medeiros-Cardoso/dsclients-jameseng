//CAMADA DE SERVIÇO
package com.jameseng.dsclients.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jameseng.dsclients.entities.Client;
import com.jameseng.dsclients.repositories.ClientRepository;

@Service //registar esa classe como um componente que participa de um sistema de injeção de dependencia automatizado do Spring
public class ClientService {

	@Autowired //injeta uma instância gerenciada pelo Spring
	private ClientRepository repository;
	
	@Transactional(readOnly=true) //garante integridade na transação com o banco de dados | readOnly=true = evita looking do banco de dados.
	public List<Client> findAll() {
		return repository.findAll();
	}
	
}
