package com.project.jobms.job.mapper;

import com.project.jobms.job.Job;
import com.project.jobms.job.dto.JobWithDTO;
import com.project.jobms.job.external.Company;
import com.project.jobms.job.external.Review;

import java.util.List;

public class JobMapper {

    public static JobWithDTO mapToJobWithCompanyDTO(Job job, Company company, List<Review> reviews){
        JobWithDTO jobWithDTO = new JobWithDTO();
        jobWithDTO.setId(job.getId());
        jobWithDTO.setTitle(job.getTitle());
        jobWithDTO.setDescription(job.getDescription());
        jobWithDTO.setLocation(job.getLocation());
        jobWithDTO.setMaxSalary(job.getMaxSalary());
        jobWithDTO.setMinSalary(job.getMinSalary());
        jobWithDTO.setCompany(company);
        jobWithDTO.setReview(reviews);
        return jobWithDTO;
    }
}
