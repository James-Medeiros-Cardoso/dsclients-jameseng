//CAMADA DE SERVIÇO
package com.jameseng.dsclients.services;

import com.jameseng.dsclients.dto.RoleDTO;
import com.jameseng.dsclients.dto.UserDTO;
import com.jameseng.dsclients.dto.UserInsertDTO;
import com.jameseng.dsclients.dto.UserUpdateDTO;
import com.jameseng.dsclients.entities.Role;
import com.jameseng.dsclients.entities.User;
import com.jameseng.dsclients.repositories.UserRepository;
import com.jameseng.dsclients.services.exceptions.DataBaseException;
import com.jameseng.dsclients.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> list = userRepository.findAll(pageable);
        return list.map(x -> new UserDTO(x));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        User user = obj.orElseThrow(() -> new ResourceNotFoundException("UserService/Entity not Found. Id = " + id));
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO userDto) {
        User user = new User();
        copyDtoToEntity(userDto, user);
        user = userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO userDto) {
        try {
            User user = userRepository.getOne(id);
            copyDtoToEntity(userDto, user);
            user = userRepository.save(user);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("UserService/Entity not Found. Id = " + id);
        }
    }

    //método DELETE = não precisa do Transactional
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("UserService/Entity not Found. Id = " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation.");
        }
    }

    private void copyDtoToEntity(UserDTO userDto, User user) {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        user.getRoles().clear();
        for (RoleDTO roleDto : userDto.getRoles()) {
            user.getRoles().add(new Role(roleDto.getId(), roleDto.getAuthority()));
        }
    }
}