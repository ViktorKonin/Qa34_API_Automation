package dtoSuper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class DateDto {
    private int dayOfMonth;
    private String dayOfWeek;
    private int month;
    private int year;
}
