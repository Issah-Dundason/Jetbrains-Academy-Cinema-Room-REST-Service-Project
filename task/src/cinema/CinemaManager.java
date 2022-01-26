package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Service
public class CinemaManager {
    @JsonProperty("total_rows")
    private int totalRows;

    @JsonProperty("total_columns")
    private int totalColumns;

    @JsonProperty("available_seats")
    private List<Seat> availableSeats;

    @JsonIgnore
    private int totalIncome;

    @JsonIgnore
    private Map<String, Map<String, ?>> purchases = new HashMap<>();

    public CinemaManager() {
        totalColumns = 9;
        totalRows = 9;
        availableSeats = IntStream.rangeClosed(1, 9)
                .mapToObj(i -> IntStream.rangeClosed(1, 9)
                        .mapToObj(j -> {
                            int price = i <= 4 ? 10 : 8;
                            return new Seat(i, j, price);
                        })
                        .collect(Collectors.toList())).flatMap(Collection::stream)
               .collect(Collectors.toList());
    }

    public Map<String, ?> handlePurchaseRequest(PurchaseRequest request) {
        if(request.getRow() > 9 || request.getRow() < 1 || request.getColumn() > 9
                || request.getColumn() < 1 ) {
            throw new CinemaException("The number of a row or a column is out of bounds!");
        }
       Seat purchasedSeat = availableSeats.stream()
                .filter(seat -> seat.getRow() == request.getRow() && seat.getColumn() == request.getColumn())
                .findFirst().orElseThrow(() -> new CinemaException("The ticket has been already purchased!"));
        availableSeats.remove(purchasedSeat);
        totalIncome += purchasedSeat.getPrice();
        String token = UUID.randomUUID().toString();
        Map<String, ?> map = Map.of("token", token,
                "ticket", purchasedSeat);
        purchases.put(token, map);
        return map;
    }

    public Map<String, ?> handleReturnedTickets(String token) {
        Map<String, ?> data = Optional.ofNullable(purchases.get(token))
                .orElseThrow(() -> new CinemaException("Wrong token!"));
        Seat seat = (Seat) data.get("ticket");
        availableSeats.add(seat);
        totalIncome -= seat.getPrice();
        purchases.remove(token);
        return Map.of("returned_ticket", seat);
    }

    public Statistics checkStat() {
        return new Statistics(totalIncome, availableSeats.size(), purchases.size());
    }
}
