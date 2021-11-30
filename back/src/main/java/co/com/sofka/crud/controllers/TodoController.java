package co.com.sofka.crud.controllers;

import co.com.sofka.crud.DTO.TodoDTO;
import co.com.sofka.crud.DTO.TodoListDTO;
import co.com.sofka.crud.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping(value = "api/list")
    public Iterable<TodoListDTO> getAllListToDos(){
        return service.getAllListTodos();
    }

    @GetMapping(value = "api/{listId}/todos")
    public Iterable<TodoDTO> getToDosByListId(@PathVariable("listId") Long listId){

        return service.getTodosByListId(listId);

    }

    @PostMapping(value = "api/todolist")
    public TodoListDTO newListTodo(@RequestBody TodoListDTO todo){
        return service.newListTodo(todo);
    }

    @DeleteMapping(value = "api/{id}/todolist")
    public void deleteListById(@PathVariable("id") Long id){
        service.deleteListById(id);
    }

    @PutMapping(value = "api/{listId}/todo")
    public TodoDTO updateAToDoByListId(@PathVariable("listId") Long listId, @RequestBody TodoDTO todo){
        if(todo.getId() != null){
            return service.updateATodoByListId(listId, todo);
        }
        throw new RuntimeException("No existe el id para actualizar");
    }

    @PostMapping(value = "api/{listId}/todo")
    public TodoDTO addNewTodoByListId(@PathVariable("listId") Long listId, @RequestBody TodoDTO todo){
        return service.addNewTodoByListId(listId, todo);
    }

    @DeleteMapping(value = "api/{id}/todo")
    public void deleteATodoById(@PathVariable("id")Long id){
        service.deleteATodoById(id);
    }

}
