package br.com.mytasks.util;

import android.content.Context;

import java.util.Calendar;
import java.util.List;

import br.com.mytasks.entities.Activity;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.service.ActivityService;


public class GetWorkedHours {

    private ActivityService activityService;

    public GetWorkedHours(Context context) {
        activityService = new ActivityService(context);
    }

    public float getAllWorkedHoursPerMonth(int month) {
        float workedHours = 0;
        float hourFloat = 0;
        try {
            List<Activity> listActivitty = activityService.listAllActivities();
            for (Activity activity : listActivitty) {
                if (month == getMonth(activity)) {
                    String hour = DateConversor.hourStringConversor(activity.getWorkedHours());
                    hourFloat = splitDate(hour);
                }
                workedHours += hourFloat;
                return workedHours;
            }
        } catch (ActivityManagerException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static float splitDate(String string) {

        String replace = string.replace(':', '.');

        float horaC = Float.parseFloat(replace);

        return horaC;
    }

    private static int getMonth(Activity activity) {
        int month = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.setTime(activity.getDeadLine());
        month = calendar.get(Calendar.MONTH);
        return month;
    }
}
