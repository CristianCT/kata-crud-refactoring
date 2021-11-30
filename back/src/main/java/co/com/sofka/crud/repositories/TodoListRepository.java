package co.com.sofka.crud.repositories;

import co.com.sofka.crud.models.TodoListModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends CrudRepository<TodoListModel, Long> {
}
