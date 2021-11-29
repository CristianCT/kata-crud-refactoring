package co.com.sofka.crud.services;

import co.com.sofka.crud.DTO.TodoDTO;
import co.com.sofka.crud.models.TodoModel;
import co.com.sofka.crud.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public Iterable<TodoModel> list(){
        return repository.findAll();
    }

    public TodoModel save(TodoModel todo){
        return repository.save(todo);
    }

    public void delete(Long id){
        repository.delete(get(id));
    }

    public TodoModel get(Long id){
         return repository.findById(id).orElseThrow();
    }
}
