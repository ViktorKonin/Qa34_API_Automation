package dtoSuper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder

public class PeriodDto {
    private int monthFrom;
    private int monthTo;
    private int yearFrom;
    private int yearTo;
}
