package com.musicstreaming.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.musicstreaming.entities.User;
import com.musicstreaming.entities.UserArtist;
import com.musicstreaming.respository.UserRepository;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;


import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    UserRepository userRepository;

    private final WebClient.Builder webClientBuilder; 

    public UserRestController(WebClient.Builder webClientBuilder){
        this.webClientBuilder = webClientBuilder; 

    }

    // WebClient requires HttpClient library to work properly
    HttpClient client = HttpClient.create()
        // Connection Timeout: is a period within which a connection between a client and a server must be established
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .option(EpollChannelOption.TCP_KEEPIDLE, 300)
        .option(EpollChannelOption.TCP_KEEPINTVL, 60)
        // Response Timeout: The maximum time we wait to receive a response after sending a request
        .responseTimeout(Duration.ofSeconds(1))
        // Read and Write Timeout: A read timeout occurs when no data was read within a certain
        // period of time, while the write timeout when a write operation cannot finish at a specific time
        .doOnConnected(connection -> {
            connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
        });

    private String artistaName;

    @GetMapping("/findAll")
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public User getbyId(@PathVariable long id) {
        return userRepository.findById(id).get();
    }
    

    @PostMapping("/añadir")
    public ResponseEntity<User> postUser(@RequestBody User input) {

        if (input.getArtistas() != null) {
        input.getArtistas().forEach(ua -> ua.setUser(input));
        }
        User saved = userRepository.save(input);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }   

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User inputUser) {

        return userRepository.findById(id).map(user -> {
                    user.setName(inputUser.getName());
                    user.setPhone(inputUser.getPhone());
                    user.setPassword(inputUser.getPassword());
                    user.setDni(inputUser.getDni());

                    User updated = userRepository.saveAndFlush(user);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            userRepository.delete(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //recibo un ID del artista
    private String getArtistaName(long id){
        WebClient webClient = webClientBuilder //te devuelde un web client ya listo 
            .clientConnector(new ReactorClientHttpConnector(client))
            .baseUrl("http://localhost:8081/artista")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) //para transportar objetos JSON 
            .build();

        //aqui chat Gpt dice que hay meter DTO pero todavia no lo he visto 
        JsonNode block = webClient.method(HttpMethod.GET).uri("/"+id).retrieve().bodyToMono(JsonNode.class).block(); 

        String name = block.get("nombre").asText(); 
        return name; 
    }

    @GetMapping("/full")
    public User getByDni(@RequestParam String code) {
        User user = userRepository.findByDni(code); 
        List<UserArtist> artists = user.getArtistas();  
        artists.forEach(a->{
            artistaName = getArtistaName(a.getArtistaId()); 
            a.setNombreArtista(artistaName);
        });
        return user; 
    }
    

    
}
