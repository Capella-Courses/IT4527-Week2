package com.capella.it4527.ticketapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class TicketController {

    @RequestMapping("/ticket/put")
    public String putTicket(
            @RequestParam int id,
            @RequestParam String title,
            @RequestParam String type,
            @RequestParam int priority,
            @RequestParam(required = false, defaultValue = "Open") String status,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "0") int error,
            @RequestParam(required = false) String task) {

        Ticket ticket;

        if (type.equalsIgnoreCase("bug")) {
            BugTicket bugTicket = new BugTicket();

            bugTicket.setId(id);
            bugTicket.setTitle(title);
            bugTicket.setPriority(priority);
            bugTicket.setStatus(status);
            bugTicket.setDescription(description);
            bugTicket.setError(error);

            ticket = bugTicket;

        } else if (type.equalsIgnoreCase("task")) {
            TaskTicket taskTicket = new TaskTicket();

            taskTicket.setId(id);
            taskTicket.setTitle(title);
            taskTicket.setPriority(priority);
            taskTicket.setStatus(status);
            taskTicket.setTask(task);

            ticket = taskTicket;

        } else {
            return "Invalid ticket type. Use bug or task.";
        }

        String fileName = id + ".txt";
        Path filePath = Path.of(fileName);

        try {
            Files.writeString(filePath, ticket.toString());
            return fileName;
        } catch (IOException exception) {
            return "The ticket could not be saved: " + exception.getMessage();
        }
    }

    @RequestMapping("/ticket/get")
    public Ticket getTicket(@RequestParam int id) {

        Path filePath = Path.of(id + ".txt");

        try {
            String fileContents = Files.readString(filePath);
            String[] values = fileContents.split("\\|");

            String type = values[2];

            if (type.equalsIgnoreCase("bug")) {
                BugTicket bugTicket = new BugTicket();

                bugTicket.setId(Integer.parseInt(values[0]));
                bugTicket.setTitle(values[1]);
                bugTicket.setPriority(Integer.parseInt(values[3]));
                bugTicket.setStatus(values[4]);
                bugTicket.setDescription(values[5]);
                bugTicket.setError(Integer.parseInt(values[6]));

                return bugTicket;

            } else if (type.equalsIgnoreCase("task")) {
                TaskTicket taskTicket = new TaskTicket();

                taskTicket.setId(Integer.parseInt(values[0]));
                taskTicket.setTitle(values[1]);
                taskTicket.setPriority(Integer.parseInt(values[3]));
                taskTicket.setStatus(values[4]);
                taskTicket.setTask(values[5]);

                return taskTicket;
            }

        } catch (IOException exception) {
            System.out.println("The ticket could not be read: "
                    + exception.getMessage());
        } catch (NumberFormatException exception) {
            System.out.println("A number in the ticket file is invalid.");
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("The ticket file does not contain all required fields.");
        }

        return null;
    }
}