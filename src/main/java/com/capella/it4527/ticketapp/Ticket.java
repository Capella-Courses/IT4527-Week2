package com.capella.it4527.ticketapp;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = TaskTicket.class,
                name = "task"
        ),
        @JsonSubTypes.Type(
                value = BugTicket.class,
                name = "bug"
        )
})

public abstract class Ticket {

    private int id;
    private String title;
    private String type;
    private int priority;
    private String status;

    public Ticket() {
        this.status = "Open";
    }
    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getType(){
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + "|" + title + "|" + type + "|" + priority + "|" + status;
    }

}