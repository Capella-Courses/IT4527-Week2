package com.capella.it4527.ticketapp;

public class TaskTicket extends Ticket {

private String task;

public TaskTicket(){
    setType("task");
}
public String getTask(){
    return task;
}
public void setTask(String task){
   this.task = task;
}
@Override
    public String toString() {
        return super.toString() + "|" + task;
    }
  
}