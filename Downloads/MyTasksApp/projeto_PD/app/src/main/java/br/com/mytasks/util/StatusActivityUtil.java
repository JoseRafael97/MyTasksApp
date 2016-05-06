package br.com.mytasks.util;

import java.util.Date;

import br.com.mytasks.entities.Activity;
import br.com.mytasks.exception.ActivityManagerException;


public class StatusActivityUtil {

    public int getAtivitiesStatus(Activity activity) throws ActivityManagerException {

        Date date = new Date();
        date.getTime();
        int flag;

            // atividade finalizada a tempo
            if(activity.getDeadLine().before(date) && activity.isFinished()){
                flag = 1;
            }

           else if (activity.getDeadLine().after(date) && activity.isFinished()){
                flag = 1;
            }
            //atividade falhou
            else if(date.after(activity.getDeadLine()) && !activity.isFinished()){
                flag = -1;
            }
            //atividade em andamento
            else{
                flag = 0;
            }

        return flag;
    }


}
