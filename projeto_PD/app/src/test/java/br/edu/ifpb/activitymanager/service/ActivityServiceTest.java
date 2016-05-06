package br.edu.ifpb.activitymanager.service;

import org.junit.Test;

import br.com.mytasks.service.ActivityService;


public class ActivityServiceTest {

    @Test(expected = Exception.class)
    public void testDeleteActivity() throws Exception {
        ActivityService activityService = new ActivityService();
        activityService.removeActivity(78689L);
    }

    @Test(expected = Exception.class)
    public void testListAllActivities() throws Exception {
        ActivityService activityService = new ActivityService();
        activityService.listAllActivities();
    }

    @Test(expected = NullPointerException.class)
    public void testFindActivityById() throws Exception {
        ActivityService activityService = new ActivityService();
        activityService.findActivityById(39042093L);
    }
}