package com.makeit;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@SpringBootApplication
public class ReservationServiceApplication {
	
	@Bean
	CommandLineRunner runner(ReservationRepository rr){
		return args -> {
			Arrays.asList("A,B,C,D,E".split(",")).forEach(item -> rr.save(new Reservation(item)));
			rr.findAll().forEach(item -> System.out.println(item));
		};
			
	}

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}
}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long>{
	@RestResource(path="by-name")
	Collection<Reservation> findByName(@Param("name") String name);
}

@Entity
class Reservation{
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	
	Reservation(){}
	
	Reservation(String name){
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}
}