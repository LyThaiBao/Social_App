package social_app.example.social_app.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class ConvertDateTime {
    public String convertInstant(Instant instant){
        LocalDateTime ldt = LocalDateTime.ofInstant(instant,ZoneId.of("Asia/Ho_Chi_Minh"));
       return ldt.getHour()+":"+ldt.getMinute()+" # "+ldt.getDayOfMonth()+"/"+ldt.getMonth().getValue()+"/"+ldt.getYear();
    }
}
