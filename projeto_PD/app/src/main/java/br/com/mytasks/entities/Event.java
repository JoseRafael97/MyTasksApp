package br.com.mytasks.entities;

import java.io.Serializable;
import java.util.Date;


public class Event implements Serializable{

    private long id;
    private String name;
    private Date initialDate;
    private Date finalDate;

    public Event() {}

    public Event(String name, Date initialDate, Date finalDate) {
        this.name = name;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public Event(long id, String name, Date initialDate, Date finalDate) {
        this.id = id;
        this.name = name;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", initialDate=" + initialDate.toString() +
                ", finalDate=" + finalDate.toString() +
                '}';
    }
}
