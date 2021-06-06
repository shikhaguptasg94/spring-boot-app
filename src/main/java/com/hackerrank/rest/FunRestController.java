package com.hackerrank.rest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hackerrank.payload.UploadFileResponse;
import com.hackerrank.service.FileStorageService;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
public class FunRestController {
	private static final Logger logger = LoggerFactory.getLogger(FunRestController.class);
	
	@Autowired
    private FileStorageService fileStorageService;
    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        fileStorageService.save(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        System.out.println("data::"+fileName);
        System.out.println("data::"+fileDownloadUri);
      //  fileStorageService.save(file);
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
	
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
	
	List<Notes> responseList=new ArrayList<Notes>();
	@GetMapping("/readNote")
	public List<Notes> home() {
        Notes sampleNote = new Notes(101, "Sample Note", "Sample message");
        responseList.add(sampleNote);
        return  responseList;
    } 
	
	@PostMapping("/writeNote")
    String addUser(@RequestBody Notes note) {
		System.out.println("Inside set..."+ note);
		 for(int i=0; i<responseList.size();i++) {
			 if(note.getNoteName().equalsIgnoreCase(responseList.get(i).getNoteName())) {
				 return "Fail";
			 }
		 }
		 responseList.add(note);
		 return "Success";
    }
		
	/*@Value("${project.name}")
	private String project;
	
	@Value("${team.name}")
	private String team;
	
	@GetMapping("/teamDetails")
	public String checkTeam() {
		return "Project name:"+project+", Team:"+team;
	}
	
	@GetMapping("/")
	public String sayHello() {
		return "Hello World! Time on server is " + LocalDateTime.now();
	}
	
	@GetMapping("/workout")
		public String getWorkoutPlan() {
			return "Run daily 1 hrs in morning!";
		}
	
	@GetMapping("/fortune")
	public String getDailyFortune() {
		return "Today is your lucky day!";
	}
	*/
}












