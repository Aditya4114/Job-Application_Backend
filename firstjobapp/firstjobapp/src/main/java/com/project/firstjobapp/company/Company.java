package com.project.firstjobapp.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.firstjobapp.job.Job;
import com.project.firstjobapp.reviews.Review;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;


    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<Job> jobs;

    @OneToMany(mappedBy = "company")
    private List<Review> reviews;
}
