package co.com.sofka.crud.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "todolists")
public class TodoListModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TodoModel> todos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TodoModel> getTodos() {
        return todos;
    }

    public void setTodos(Set<TodoModel> todos) {
        this.todos = todos;
    }
}
