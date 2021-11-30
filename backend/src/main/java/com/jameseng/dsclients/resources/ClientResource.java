package com.jameseng.dsclients.resources;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jameseng.dsclients.entities.Client;

//ClientResource = recurso da classe Client
@RestController    //será um controlador REST que responde por este recurso
@RequestMapping(value="/clients")	//(value="/clients") = rota REST do recurso
public class ClientResource {

	//primeiro endpoint (primeira rota do recurso):
	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		List<Client> list=new ArrayList<>();
		//TIMESTAMP WITH TIME ZONE '2020-07-14T10:00:00Z'
		//Instant.now()
		//"2021-11-30T22:34:07.476305100Z"
		//"1990-10-21T15:30:00Z"
		//"1985-12-10T09:45:45Z"
		//1990-10-21'T'15: 30: 00.000Z
		list.add(new Client(1L, "Maria", "011.355.950-74", 5000.00, Instant.now(), 2));
		list.add(new Client(2L, "José", "050.755.624-52", 4000.00, Instant.now(), 1));
		return ResponseEntity.ok().body(list); //.ok() = deixa reponder resposta 200 = requisição realizada com sucesso
	}

}