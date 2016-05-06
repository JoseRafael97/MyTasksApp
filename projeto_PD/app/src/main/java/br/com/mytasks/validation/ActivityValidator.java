package br.com.mytasks.validation;

import android.content.Context;

import br.com.mytasks.dao.ActivityDAO;
import br.com.mytasks.entities.Activity;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.exception.IdDoesNotExistException;
import br.com.mytasks.exception.InvalidArgumentsException;


public class ActivityValidator {

    private ActivityDAO activityDAO;

    public ActivityValidator(Context context) {
        activityDAO = new ActivityDAO(context);
    }

    /**
     * Verifica se uma atividade já existe no banco de dados
     * @param string Nome da atividade
     * @return true caso exista, e false, caso contrario
     * @throws ActivityManagerException excecao lançada quando ocorre algum erro na solicitação
     */
    public boolean containsActivity(String string) throws ActivityManagerException {
        for (Activity activity : activityDAO.findAll()) {
            if (string.equalsIgnoreCase(activity.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se uma atividade já existe no banco de dados
     * @param string Nome da atividade
     * @return true caso exista, e false, caso contrario
     */
    public boolean containsActivity(String string, long id) {
        for (Activity activity : activityDAO.findAll()) {
            if (activity.getId() != id && string.equalsIgnoreCase(activity.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se o número de repetições inserido pelo usuário é válido
     * @param numberOfRepetitions Número de repetições da atividade
     * @throws InvalidArgumentsException excecao lancada quando o número de repeticoes é inválido
     */
    public void validateRepetitions(int numberOfRepetitions) throws InvalidArgumentsException {
        if (numberOfRepetitions < 0) {
            throw new InvalidArgumentsException("Invalid number of repetitions");
        }
    }

    /**
     * Verifica se uma atividade existe
     * @param id Código de identificação único da atividade
     * @return true caso a atividade exista
     * @throws IdDoesNotExistException excecao lancada caso a id não conste no banco
     */
    public boolean containsActivity( long id) throws IdDoesNotExistException {
        for (Activity activity : activityDAO.findAll()) {
            if (activity.getId() == id){
                return true;
            }
        }
        throw new IdDoesNotExistException("Id doesn't exists");
    }
}
