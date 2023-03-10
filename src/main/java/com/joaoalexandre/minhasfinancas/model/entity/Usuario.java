package com.joaoalexandre.minhasfinancas.model.entity;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario" , schema = "financas")
public class Usuario {
	
	  @Id
      @Column(name = "id")
	  @GeneratedValue(strategy =  GenerationType.IDENTITY)
	  private Long id;
	  
      @Column(name = "nome")
	  private String nome;
	  
      @Column(name = "email")
	  private  String email;
      
	  @Column(name = "senha")
	  private String senha;
	
	  
}
