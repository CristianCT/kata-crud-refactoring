import React, { useState, useContext, useEffect } from 'react';
import ToDoForm from "../todo/FormView";
import ToDoList from "../todo/ListView";
import consumer from "./consumer";
import events from "./events";
import Store from "../../store"

export default () => {
    const { state: { list, todo }, dispatch } = useContext(Store);
    const [isLoaded, setLoaded] = useState(false);
    useEffect(() => {
        consumer.findAll().then((response) => {
            if(response.ok) {
                response.json().then((list) => {
                    dispatch(events.finded(list));
                    console.log("successful list");
                });
            }
            setLoaded(true);
        })
    }, [dispatch]);

    const onDelete = (listId) => {
        consumer.delete(listId).then((response) => {
            if(response.ok) {
                dispatch(events.deleted(listId));
            }
        })
    };

    return <div className="container-cards">
        {!isLoaded && <div>Loading...</div>}
        {list.elements.length === 0 && <div>empty list!</div>}
        {list.elements.map((element) => {
            return <div className="card todo-list" key={element.id} id={"list-todo-"+element.id}>
                <fieldset>
                    <legend className="card-header">{element.name.toUpperCase()}</legend>
                    <ToDoForm listId={element.id} todo={todo} />
                    <ToDoList listId={element.id} todo={todo} />
                </fieldset>
                <div className="d-grid gap-2">
                    <button className="btn btn-danger btn-block" onClick={() => onDelete(element.id)}>Eliminar</button>
                </div>
            </div>
        })}
    </div>
}