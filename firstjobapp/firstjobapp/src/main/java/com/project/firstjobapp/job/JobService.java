package com.project.firstjobapp.job;

import java.util.List;

public interface JobService {
    List<Job> findAll();
    Job createJob(Job job);

    Job getById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJob(Long id, Job updatedJob);
}
