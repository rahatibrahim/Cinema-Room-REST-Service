package cinema;

import java.util.UUID;

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

