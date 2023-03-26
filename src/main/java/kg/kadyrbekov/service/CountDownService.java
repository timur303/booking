package kg.kadyrbekov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountDownService {


    public void countdown(int minutes) throws InterruptedException {
        int seconds = 0;
        int hours = 0;
        int totalSeconds = hours * 3600 + minutes * 60 + seconds;
        while (totalSeconds > 0) {
            int remainingHours = totalSeconds / 3600;
            int remainingMinutes = (totalSeconds % 3600) / 60;
            int remainingSeconds = totalSeconds % 60;
            System.out.printf("%02d:%02d:%02d\n", remainingHours, remainingMinutes, remainingSeconds);
            Thread.sleep(1000);
            totalSeconds--;
        }

        System.out.println("Time's up!");
        int cost = minutes * 2;
        System.out.println("Your check " + cost + " $ ");
    }

    public void countdown(int hours, int minutes) throws InterruptedException {
        int seconds = 0;
        int totalSeconds = hours * 3600 + minutes * 60 + seconds;
        while (totalSeconds > 0) {
            int remainingHours = totalSeconds / 3600;
            int remainingMinutes = (totalSeconds % 3600) / 60;
            int remainingSeconds = totalSeconds % 60;
            System.out.printf("%02d:%02d:%02d\n", remainingHours, remainingMinutes, remainingSeconds);
            Thread.sleep(1000);
            totalSeconds--;
        }

        System.out.println("Time's up!");
        int cost = minutes * 2;
        System.out.println("Сиздин чек " + cost + " сом болду ");

    }

}
