package cinema;

public class ReturnedTicketBody {
    Seats returned_ticket;

    ReturnedTicketBody() {}

    ReturnedTicketBody(Seats s) {
        this.returned_ticket = s;
    }

    public Seats getReturned_ticket() {
        return returned_ticket;
    }

    public void setReturned_ticket(Seats returned_ticket) {
        this.returned_ticket = returned_ticket;
    }
}
