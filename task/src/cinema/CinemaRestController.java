package cinema;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

@RestController
public class CinemaRestController {
    @Resource
    private CinemaManager cinemaManager;

    @GetMapping("/seats")
    public CinemaManager getSeats() {
        return cinemaManager;
    }

    @PostMapping("/purchase")
    public Map<String, ?> purchaseSeat(@RequestBody PurchaseRequest request) {
        return cinemaManager.handlePurchaseRequest(request);
    }

    @PostMapping("/return")
    public Map<String, ?> returnTicket(@RequestBody Map<String, String> token) {
        return cinemaManager.handleReturnedTickets(token.get("token"));
    }

    @PostMapping("/stats")
    public Statistics getStats(@RequestParam(defaultValue = "", required = false) String password) {
        if(!password.equals("super_secret")) {
            throw new WrongPasswordException("The password is wrong!");
        }
        return cinemaManager.checkStat();
    }

}
