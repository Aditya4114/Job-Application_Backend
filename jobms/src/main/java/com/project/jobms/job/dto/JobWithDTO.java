package com.project.jobms.job.dto;

import com.project.jobms.job.external.Company;
import com.project.jobms.job.external.Review;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class JobWithDTO {
    private long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private Company company;
    private List<Review> review;
}
