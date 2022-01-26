package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Statistics {
    @JsonProperty("current_income")
    private int currentIncome;
    @JsonProperty("number_of_available_seats")
    private int numberOfAvailableSeats;
    @JsonProperty("number_of_purchased_tickets")
    private int numberOfPurchasedTickets;
}
