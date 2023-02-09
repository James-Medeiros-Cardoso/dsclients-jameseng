package com.jameseng.dsclients.services.validations;

import com.jameseng.dsclients.dto.UserUpdateDTO;
import com.jameseng.dsclients.entities.User;
import com.jameseng.dsclients.repositories.UserRepository;
import com.jameseng.dsclients.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {
    // <(anotation criada), (classe que receberá a anotation)>
    // anottation UserInsertValid será aplicada na classe UserInsertDTO

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest httpServletRequest; // informações da requisição

    @Override
    public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        @SuppressWarnings("unchecked")
        var uriVars = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long userId = Long.parseLong(uriVars.get("id")); // id da requisição

        // Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista

        User user = userRepository.findByEmail(dto.getEmail()); // returna null se não encontrar um usuário
        if (user != null && userId != user.getId()) {
            list.add(new FieldMessage("email", "O email " + dto.getEmail() + " email já existe."));
        }

        for (FieldMessage e : list) { // inserir na lista de erros do bean validation
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}