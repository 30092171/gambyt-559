package gambyt.proxy.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inbox")
public class InboxController {
    @GetMapping("/{id}")
    public void getInbox(@PathVariable("id") long id) {
//        Endpoint to get a user's inbox
    }


}
