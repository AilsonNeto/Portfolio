package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	public User findById(Long id) {
		
		Optional<User> usuario = repository.findById(id);
		return usuario.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public User insert(User usuario) {
		return repository.save(usuario);
		
	}
	
	public void delete(Long id) {
		
		try {
			repository.deleteById(id);
		} catch (ResourceNotFoundException e) {
			
		}
	}
	
	public User update(Long id, User usuario) {
		try {
		User registro = repository.getReferenceById(id);
		updateData(registro, usuario);
		return repository.save(registro);
	
		}catch (RuntimeException e) {
			throw new ResourceNotFoundException(id);
		}
	}
		
	private void updateData(User registro, User usuario) {
		registro.setNome(usuario.getNome());
		registro.setEmail(usuario.getEmail());
		registro.setTelefone(usuario.getTelefone());
		
	}
}
