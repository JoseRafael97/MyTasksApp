package br.com.mytasks.util;


import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.mytasks.entities.Activity;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.service.ActivityService;

public class RepetitionsUtil {

    private  int numberOfRepetitions;
    private ArrayList<Activity> listActivities;

    public  RepetitionsUtil(){
        listActivities = new ArrayList<>();
    }


    public ArrayList<Activity> getActivitiesOfRepetitions(Activity activity){
        numberOfRepetitions = 7;
        if(activity.getRepetitions() > 0){
            for (int i = 0; i < activity.getRepetitions(); i++){
                Activity activity1 = new Activity();
                activity1.setName(activity.getName());
                activity1.setCategory(activity.getCategory());
                activity1.setFinished(activity.isFinished());
                activity1.setRepetitions(activity.getRepetitions());
                activity1.setPlannedHours(activity.getPlannedHours());
                activity1.setWorkedHours(activity.getWorkedHours());
                activity1.setDeadLine(getNewDeadLine(activity.getDeadLine()));
                listActivities.add(activity1);
            }
        }
        return listActivities;
    }

    private Date getNewDeadLine(Date date){
        Calendar deadLine = Calendar.getInstance();
        deadLine.setTime(date);

        deadLine.add(Calendar.DAY_OF_MONTH,numberOfRepetitions);

        numberOfRepetitions += 7;

        return deadLine.getTime();
    }

    public ArrayList<Activity> addRepetitionsInActivities(List<Activity> activities, Context context) throws ActivityManagerException {

        ActivityService activityService = new ActivityService(context);
        ArrayList<Activity> activities1 = new ArrayList<>();

        for (Activity ac : activityService.listAllActivities()){
            activities1.add(ac);
            for(Activity a : this.getActivitiesOfRepetitions(ac)){
                activities1.add(a);
            }
        }
        return  activities1;
    }
}
