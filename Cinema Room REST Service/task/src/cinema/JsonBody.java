package cinema;

import java.util.UUID;

interface JsonBody {
}

class RequestedTicketBody implements JsonBody {
    Integer row;
    Integer column;

    RequestedTicketBody() {
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}

class ReturnedTicketBody implements JsonBody {
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


class TokenBody implements JsonBody {
    String token;

    TokenBody() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

class TicketWithTokenBody {
    UUID token;
    Seats ticket;

    TicketWithTokenBody() {
    }

    TicketWithTokenBody(Seats s) {
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

class StatsBody implements JsonBody {
    int current_income;
    int number_of_available_seats;
    int number_of_purchased_tickets;

    StatsBody() {}

    StatsBody(Room theater) {
        this.current_income = 0;
        this.number_of_available_seats = theater.available_seats.size();
        this.number_of_purchased_tickets = 0;
    }

    public void updateStatsBody(Seats s, String action) {
        if (action.equals("sold")) {
            this.current_income += s.price;
            this.number_of_available_seats--;
            this.number_of_purchased_tickets++;
        } else if (action.equals("returned")) {
            this.current_income -= s.price;
            this.number_of_available_seats++;
            this.number_of_purchased_tickets--;
        }
    }

    public int getCurrent_income() {
        return current_income;
    }

    public void setCurrent_income(int current_income) {
        this.current_income = current_income;
    }

    public int getNumber_of_available_seats() {
        return number_of_available_seats;
    }

    public void setNumber_of_available_seats(int number_of_available_seats) {
        this.number_of_available_seats = number_of_available_seats;
    }

    public int getNumber_of_purchased_tickets() {
        return number_of_purchased_tickets;
    }

    public void setNumber_of_purchased_tickets(int number_of_purchased_tickets) {
        this.number_of_purchased_tickets = number_of_purchased_tickets;
    }
}

