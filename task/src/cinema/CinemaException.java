package cinema;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CinemaException extends RuntimeException{
    private String error;
}
