//CAMADA DE ACESSO A DADOS
package com.jameseng.dsclients.repositories;

import com.jameseng.dsclients.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//já temos operações prontas de banco de dados H2 (MySQL, Polstgress)
@Repository //um componente injetavel pelo sistema de injeção do Spring
public interface RoleRepository extends JpaRepository<Role, Long> {

}
