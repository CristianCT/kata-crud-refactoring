import React, { useContext, useState, useRef } from 'react';
import consumer from "./consumer";
import events from "./events";
import Store from "../../store"

export default () => {
    const { dispatch } = useContext(Store);
    const formRef = useRef(null);
    const [state, setState] = useState({ name: "" });

    const onCreate = (event) => {
        event.preventDefault();
        consumer.save({ name: state.name, id: null })
            .then((response) => {
                if (response.ok) {
                    response.json().then((newList) => {
                        dispatch(events.saved(newList));
                        formRef.current.reset();
                        setState({ name: "" })
                    })
                }
            });

    };

    return <form ref={formRef}>
        <div className="card">
            <div className="card-header text-center">Create List</div>
            <div className="input-group card-body">
                <input
                    className="form-control"
                    aria-describedby="button-addon1"
                    type="text"
                    name="name"
                    placeholder="TODOS LIST"
                    onChange={(event) => {
                        setState({ name: event.target.value })
                    }}  ></input>
                <button onClick={onCreate} className="btn btn-outline-secondary" type="button" id="button-addon1">Nueva lista</button>
            </div>
        </div>
    </form>
};
