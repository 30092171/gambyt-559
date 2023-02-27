package gambyt.proxy.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @CrossOrigin(origins = "*")
    @GetMapping("")
    public void checkLogin() {
//        Endpoint to login a user
    }
}
