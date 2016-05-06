package br.com.mytasks.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.mytasks.dao.ActivityDAO;
import br.com.mytasks.entities.Activity;
import br.com.mytasks.entities.Category;
import br.com.mytasks.entities.Event;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.exception.IdDoesNotExistException;
import br.com.mytasks.exception.InvalidArgumentsException;
import br.com.mytasks.exception.InvalidDateException;
import br.com.mytasks.util.DateConversor;
import br.com.mytasks.util.RepetitionsUtil;
import br.com.mytasks.validation.ActivityValidator;
import br.com.mytasks.validation.DateValidation;
import br.com.mytasks.validation.Validation;


public class ActivityService {

    private ActivityDAO activityDAO;
    private ActivityValidator activityValidator;
    private RepetitionsUtil repetitionsUtil;
    private Context context;

    public ActivityService(Context context) {
        this.context = context;
        activityDAO = new ActivityDAO(context);
        activityValidator = new ActivityValidator(context);
        repetitionsUtil = new RepetitionsUtil();
    }

    public ActivityService() {
    }

    /**
     * Adiciona uma nova atividade no banco de dados
     *
     * @param name nome da atividade
     * @param deadLine finalização da atividade
     * @param plannedHours horas planejadas
     * @param category categoria a que essa atividade pertence
     * @param repetitions número de repetições
     * @param finished indica se a atividade foi finalizada ou não
     * @param eventList lista de evento que a atividade contém
     * @throws ActivityManagerException
     * @throws InvalidArgumentsException lançada quano um argumento for inválido
     * @throws InvalidDateException lançada quando uma data for inválida
     */
    public void addActivity(String name, String deadLine, Date plannedHours,
                            Category category, int repetitions, boolean finished, List<Event> eventList) throws ActivityManagerException,
            InvalidArgumentsException, InvalidDateException {

        Date date;
        Validation.nameValidation(name);
        date = DateConversor.stringToDateConversor(deadLine);
        activityValidator.validateRepetitions(repetitions);
        if (activityValidator.containsActivity(name)) {
            throw new ActivityManagerException("Activity already exists.");
        } else {

            Activity activity = new Activity(name, date, plannedHours, category, repetitions, finished, eventList);
            activityDAO.save(activity);

        }
    }

    /**
     * Atualiza uma atividade
     *
     * @param id identificação única da atividade a ser atualizada
     * @param name nome da atividade a ser atualizada
     * @param deadLine data limite para conclusão da atividade
     * @param plannedHours horas planejadas
     * @param workedHours quantidade de horas trabalhadas
     * @param category categoria a que pertence a atividade
     * @param repetitions numero de repetições
     * @param finished indica se a atividade foi finalizada
     * @param listEvent lista de eventos q a atividade contem
     * @throws ActivityManagerException
     * @throws InvalidDateException lançada quando informar data invalida
     *
     */
    public void updateActivity(long id, String name, String deadLine, Date plannedHours, Date workedHours,
                               Category category, int repetitions, boolean finished, List<Event> listEvent) throws ActivityManagerException, InvalidDateException {
        try {
            Validation.nameValidation(name);
            Date date = DateConversor.stringToDateConversor(deadLine);
            Validation.validateCategory(category);
            if (activityValidator.containsActivity(name, id)) {
                throw new ActivityManagerException("Activity exists.");
            } else {
                Activity newActivity = new Activity(name, date, plannedHours, workedHours,
                        category, repetitions, finished, listEvent);
                activityDAO.update(newActivity, id);
            }
        } catch (InvalidArgumentsException e) {
            throw new ActivityManagerException(e.getMessage());
        }
    }

    /**
     * Remove uma atividade previamente cadastrada
     *
     * @param id Código de identificação único da atividade
     * @throws IdDoesNotExistException
     */

    public void removeActivity(long id) throws IdDoesNotExistException {
        activityValidator.containsActivity(id);
        activityDAO.delete(id);
    }

    /**
     * Lista todas as atividades cadastradas
     *
     * @return Uma lista com todas as atividades
     * @throws ActivityManagerException
     */
    public List<Activity> listAllActivities() throws ActivityManagerException {
        return activityDAO.findAll();
    }

    public List<Activity> activitiesWithRepetitions() throws ActivityManagerException {
        return repetitionsUtil.addRepetitionsInActivities(listAllActivities(), context);
    }

    /**
     * Encontra uma atividade pelo id
     *
     * @param id Código de identificação único
     * @return a atividade
     * @throws IdDoesNotExistException
     */
    public Activity findActivityById(long id) throws IdDoesNotExistException {
        activityValidator.containsActivity(id);
        return activityDAO.findById(id);

    }

    /**
     * Lista a atividade pela data passada como parâmetro
     *
     * @param date data da atividade que se deseja procurar
     * @return Lista com as atividades marcadas para aquela data
     * @throws ActivityManagerException
     */
    public List<Activity> findByDate(Date date) throws ActivityManagerException {

        ArrayList<Activity> list = new ArrayList<>();

        for (Activity activity : activitiesWithRepetitions()) {
            if (DateValidation.equalsDateForDay(activity.getDeadLine(), date)) {
                list.add(activity);
            }
        }
        return list;
    }
}
