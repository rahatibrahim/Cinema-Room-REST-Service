package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

class TicketWithToken {
    UUID token;
    Seats ticket;

    TicketWithToken() {
    }

    TicketWithToken(Seats s) {
        this.ticket = s;
        this.token = UUID.randomUUID();
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Seats getTicket() {
        return ticket;
    }

    public void setTicket(Seats ticket) {
        this.ticket = ticket;
    }
}

class Returned_ticket {

}

@RestController
class Controller {
    Room Theater1 = new Room(9, 9);
    CopyOnWriteArrayList<Seats> available_seats = Theater1.available_seats;
    CopyOnWriteArrayList<TicketWithToken> handed_ticket_with_tokens = new CopyOnWriteArrayList<>();

    @GetMapping("/seats")
    public Room getInformation() {
        return Theater1;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseTicket(@RequestBody RequestedTicket tickets) {
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
                TicketWithToken t = new TicketWithToken(s);
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
            for (TicketWithToken t : handed_ticket_with_tokens) {
                if (t.token.compareTo(refundedTokenUUID) == 0) {
                    handed_ticket_with_tokens.remove(t);
                    available_seats.add(t.ticket);
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
}
