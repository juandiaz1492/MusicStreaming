package com.musicstreaming.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users",
  uniqueConstraints = {
    @UniqueConstraint(name = "uk_users_name", columnNames = "name"),
    @UniqueConstraint(name = "uk_users_dni", columnNames = "dni")
  }
)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  private String phone;

  private String password;

  @Column(nullable = false, unique = true)
  private String dni;

  // Relación interna (tabla puente) con IDs del microservicio artista
  //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
 //private List<String> artistasId = new ArrayList<>();
}
