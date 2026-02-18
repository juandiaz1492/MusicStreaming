package com.musicstreaming.servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.musicstreaming.dto.ArtistResponse;
import com.musicstreaming.dto.UserRequest;
import com.musicstreaming.dto.UserResponse;
import com.musicstreaming.entities.User;

import com.musicstreaming.mapper.UserRequestMapper;
import com.musicstreaming.mapper.UserResponseMapper;
import com.musicstreaming.respository.UserRepository;



@Service
public class ServiciosControladorUser {
    @Autowired
    UserRepository userRepository;
    private final UserResponseMapper userResponseMapper; 
    private final UserRequestMapper userRequestMapper; 
    private final RestClient restClient;
    private final PasswordEncoder passwordEncoder;

    @Value("${artista.service.url}")
    private String artistaServiceUrl;



    public ServiciosControladorUser(UserResponseMapper userResponseMapper, UserRequestMapper userRequestMapper,RestClient restClient, PasswordEncoder passwordEncoder) {
        this.userRequestMapper = userRequestMapper; 
        this.userResponseMapper = userResponseMapper; 
        this.restClient = restClient; 
        this.passwordEncoder = passwordEncoder; 
    }
  

    //get all
    public ResponseEntity<List<UserResponse>> findAll() {
        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            return ResponseEntity.noContent().build(); 
        }

        List<UserResponse> response = userResponseMapper.toListUserResponse(users); 

       
        return ResponseEntity.ok(response); 
    }

    // get {id}
    public ResponseEntity<UserResponse> getbyId(long id) {
        Optional<User> find = userRepository.findById(id); 
        
        if(find.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }

        UserResponse response = userResponseMapper.toUserResonse(find.get()); 

        return ResponseEntity.ok(response); 
    }

    //añadir
    // añadir
public ResponseEntity<UserResponse> postUser(UserRequest input) {

    User usersave = userRequestMapper.UserRequestToUser(input);

    String passwordHasheada = passwordEncoder.encode(usersave.getPassword());
    usersave.setPassword(passwordHasheada);

    User save = userRepository.save(usersave);

    UserResponse response = userResponseMapper.toUserResonse(save);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}


    //update {id}
    public ResponseEntity<?> updateUser(Long id, UserRequest inputUser) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = userOpt.get();

        User input = userRequestMapper.UserRequestToUser(inputUser);

        user.setName(input.getName());
        user.setDni(input.getDni());
        user.setPhone(input.getPhone());
        user.setPassword(input.getPassword());

        User actualizado = userRepository.save(user);

        UserResponse response = userResponseMapper.toUserResonse(actualizado); 

        return ResponseEntity.ok(response);
    }



    // delete {id}    
    public ResponseEntity<?> deleteUser(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            userRepository.delete(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // get /username{username}
    public ResponseEntity<?> getbyNombreUser(String username) {
        Optional<User> find = userRepository.findByname(username);  
        if( find.isPresent()){
            return ResponseEntity.ok(find); 
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
    }

    //get full
    //falta tocar esto
    /* 
    public ResponseEntity<User> getByDni(String code) {
        User user = userRepository.findByDni(code);
        if (user != null) {
            List<UserArtist> artists = user.getArtistas();
            artists.forEach(a -> {
                String artistaNameLocal = getArtistaName(a.getArtistaId()); 
                a.setNombreArtista(artistaNameLocal); 
            });
        }
        return ResponseEntity.ok(user); 
    }
        

    //recibo un ID del artista
    private String getArtistaName(long id){
        WebClient webClient = webClientBuilder //te devuelde un web client ya listo 
            .clientConnector(new ReactorClientHttpConnector(client))
            .baseUrl("http://localhost:8081/artista")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) //para transportar objetos JSON 
            .build();

        //aqui chat Gpt dice que hay meter DTO pero todavia no lo he visto 
        JsonNode block = webClient.
                    method(HttpMethod.GET).
                    uri("/"+id).
                    retrieve().
                    bodyToMono(JsonNode.class).
                    block(); 

        if (block == null || block.get("nombre") == null) {
        return "Desconocido";   // o null si prefiere
        }
    return block.get("nombre").asText();
    }
    */ 


    public ResponseEntity<?> getArtistsOfUser(Long userId) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        String username = user.getName(); 

        ArtistResponse[] artistas = restClient.get()
            .uri(artistaServiceUrl + "/artista/by-user/{username}", username)
            .retrieve()
            .body(ArtistResponse[].class);

        if (artistas == null || artistas.length == 0) {
        return ResponseEntity.noContent().build();
        }

    return ResponseEntity.ok(List.of(artistas));
  }

}
