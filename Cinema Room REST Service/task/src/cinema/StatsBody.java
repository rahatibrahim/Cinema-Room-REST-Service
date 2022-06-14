package cinema;

public class StatsBody {
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
