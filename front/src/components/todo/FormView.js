import React, { useContext, useRef, useState } from 'react';
import consumer from "./consumer";
import events from "./events";
import Store from "../../store"

export default ({ listId, todo }) => {
    const formRef = useRef(null);
    const { dispatch } = useContext(Store);
    const item = todo.item[listId] ? todo.item[listId] : {};
    const [state, setState] = useState(item);

    const onAdd = (event) => {
        event.preventDefault();

        const request = {
            name: state.name,
            id: null,
            completed: false
        };

        consumer.save(listId, request).then((response) => {
            if(response.ok){
                response.json().then((result) => {
                    dispatch(events.saved(listId, result));
                    setState({ name: "" });
                    formRef.current.reset();
                });
            }
           
        });

    }

    const onEdit = (event) => {
        event.preventDefault();

        const request = {
            name: state.name,
            id: item.id,
            completed: item.completed
        };

        consumer.update(listId, request).then((response) => {
            if(response.ok){
                response.json().then((result) => {
                    dispatch(events.updated(listId, result));
                    setState({ name: "" });
                    formRef.current.reset();
                });
            }
           
        });
    }

    return <form ref={formRef}>
        <div className="input-group">
            <input
                className="form-control"
                aria-describedby="button-addon2"
                type="text"
                name="name"
                placeholder="¿Qué piensas hacer?"
                defaultValue={item.name}
                onChange={(event) => {
                    setState({ ...state, name: event.target.value })
                }}  ></input>
            {item.id && <button className="btn btn-outline-secondary" type="button" id="button-addon2" onClick={onEdit}>Actualizar</button>}
            {!item.id && <button className="btn btn-outline-secondary" type="button" id="button-addon2" onClick={onAdd}>Crear</button>}
        </div>
    </form>
}