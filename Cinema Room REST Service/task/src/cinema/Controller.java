package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
class Controller {
    Room Theater1 = new Room(9, 9);
    CopyOnWriteArrayList<Seats> available_seats = Theater1.available_seats;

    @GetMapping("/seats")
    public Room getInformation() {
        return Theater1;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseTicket(@RequestBody TicketInfo tickets) {
        Integer row = tickets.getRow();
        Integer column = tickets.getColumn();
        if (row == null
                || column == null
                || row > 9
                || column > 9
                || row <= 0
                || column <= 0) {
            return new ResponseEntity<>(Map.of(
                    "error", "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }
        for (Seats s : available_seats) {
            if (s.getRow() == row && s.getColumn() == column) {
                available_seats.remove(s);
                return ResponseEntity.ok().body(s);
            }
        }

        return new ResponseEntity<>(Map.of(
                "error", "The ticket has been already purchased!"),
                HttpStatus.BAD_REQUEST);
    }
}
