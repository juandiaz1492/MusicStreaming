package com.musicstreaming.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicstreaming.entities.User;



public interface UserRepository extends JpaRepository<User,Long>{
    public User findByDni(String dni); 
}
