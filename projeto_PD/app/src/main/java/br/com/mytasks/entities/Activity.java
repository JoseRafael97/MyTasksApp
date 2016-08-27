package br.com.mytasks.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Activity implements Serializable {

    private long id;
    private String name;
    private Category category;
    private Date deadLine;
    private Date plannedHours;
    private Date workedHours;
    private int repetitions;
    private List<Event> events;
    private boolean finished;

    public boolean selected;

    public Activity(){

    }

    public Activity(String name, Date deadLine, Date plannedHours, Category category, int repetitions) {
        this.name = name;
        this.deadLine = deadLine;
        this.plannedHours = plannedHours;
        this.category = category;
        this.repetitions = repetitions;
    }

    public Activity(long id, String name, Date deadLine, Date plannedHours, Category category, int repetitions) {
        this.id = id;
        this.name = name;
        this.deadLine = deadLine;
        this.plannedHours = plannedHours;
        this.category = category;
        this.repetitions = repetitions;
    }

    public Activity(String name, Date deadLine, Date plannedHours, Date workedHours,
                    Category category, int repetitions, boolean finished, List<Event> events) {
        this.name = name;
        this.deadLine = deadLine;
        this.plannedHours = plannedHours;
        this.workedHours = workedHours;
        this.category = category;
        this.repetitions = repetitions;
        this.finished = finished;
        this.events = events;
    }

    public Activity(String name, Date deadLine, Date plannedHours,
                    Category category, int repetitions, boolean finished, List<Event> events) {
        this.name = name;
        this.deadLine = deadLine;
        this.plannedHours = plannedHours;
        this.category = category;
        this.repetitions = repetitions;
        this.finished = finished;
        this.events = events;
    }

    public Activity(long id, String name, Date deadLine, Date plannedHours,
                    Date workedHours, Category category, int repetitions, boolean finished, List<Event> events) {
        this.id = id;
        this.name = name;
        this.deadLine = deadLine;
        this.plannedHours = plannedHours;
        this.workedHours = workedHours;
        this.category = category;
        this.repetitions = repetitions;
        this.finished = finished;
        this.events = events;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getPlannedHours() {
        return plannedHours;
    }

    public void setPlannedHours(Date plannedHours) {
        this.plannedHours = plannedHours;
    }

    public Date getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(Date workedHours) {
        this.workedHours = workedHours;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", deadLine=" + deadLine +
                ", plannedHours=" + plannedHours +
                ", workedHours=" + workedHours +
                ", repetitions=" + repetitions +
                ", events=" + events +
                ", finished=" + finished +
                ", selected=" + selected +
                '}';
    }
}