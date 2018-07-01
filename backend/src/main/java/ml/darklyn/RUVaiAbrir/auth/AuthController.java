package ml.darklyn.RUVaiAbrir.auth;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ml.darklyn.RUVaiAbrir.config.jwt.JwtTokenProvider;
import ml.darklyn.RUVaiAbrir.dto.JwtAuthResponse;
import ml.darklyn.RUVaiAbrir.dto.LoginRequest;
import ml.darklyn.RUVaiAbrir.dto.RegisterRequest;
import ml.darklyn.RUVaiAbrir.enumeration.RoleName;
import ml.darklyn.RUVaiAbrir.exceptions.BadRequestException;
import ml.darklyn.RUVaiAbrir.user.User;
import ml.darklyn.RUVaiAbrir.user.UserRepository;
import ml.darklyn.RUVaiAbrir.user.roles.Role;
import ml.darklyn.RUVaiAbrir.user.roles.RoleRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	    Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginRequest.getUsernameOrEmail(),
				loginRequest.getPassword()
			)
	    );
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthResponse(jwt));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
	    if (userRepository.existsByUsername(registerRequest.getUsername())) {
	        throw new BadRequestException("Nome de usuário já está em uso!");
	    }
	
	    if (userRepository.existsByEmail(registerRequest.getEmail())) {
	        throw new BadRequestException("Endereço de email já está em uso!");
	    }
	
	    User user = new User(registerRequest.getFirstName(), registerRequest.getLastName(), 
	    		registerRequest.getEmail(), registerRequest.getUsername(), registerRequest.getPassword());
	
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	
	    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
	    		.orElseThrow(() -> new BadRequestException("Usuário ROLE não foi definida."));
	
	    user.setRoles(Collections.singleton(userRole));
	
	    User result = userRepository.save(user);
	
	    URI location = ServletUriComponentsBuilder
	            .fromCurrentContextPath().path("/api/users/{username}")
	            .buildAndExpand(result.getUsername()).toUri();
	
	    return ResponseEntity.created(location).build();
//	    		.body(new ApiResponse(true, "User registered successfully"));
	}
}
