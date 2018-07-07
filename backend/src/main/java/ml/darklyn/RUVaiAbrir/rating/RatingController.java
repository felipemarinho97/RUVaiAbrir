package ml.darklyn.RUVaiAbrir.rating;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ml.darklyn.RUVaiAbrir.dto.RatingDTO;
import ml.darklyn.RUVaiAbrir.user.UserPrincipal;


@RequestMapping("/classificacao")
@RestController
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	
	/**
	 * Retorna a classificação geral do restaurante.
	 * @return
	 */
	@GetMapping
	public ResponseEntity getGeneralRating() {
		return ResponseEntity.ok(ratingService.getGeneralRating());
	}
	
	/**
	 * Cria um recurso de classificação do restaurante.
	 * @return
	 */
	@PostMapping
	public ResponseEntity createRating(
			@RequestBody @Valid RatingDTO rating, 
			@AuthenticationPrincipal UserPrincipal user) {
		Rating responseRating = ratingService.createRating(rating, user.getUsername(), user.getEmail());
		return ResponseEntity
				.created(URI.create("/classificacao/" + responseRating.getId()))
				.body(responseRating);
	}
	
	/**
	 * Obtém a classificação com o id especificado.
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity getRating(@PathVariable Long id) {
		Rating rating = ratingService.getRatingById(id);
		
		return ResponseEntity.ok(rating);
	}
	
	/**
	 * Atualiza a classificação com o id especificado.
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity updateRating(
			@PathVariable Long id, 
			@RequestBody @Valid RatingDTO updatedRating,
			@AuthenticationPrincipal UserPrincipal user) {
		Rating rating = ratingService.updateRating(id, updatedRating, user.getId());
		
		return ResponseEntity.ok(rating);
	}
	
	/**
	 * Remove a classificação com o id especificado.
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity deleteRating(
			@PathVariable Long id, 
			@AuthenticationPrincipal UserPrincipal user) {
		ratingService.deleteRating(id, user.getId());
		return ResponseEntity.ok().build();
	}

}
