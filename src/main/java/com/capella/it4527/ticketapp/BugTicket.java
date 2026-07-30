package com.capella.it4527.ticketapp;

public class BugTicket extends Ticket {

    private String description;
    private int error;

    public BugTicket() {
        setType("bug");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return super.toString() + "|" + description + "|" + error;
    }
}