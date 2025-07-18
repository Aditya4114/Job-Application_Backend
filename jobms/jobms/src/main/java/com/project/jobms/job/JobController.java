package com.project.jobms.job;


import com.project.jobms.job.dto.JobWithDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobWithDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job){
        Job savedJob = jobService.createJob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobWithDTO> getJobById(@PathVariable Long id){
        JobWithDTO jobWithDTO = jobService.getById(id);
        if (jobWithDTO != null) {
            return new ResponseEntity<>(jobWithDTO, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        boolean deleted = jobService.deleteJobById(id);
        return deleted
                ? ResponseEntity.ok("Job deleted successfully")
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job updatedJob){
        boolean updated = jobService.updateJob(id, updatedJob);
        return updated
                ? ResponseEntity.ok("Job updated successfully")
                : ResponseEntity.notFound().build();
    }
}
