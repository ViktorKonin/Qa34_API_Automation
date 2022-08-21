package dto;

import groovy.beans.Bindable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@Builder
public class ContactDto {
    private String address;
    private String description;
    private String email;
    private String lastName;
    private String name;
    private String phone;
    private int id;

}
