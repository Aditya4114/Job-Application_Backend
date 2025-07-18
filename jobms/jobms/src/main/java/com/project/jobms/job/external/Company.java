package com.project.jobms.job.external;
import jakarta.persistence.*;
import lombok.*;


@Data
@Getter
@Setter
public class Company {
    private Long id;
    private String name;
    private String description;

}
