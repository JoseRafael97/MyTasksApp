package br.com.mytasks.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.mytasks.dao.EventDAO;
import br.com.mytasks.entities.Event;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.exception.InvalidArgumentsException;
import br.com.mytasks.exception.InvalidDateException;
import br.com.mytasks.util.DateConversor;
import br.com.mytasks.validation.EventValidator;
import br.com.mytasks.validation.Validation;

public class EventService {

    private EventDAO eventDAO;
    private EventValidator eventValidator;

    public EventService() {
    }

    public EventService(Context context) {
        eventDAO = new EventDAO(context);
        eventValidator = new EventValidator(context);
    }

    /**
     * Cadastra um evento no banco de dados
     *
     * @param name        nome do evento a ser inserido
     * @param initialDate data em que começa o evento
     * @param finalDate   data em que termina o evento
     * @throws ActivityManagerException exceçao lançada caso haja algum erro
     */
    public void addEvent(String name, String initialDate, String finalDate)
            throws ActivityManagerException {
        try {
            Date initialDateConverted = DateConversor.stringToDateAndHourConversor(initialDate);
            Date finalDateConverted = DateConversor.stringToDateAndHourConversor(finalDate);
            Validation.nameValidation(name);
            eventValidator.containsEvent(name);
            eventDAO.save(new Event(name, initialDateConverted, finalDateConverted));

        } catch (InvalidDateException | InvalidArgumentsException e) {
            throw new ActivityManagerException(e.getMessage());
        }
    }

    /**
     * atualiza as informações de um evento
     *
     * @param id          identificação do evento
     * @param name        nome do evento a ser atualizado
     * @param initialDate data inicial do evento a ser atualizada
     * @param finalDate   data final do evento a ser atualizada
     * @throws ActivityManagerException exceção lançada caso haja algum erro na atualização
     */
    public void updateEvent(long id, String name, String initialDate, String finalDate)
            throws ActivityManagerException {
        try {
            Date initialDateConverted = DateConversor.stringToDateAndHourConversor(initialDate);
            Date finalDateConverted = DateConversor.stringToDateAndHourConversor(finalDate);
            Validation.nameValidation(name);
            eventValidator.containsEvent(id);
            eventDAO.update(new Event(name, initialDateConverted, finalDateConverted), id);
        } catch (InvalidDateException | InvalidArgumentsException e) {
            throw new ActivityManagerException(e.getMessage());
        }
    }

    /**
     * Lista as informações de um evento específico, caso ele exista
     *
     * @param id código de identificação único do evento
     * @return o evento
     */
    public Event findEventById(long id) throws ActivityManagerException {
        try {
            Validation.idValidation(id);
        } catch (InvalidArgumentsException e) {
            throw new ActivityManagerException("Invalid id");
        }
        eventValidator.containsEvent(id);
        return eventDAO.findById(id);
    }

    /**
     * Lista as informações de um evento localizado pela data
     *
     * @param date data em que o evento será realizado
     * @return o evento caso ele exista e null caso não exista
     */
    public Event findEventByDate(Date date) throws ActivityManagerException {
        List<Event> eventList = eventDAO.findAll();
        for (Event event : eventList) {
            if (event.getInitialDate().equals(date) || event.getFinalDate().equals(date)) {
                return event;
            }
        }
        throw new ActivityManagerException("No events found.");
    }

    /**
     * Lista todos os eventos cadastrados
     *
     * @return uma lista com todos os eventos.
     */
    public List<Event> findAll() throws ActivityManagerException {
        return eventDAO.findAll();
    }

    // metodo provisório so para funcionar o app.
    public List<Event> listEventName(List<String> list) {
        List<Event> eventList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Event event = findByName(list.get(i));
            if (event != null){
                eventList.add(event);
            }
        }
        return eventList;
    }

    public Event findByName(String name) {
        for (Event event : eventDAO.findAll()) {
            if (event.getName().equalsIgnoreCase(name)) {
                return event;
            }
        }
        return null;
    }
}