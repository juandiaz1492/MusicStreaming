package com.musicstreaming.dto;

import lombok.Data;

@Data
public class ArtistResponse {
  private Long id;
  private String name;
  private String fechaNacimiento;
  private String nacionalidad;
  private String username;
}