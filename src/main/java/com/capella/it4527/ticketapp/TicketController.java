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

    @PostMapping("/ticket/put")
    public String putTicket(@RequestBody Ticket ticket) {

       String fileName = ticket.getId() + ".txt";
       Path filePath = Path.of(fileName);

        try {
            Files.writeString(filePath, ticket.toString());
            return fileName;
        } catch (IOException exception) {
            return "The ticket could not be saved: " + exception.getMessage();
        }
    }

    @GetMapping("/ticket/get")
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
    @DeleteMapping("/ticket/delete")
    public String deleteTicket(@RequestParam int id) {
        String fileName = id + ".txt";
        Path filePath = Path.of(fileName);
        try {
            boolean deleted = Files.deleteIfExists(filePath);

            if (deleted) {
                return fileName;
            }

        } catch (IOException exception) {
            System.out.println(
                    "The ticket could not be deleted: "
                            + exception.getMessage()
            );
        }

        return "";
        
    }
    
}