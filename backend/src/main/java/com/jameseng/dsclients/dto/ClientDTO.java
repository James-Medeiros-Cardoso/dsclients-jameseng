package com.jameseng.dsclients.dto;

import com.jameseng.dsclients.entities.Client;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

public class ClientDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(min = 2, max = 120, message = "O campo 'name' deve ter entre 2 e 120 caracteres.")
    @NotEmpty(message = "O campo 'name' é obrigatório.")
    @NotBlank(message = "O campo 'name' não pode estar em branco.")
    private String name;

    @CPF(message = "O CPF digitado não é válido.")
    @NotEmpty(message = "O campo 'cpf' é obrigatório.")
    @NotBlank(message = "O campo 'cpf' não pode estar em branco.")
    private String cpf;

    @Positive(message = "O campo 'income' deve ser positivo.")
    @NotNull(message = "O campo 'income' não pode estar em branco.")
    private Double income;

    @PastOrPresent(message = "O campo 'birthDate' não pode estar no futuro.")
    @NotNull(message = "O campo 'birthDate' não pode estar em branco.")
    private Instant birthDate;

    @NotNull(message = "O campo 'children' não pode estar vazio.")
    private Integer children;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String name, String cpf, Double income, Instant birthDate, Integer children) {
        super();
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.income = income;
        this.birthDate = birthDate;
        this.children = children;
    }

    //contrudor que vai povoar a ClientDTO simplismente passando a entidade como argumento
    public ClientDTO(Client entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = entity.getCpf();
        this.income = entity.getIncome();
        this.birthDate = entity.getBirthDate();
        this.children = entity.getChildren();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }
}