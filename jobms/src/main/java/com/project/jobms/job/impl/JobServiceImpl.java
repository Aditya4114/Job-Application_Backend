package com.project.jobms.job.impl;

import com.project.jobms.job.Job;
import com.project.jobms.job.JobRepository;
import com.project.jobms.job.JobService;
import com.project.jobms.job.clients.CompanyClient;
import com.project.jobms.job.clients.ReviewClient;
import com.project.jobms.job.dto.JobWithDTO;
import com.project.jobms.job.external.Company;
import com.project.jobms.job.external.Review;
import com.project.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class JobServiceImpl implements JobService{
    //private List<Job> jobs = new ArrayList<>();
    final
    JobRepository jobRepository;

    final
    RestTemplate restTemplate;

    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;


    public JobServiceImpl(JobRepository jobRepository, RestTemplate restTemplate, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.restTemplate = restTemplate;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
//    @CircuitBreaker(name ="companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobWithDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public List<String> companyBreakerFallback(Exception e) {
        List<String> list = new ArrayList<>();
        list.add("Too many failed requests!");
        return list;
    }



    private JobWithDTO convertToDTO(Job job) {


//            RestTemplate restTemplate = new RestTemplate();
            Company company = companyClient.getCompany(job.getCompanyId());

            List<Review> reviews = reviewClient.getAllReviews(job.getCompanyId());

            JobWithDTO jobWithDTO = JobMapper.mapToJobWithCompanyDTO(job,  company, reviews);
//            jobWithDTO.setCompany(company);
            return jobWithDTO;
    }


    @Override
    public Job createJob(Job job) {
//        job.setId(null);
      return jobRepository.save(job);
    }

    @Override
    public JobWithDTO getById(Long id) {
        //return jobs.stream().filter(job -> Objects.equals(job.getId(), id)).findFirst().orElse(null);
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDTO(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
            if(jobOptional.isPresent()){
                Job job = jobOptional.get();
                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setLocation(updatedJob.getLocation());
                jobRepository.save(job);
                return true;

        }
    return false;
    }
}
