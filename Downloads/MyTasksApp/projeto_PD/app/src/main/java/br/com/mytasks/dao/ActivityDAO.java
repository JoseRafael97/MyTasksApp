package br.com.mytasks.dao;

import android.content.Context;

import java.util.List;

import br.com.mytasks.entities.Activity;
import br.com.mytasks.persistence.ActivityBD;
import br.com.mytasks.persistence.IManagerBD;


public class ActivityDAO implements IDAO<Activity> {

    private IManagerBD<Activity> managerBD;

    public ActivityDAO (Context context) {
        managerBD = new ActivityBD(context);
    }

    @Override
    public void save(Activity instance) {
        managerBD.insert(instance);
    }

    @Override
    public void update(Activity instance, long id) {
        managerBD.update(instance, id);
    }

    @Override
    public Activity findById(long id)  {
        Activity activity = null;
        List<Activity> activityList;

        activityList = managerBD.findAll();

        for (int i = 0; i < activityList.size(); i++) {
            if (id == activityList.get(i).getId()) {
                activity = activityList.get(i);
                return activity;
            }
        }
        return activity;
    }

    @Override
    public List<Activity> findAll() {
        List<Activity> fActivityList;

        fActivityList = managerBD.findAll();

        return fActivityList;
    }

    @Override
    public void delete(long id) {
        managerBD.delete(id);
    }

}
