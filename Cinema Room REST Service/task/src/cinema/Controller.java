package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;


@RestController
class Controller {
    Room theater = new Room(9, 9);
    StatsBody stats = new StatsBody(theater);
    CopyOnWriteArrayList<Seats> available_seats = theater.available_seats;
    CopyOnWriteArrayList<TicketWithTokenBody> handed_ticket_with_tokens = new CopyOnWriteArrayList<>();

    @GetMapping("/seats")
    public Room getInformation() {
        return theater;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseTicket(@RequestBody RequestedTicketBody tickets) {
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
                stats.updateStatsBody(s, "sold");
                TicketWithTokenBody t = new TicketWithTokenBody(s);
                handed_ticket_with_tokens.add(t);
                return ResponseEntity.ok().body(t);
            }
        }

        return new ResponseEntity<>(Map.of(
                "error", "The ticket has been already purchased!"),
                HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/return")
    public ResponseEntity<Object> returnTicket(@RequestBody TokenBody refundedToken) {
        try {
            UUID refundedTokenUUID = UUID.fromString(refundedToken.token);
            for (TicketWithTokenBody t : handed_ticket_with_tokens) {
                if (t.token.compareTo(refundedTokenUUID) == 0) {
                    handed_ticket_with_tokens.remove(t);
                    available_seats.add(t.ticket);
                    stats.updateStatsBody(t.ticket, "returned");
                    ReturnedTicketBody body = new ReturnedTicketBody(t.ticket);
                    return ResponseEntity.ok().body(body);
                }
            }
        } catch (IllegalArgumentException ignored) {

        }

        return new ResponseEntity<>(Map.of(
                "error", "Wrong token!"),
                HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    public ResponseEntity<Object> seeStats(@RequestParam Optional<String> password) {
        if (password.isPresent()) {
            return ResponseEntity.ok().body(stats);
        }

        return new ResponseEntity<>(Map.of(
                "error", "The password is wrong!"),
                HttpStatus.UNAUTHORIZED);
    }
}
