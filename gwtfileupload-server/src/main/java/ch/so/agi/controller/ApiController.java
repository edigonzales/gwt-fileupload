package ch.so.agi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ch.so.agi.service.FilesystemStorageService;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ApiController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FilesystemStorageService fileStorageService;
    
    private int counter=0;
    
    @PostMapping(value="/rest/jobs", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadFile(@RequestParam(name="file", required=true) @RequestBody MultipartFile file) {
        
        Path uploadedFile = fileStorageService.store(file);        
        log.debug(uploadedFile.toAbsolutePath().toString());
        
        String inputFileName = uploadedFile.toAbsolutePath().toString();
        //String logFileName = Utils.getLogFileName(inputFileName);
        
//        JobId jobId = jobScheduler.enqueue(() -> ilivalidatorService.validate(inputFileName, logFileName, allObjectsAccessible, configFile));
//        log.debug(jobId.toString());

        UUID uuid = UUID.randomUUID();
        String jobId = uuid.toString();
        
        return ResponseEntity
                .accepted()
                .header("Operation-Location", getHost()+"/rest/jobs/"+jobId)
                .body(null);        
    }
    
    
    @PostMapping(value="/rest/mjobs", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadFiles(@RequestParam(name="files", required=true) @RequestBody MultipartFile[] files) {
        log.debug("number of files: {}", files.length);
        
        for (MultipartFile file : files) {
            log.debug(file.getOriginalFilename());
        }
//        Path uploadedFile = fileStorageService.store(file);        
//        log.debug(uploadedFile.toAbsolutePath().toString());
//        
//        String inputFileName = uploadedFile.toAbsolutePath().toString();
        //String logFileName = Utils.getLogFileName(inputFileName);
        
//        JobId jobId = jobScheduler.enqueue(() -> ilivalidatorService.validate(inputFileName, logFileName, allObjectsAccessible, configFile));
//        log.debug(jobId.toString());

        UUID uuid = UUID.randomUUID();
        String jobId = uuid.toString();
        
        return ResponseEntity
                .accepted()
                .header("Operation-Location", getHost()+"/rest/jobs/"+jobId)
                .body(null);        
    }
    
    
    @GetMapping("/rest/jobs/{jobId}")
    public ResponseEntity<?> getJobById(@PathVariable String jobId) {
        log.debug("jobId: {}", jobId);

        counter++;
        
        if (counter % 10 == 0) {
            return ResponseEntity.ok().body("SUCCEEDED");
        } else {
            return ResponseEntity.ok().header("Retry-After", "30").body("PROCESSING");
        }
    }
    
    private String getHost() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }
}
