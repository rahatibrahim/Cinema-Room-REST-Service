package cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

class Seats {
    int row;
    int column;
    int price;

    Seats() {
    }

    public Seats(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

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

class TicketInfo {
    Integer row;
    Integer column;

    TicketInfo() {
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


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
