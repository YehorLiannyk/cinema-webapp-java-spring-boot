package yehor.epam.cinema_final_project_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yehor.epam.cinema_final_project_spring.entities.Ticket;
import yehor.epam.cinema_final_project_spring.services.TicketService;
import yehor.epam.cinema_final_project_spring.services.UserService;

import java.util.List;

@Controller(value = "/user")
public class UserController {
    private final UserService userService;
    private final TicketService ticketService;

    @Autowired
    public UserController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping(value = "/profile")
    public String userProfilePage(Model model) {
        //final List<Ticket> ticketList = ticketService.getAll();
        return "index";
    }
}
