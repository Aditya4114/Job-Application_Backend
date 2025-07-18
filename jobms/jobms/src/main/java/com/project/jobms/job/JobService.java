package com.project.jobms.job;

import com.project.jobms.job.dto.JobWithDTO;

import java.util.List;

public interface JobService {
    List<JobWithDTO> findAll();
    Job createJob(Job job);

    JobWithDTO getById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJob(Long id, Job updatedJob);
}
