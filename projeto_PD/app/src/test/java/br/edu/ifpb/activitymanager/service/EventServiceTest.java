package br.edu.ifpb.activitymanager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import java.lang.Exception;

import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.service.EventService;


@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @Test(expected = Exception.class)
    public void testAddEvent() throws Exception {
        EventService eventService = new EventService();
        eventService.addEvent("", null, null);

    }

    @Test(expected = ActivityManagerException.class)
    public void testUpdateEvent() throws Exception {
        EventService eventService = new EventService();
        eventService.updateEvent(666L, null, null, null);

    }

    @Test(expected = Exception.class)
    public void testFindEventById() throws Exception {
        EventService eventService = new EventService();
        eventService.findEventById(45822198L);
    }

    @Test(expected = Exception.class)
    public void testFindEventByDate() throws Exception {
        EventService eventService = new EventService();
        eventService.findEventByDate(null);
    }

    @Test(expected = Exception.class)
    public void testFindAll() throws Exception {
        EventService eventService = new EventService();
        eventService.findAll();

    }
}