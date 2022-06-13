package cinema;

import java.util.concurrent.CopyOnWriteArrayList;

class Room {
    int total_rows;
    int total_columns;
    CopyOnWriteArrayList<Seats> available_seats = new CopyOnWriteArrayList<>();

    Room() {
    }

    public Room(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;

        for (int row = 1; row <= total_rows; row++) {
            for (int col = 1; col <= total_columns; col++) {
                int price = 8;
                if (row <= 4) {
                    price = 10;
                }
                available_seats.add(new Seats(row, col, price));
            }
        }
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public CopyOnWriteArrayList<Seats> getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(CopyOnWriteArrayList<Seats> available_seats) {
        this.available_seats = available_seats;
    }
}
