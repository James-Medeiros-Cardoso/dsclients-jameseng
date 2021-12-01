package com.jameseng.dsclients.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jameseng.dsclients.dto.ClientDTO;
import com.jameseng.dsclients.services.ClientService;

//ClientResource = recurso da classe Client
@RestController    //será um controlador REST que responde por este recurso
@RequestMapping(value="/clients")	//(value="/clients") = rota REST do recurso
public class ClientResource {

	@Autowired //injeta automaticamente a dependendia gerenciada pelo Spring
	private ClientService service; 
	
	//primeiro endpoint (primeira rota do recurso):
	@GetMapping
	public ResponseEntity<List<ClientDTO>> findAll() {
		
		/*List<Client> list=new ArrayList<>();
		//Instant.now()
		list.add(new Client(1L, "João", "011.355.950-74", 5000.00, Instant.parse("1990-06-25T15:45:00Z"), 2));
		list.add(new Client(2L, "José", "050.755.624-52", 4000.00, Instant.parse("1985-11-08T09:10:30Z"), 1));*/
		
		List<ClientDTO> list=service.findAll();
		return ResponseEntity.ok().body(list); //.ok() = deixa reponder resposta 200 = requisição realizada com sucesso
	}

}