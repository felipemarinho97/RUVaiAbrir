package ml.darklyn.RUVaiAbrir.status;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/status")
	public ResponseEntity<?> addOrUpdateStatus() {
		return null;
	}
	
	@GetMapping("/status")
	public ResponseEntity<?> getStatus() {
		return null;
	}
	
	
	
	@GetMapping("/usuario/status")
	public ResponseEntity<?> getUserStatus() {
		return null;
	}
	
	
	@PostMapping("/usuario/status")
	public ResponseEntity<?> createUserStatus() {
		return null;
	}
	
	@GetMapping("/usuario/status/{id}")
	public ResponseEntity<?> getUserStatusById() {
		return null;
	}
	
	@PutMapping("/usuario/status/{id}")
	public ResponseEntity<?> updateUserStatus() {
		return null;
	}
	
	@DeleteMapping("/usuario/status/{id}")
	public ResponseEntity<?> removeUserStatus() {
		return null;
	}

}
