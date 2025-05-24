package com.picpayproject.apiresfull.services;


import com.picpayproject.apiresfull.domain.user.User;
import com.picpayproject.apiresfull.domain.user.UserType;
import com.picpayproject.apiresfull.dtos.UserDTO;
import com.picpayproject.apiresfull.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validadeTransaction(User sender, BigDecimal amount) throws Exception { // se o for usuário lojista não poderá fazer transação
        if (sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuário do tipo lojista não está autorizado a realizar transação");
        }

        if (sender.getBalance().compareTo(amount) < 0) { // se o valor do balance for menor do que o amount retornará, "Saldo insuficiente".
            throw new Exception("Saldo insuficiente");

        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));

    }


    public User createUser(UserDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers(){
        return this.repository.findAll();

    }

    public void saveUser(User user){
        this.repository.save(user);
    }


}

