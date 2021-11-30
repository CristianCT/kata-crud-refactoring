package co.com.sofka.crud.services;

import co.com.sofka.crud.DTO.TodoDTO;
import co.com.sofka.crud.DTO.TodoListDTO;
import co.com.sofka.crud.models.TodoListModel;
import co.com.sofka.crud.models.TodoModel;
import co.com.sofka.crud.repositories.TodoListRepository;
import co.com.sofka.crud.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TodoService {

    public static final String NO_FAULT_ID = "No existe el id de la lista";
    private TodoListRepository todoListRepository;
    private TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoListRepository todoListRepository, TodoRepository todoRepository) {
        this.todoListRepository = todoListRepository;
        this.todoRepository = todoRepository;
    }

    public Set<TodoDTO> getTodosByListId(Long id) {
        return todoListRepository.findById(id)
                .orElseThrow(() -> new NotFoundIdxception(NO_FAULT_ID))
                .getTodos().stream()
                .map(item -> new TodoDTO(item.getId(), item.getName(), item.isCompleted(), id))
                .collect(Collectors.toSet());
    }

    public TodoDTO addNewTodoByListId(Long listId, TodoDTO todoDTO) {
        var listTodo = todoListRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException(NO_FAULT_ID));
        var todo = new TodoModel();

        todo.setCompleted(todoDTO.isCompleted());
        todo.setName(Objects.requireNonNull(todoDTO.getName()));
        todo.setId(todoDTO.getId());

        if(todo.getName().isEmpty() || todo.getName().length() < 3){
            throw new TodoBusinessException("No valid entity To-Do to be save");
        }

        //addition new to-do
        listTodo.getTodos().add(todo);

        var listUpdated = todoListRepository.save(listTodo);
        //last item
        var lastToDo = listUpdated.getTodos()
                .stream()
                .max(Comparator.comparingInt(item -> item.getId().intValue()))
                .orElseThrow();
        todoDTO.setId(lastToDo.getId());
        todoDTO.setListId(listId);
        return todoDTO;
    }

    public TodoDTO updateATodoByListId(Long listId, TodoDTO todoDTO) {
        var listTodo = todoListRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException(NO_FAULT_ID));

        //edit to-do
        for(var item : listTodo.getTodos()){
            if(item.getId().equals(todoDTO.getId())){
                item.setCompleted(todoDTO.isCompleted());
                item.setName(Objects.requireNonNull(todoDTO.getName()));
                item.setId(Objects.requireNonNull(todoDTO.getId()));
            }
        }

        todoListRepository.save(listTodo);

        return todoDTO;
    }


    public TodoListDTO newListTodo(TodoListDTO todoListDTO) {
        var listTodo = new TodoListModel();
        listTodo.setName(Objects.requireNonNull(todoListDTO.getName()));
        if(listTodo.getName().isEmpty() || listTodo.getName().length() < 3){
            throw new ToDoBusinessException("No valid entity List To-Do to be save");
        }
        var id = todoListRepository.save(listTodo).getId();
        todoListDTO.setId(id);
        return todoListDTO;
    }

    public Set<TodoListDTO> getAllListTodos() {
        return StreamSupport
                .stream(todoListRepository.findAll().spliterator(), false)
                .map(todoList -> {
                    var listDTO = todoList.getTodos()
                            .stream()
                            .map(item -> new TodoDTO(item.getId(), item.getName(), item.isCompleted(), todoList.getId()))
                            .collect(Collectors.toSet());
                    return new TodoListDTO(todoList.getId(), todoList.getName(), listDTO);
                })
                .collect(Collectors.toSet());
    }

    public void deleteListById(Long listId){
        var listTodo = todoListRepository.findById(listId)
                .orElseThrow(() -> new NotFoundException(NO_FAULT_ID));
        todoListRepository.delete(listTodo);
    }

    public void deleteATodoById(Long id) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new NotFoundException(NO_FAULT_ID));
        todoRepository.delete(todo);
    }
}
