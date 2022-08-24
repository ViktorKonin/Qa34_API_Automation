package dtoSuper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class RecordDto {
    private int breaks;
    private String currency;
    private DateDto date;
    private int hours;
    private int id;
    private String timeFrom;
    private String timeTo;
    private String title;
    private float totalSalary;
    private String type;
    private float wage;


}
