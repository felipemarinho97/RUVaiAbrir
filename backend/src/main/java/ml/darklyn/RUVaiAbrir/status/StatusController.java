package ml.darklyn.RUVaiAbrir.status;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import ml.darklyn.RUVaiAbrir.dto.StatusDTO;
import ml.darklyn.RUVaiAbrir.user.UserPrincipal;

@RestController
public class StatusController {
	
	@Autowired
	private StatusService statusService;
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/status")
	public ResponseEntity<?> addOrUpdateStatus(@Valid @RequestBody StatusDTO statusDTO) {
		Status status = statusService.addOrUpdateStatus(statusDTO);
		
		return ResponseEntity.ok(status);
	}
	
	@GetMapping("/status")
	public ResponseEntity<?> getStatus() {
		Status status = statusService.getCurrentStatus();
		
		return ResponseEntity.ok(status);
	}
	
	
	
	@GetMapping("/usuario/status")
	public ResponseEntity<?> getUserStatus() {
		Status status = statusService.getCurrentUserStatus();
		
		return ResponseEntity.ok(status);
	}
	
	
	@PostMapping("/usuario/status")
	public ResponseEntity<?> createUserStatus(@Valid @RequestBody StatusDTO statusDTO, 
			@AuthenticationPrincipal UserPrincipal userPrincipal) {
		UserStatus status = statusService.createUserStatus(statusDTO, userPrincipal.getEmail());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(status);
	}
	
	@GetMapping("/usuario/status/{id}")
	public ResponseEntity<?> getUserStatusById(@PathVariable("id") Long id) {
		UserStatus status = statusService.getUserStatusById(id);
		
		return ResponseEntity.ok(status);
	}
	
	@PutMapping("/usuario/status/{id}")
	public ResponseEntity<?> updateUserStatus(
			@PathVariable("id") Long id, 
			@Valid @RequestBody StatusDTO statusDTO) {
		UserStatus status = statusService.updateUserStatus(id, statusDTO);
		
		return ResponseEntity.ok(status);
	}
	
	@DeleteMapping("/usuario/status/{id}")
	public ResponseEntity<?> removeUserStatus(
			@PathVariable("id") Long id,
			@AuthenticationPrincipal UserPrincipal userPrincipal) {
		statusService.removeUserStatus(id, userPrincipal.getEmail());
		
		return ResponseEntity.noContent().build();
	}

}
