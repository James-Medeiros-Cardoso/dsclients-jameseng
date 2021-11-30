//CAMADA DE ACESSO A DADOS
package com.jameseng.dsclients.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jameseng.dsclients.entities.Client;

//já temos operações prontas de banco de dados H2 (MySQL, Polstgress)
@Repository //um componente injetavel pelo sistema de injeção do Spring
public interface ClientRepository extends JpaRepository<Client, Long> {

}
