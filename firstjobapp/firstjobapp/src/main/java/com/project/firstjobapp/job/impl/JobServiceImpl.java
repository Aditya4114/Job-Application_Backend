package com.project.firstjobapp.job.impl;

import com.project.firstjobapp.job.Job;
import com.project.firstjobapp.job.JobRepository;
import com.project.firstjobapp.job.JobService;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class JobServiceImpl implements JobService {

    //private List<Job> jobs = new ArrayList<>();
    final
    JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job createJob(Job job) {
//        job.setId(null);
      return jobRepository.save(job);
    }

    @Override
    public Job getById(Long id) {
        //return jobs.stream().filter(job -> Objects.equals(job.getId(), id)).findFirst().orElse(null);
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
//        Iterator<Job> iterator = jobs.iterator();
//        while(iterator.hasNext()){
//            Job job = iterator.next();
//            if(Objects.equals(job.getId(), id)){
//                iterator.remove();
//                return true;
//            }
//        }
//        return false;
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
