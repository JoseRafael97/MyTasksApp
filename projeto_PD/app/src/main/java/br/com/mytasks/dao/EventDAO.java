package br.com.mytasks.dao;

import android.content.Context;

import java.util.List;

import br.com.mytasks.entities.Event;
import br.com.mytasks.persistence.EventBD;
import br.com.mytasks.persistence.IManagerBD;


public class EventDAO implements IDAO<Event>{

    private IManagerBD<Event> managerBD;

    public EventDAO(Context context){ managerBD = new EventBD(context); }


    @Override
    public void save(Event instance) {
        managerBD.insert(instance);
    }

    @Override
    public void update(Event instance, long id) {
        managerBD.update(instance, id);
    }

    @Override
    public Event findById(long id) {
        Event event;
        List<Event> eventList;

        eventList = managerBD.findAll();

        for (int i = 0; i < eventList.size(); i++){
            if(id == eventList.get(i).getId()){
                event = eventList.get(i);
                return event;
            }
        }
        return null;
    }

    @Override
    public List<Event> findAll() {
        return managerBD.findAll();
    }

    @Override
    public void delete(long id) {managerBD.delete(id);}
        
}
