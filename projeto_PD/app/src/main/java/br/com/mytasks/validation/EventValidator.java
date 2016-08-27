package br.com.mytasks.validation;

import android.content.Context;

import br.com.mytasks.dao.EventDAO;
import br.com.mytasks.entities.Event;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.exception.InvalidArgumentsException;


public class EventValidator {

    private EventDAO eventDAO;

    public EventValidator(Context context){
        eventDAO =new EventDAO(context);
    }
    /**
     * Verifica se um evento já existe no banco de dados
     * @param string nome do evento
     * @return true caso o evento já exista, e false, caso contrário
     * @throws InvalidArgumentsException excecao lancada quando os dados informados pelo usuário são inválidos
     */
    public boolean containsEvent(String string) throws InvalidArgumentsException {
        for(Event event : eventDAO.findAll()){
            if (string.equalsIgnoreCase(event.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se um evento já existe no banco de dados
     * @param id código de identificação unico do evento
     * @return true caso exista, e false, caso contrário
     * @throws ActivityManagerException excecao lancada quando a id passada não é encontrada
     */
    public boolean containsEvent(long id) throws ActivityManagerException {
        for (Event event: eventDAO.findAll()) {
            if (event.getId() == id){
                return true;
            }
        }
        throw new ActivityManagerException("Id doesn't exists.");
    }


}
