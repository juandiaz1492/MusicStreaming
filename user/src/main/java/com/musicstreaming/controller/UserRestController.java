package com.musicstreaming.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

import com.musicstreaming.dto.UserRequest;
import com.musicstreaming.servicios.ServiciosControladorUser;


@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private ServiciosControladorUser serviciosControladorUser; 

    private Environment env; 
     
    @GetMapping("/check")
    public String check() {
        return "Tu propiedad es: "+ env.getProperty("spring.profiles.active"); 
    }
    
    @GetMapping("/findAll")
    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll() {
        return serviciosControladorUser.findAll(); 
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getbyId(@PathVariable long id) {
        return serviciosControladorUser.getbyId(id); 
    }

    @GetMapping("/nombre/{nombreUser}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getbyNombreUser(@PathVariable String nombreUser) {
        return serviciosControladorUser.getbyNombreUser(nombreUser); 
    }

    @PostMapping("/añadir")
    public ResponseEntity<?> postUser(@RequestBody UserRequest input) {
        return serviciosControladorUser.postUser(input); 
    }   

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequest inputUser) {
        return serviciosControladorUser.updateUser(id, inputUser); 
    }
   
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return serviciosControladorUser.deleteUser(id); 
    }

    /* 
    @GetMapping("/full")
    public ResponseEntity<User> getByDni(@RequestParam String code) {
        return serviciosControladorUser.getByDni(code); 
    }
    
    */

    @GetMapping("artistas/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getArtistasbyId(@PathVariable long id) {
        return serviciosControladorUser.getArtistsOfUser(id); 
    }

    
}
