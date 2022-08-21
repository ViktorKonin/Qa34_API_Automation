package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@Builder
public class ErrorDto {

    private int code;
    private String details;
    private String message;
    private String timestamp;
}
