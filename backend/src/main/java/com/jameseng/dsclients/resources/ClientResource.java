package com.jameseng.dsclients.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jameseng.dsclients.dto.ClientDTO;
import com.jameseng.dsclients.services.ClientService;

//ClientResource = recurso da classe Client
@RestController    //será um controlador REST que responde por este recurso
@RequestMapping(value="/clients")	//(value="/clients") = rota REST do recurso
public class ClientResource {

	@Autowired //injeta automaticamente a dependendia gerenciada pelo Spring
	private ClientService service; 
	
	//primeiro endpoint (primeira rota do recurso - mostrar todos os clientes):
	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll( //parâmetros opcionais:
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,	
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
			) {
		
		/*List<Client> list=new ArrayList<>();
		//Instant.now()
		list.add(new Client(1L, "João", "011.355.950-74", 5000.00, Instant.parse("1990-06-25T15:45:00Z"), 2));
		list.add(new Client(2L, "José", "050.755.624-52", 4000.00, Instant.parse("1985-11-08T09:10:30Z"), 1));*/
		
		//public ResponseEntity<List<ClientDTO>> findAll()
		//List<ClientDTO> list=service.findAll();
		//return ResponseEntity.ok().body(list); //.ok() = deixa reponder resposta 200 = requisição realizada com sucesso
		
		PageRequest pageRequest=PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<ClientDTO> list=service.findAllPaged(pageRequest);
		
		return ResponseEntity.ok().body(list); //.ok() = deixa reponder resposta 200 = requisição realizada com sucesso
	}
	
	//segundo endpoint (mostrar cliente por id):
	@GetMapping(value="/{id}") //acrescentar o id na rota
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
		//@PathVariabl = para o spring entender que deve vincular o id da rota com o id do finById
		ClientDTO dto=service.findById(id);
		return ResponseEntity.ok().body(dto); //.ok() = deixa reponder resposta 200 = requisição realizada com sucesso
	}
	
	//Inserir no banco um novo cliente (INSERT) | @RequestBody = endpoint reconhecer objeto enviado na requisição e vincule o dto*/
	@PostMapping //padrão REST = inserir um novo recurso = método POST
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO dto){
		dto=service.insert(dto);
		URI uri=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto); //código 201 = recurso criado
	}
	
	//Atualizar um Client por Id (PUT)
	@PutMapping(value="/{id}") //acrescentar o id na rota
	public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO dto){
		dto=service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	//Deletar um Client por Id (DELETE)
	@DeleteMapping(value="/{id}") //acrescentar o id na rota
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build(); //vai dar uma resposta 204 = deu certo e o corpo da resposta será vazio
	}	
	
	
}