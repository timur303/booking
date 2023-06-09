package kg.kadyrbekov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class BookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
        System.out.println("Welcome to colleges, project name is Booking !");
    }

    public String PORT = System.getenv("PORT");

    @GetMapping()
    public String getCreatingPage() {
        return "<h1> Welcome to Boking Application! <h1>";
    }

    @GetMapping
    public String welcome() {
        return "Welcome to Boking Application!";
    }
}
