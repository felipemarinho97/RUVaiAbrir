package ml.darklyn.RUVaiAbrir.comments;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ml.darklyn.RUVaiAbrir.dto.CommentDTO;
import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.time.TimeService;
import ml.darklyn.RUVaiAbrir.user.UserPrincipal;


@RestController
public class CommentsController {
	
	@Autowired
	private TimeService timeService;
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * Obtém uma página contendo todos os comentários ordenados pelo mais recente.
	 * @return
	 */
	@GetMapping("/comentarios")
	public ResponseEntity<Page<?>> getComments(
			@RequestParam(name = "dataInicial", required = false)
			@DateTimeFormat(iso = ISO.DATE) Optional<LocalDate> dateParam,
			@RequestParam(name = "tipoDeRefeicao", required = false) Optional<MealType> mealTypeParam,
			@RequestParam(name = "pagina", defaultValue = "0") Integer page,
			@RequestParam(name = "limite", defaultValue = "20") Integer limit,
			@RequestParam(name = "inc", required = false) Optional<List<String>> includeOpt) {
		Sort sort = Sort.by(Direction.ASC, "date");
		Pageable pageable = PageRequest.of(page, limit, sort);
		LocalDate date = dateParam.orElse(timeService.getCurrentDate());
		MealType mealType = mealTypeParam.orElse(timeService.getCurrentMealType());
		
		Page<Comment> comments = includeOpt
				.map((include) -> commentService.getComments(date, mealType, pageable, include))
				.orElse(commentService.getComments(date, mealType, pageable));
		
		return ResponseEntity.ok(comments);
	}
	
	/**
	 * Cria um comentario.
	 * @return
	 */
	@PostMapping("/comentario")
	public ResponseEntity<?> createComment(
			@RequestBody @Valid CommentDTO comment,
			@AuthenticationPrincipal UserPrincipal user) {
		Comment createdComment = commentService.createComment(comment, user.getId());

		return ResponseEntity
				.created(URI.create("/comentario/" + createdComment.getId()))
				.body(createdComment);
	}
	
	/**
	 *  Obtém o comentário com o id especificado.
	 * @return
	 */
	@GetMapping("/comentario/{id}")
	public ResponseEntity<?> getComment(
			@PathVariable("id") Long commentId,
			@RequestParam(name = "inc", required = false) Optional<List<String>> includeOpt) {

		Comment responseComment = includeOpt
						.map((include) -> commentService.getComment(commentId, include))
						.orElse(commentService.getComment(commentId));

		return ResponseEntity.ok(responseComment);
	}
	
	/**
	 * Atualiza o comentário com o id especificado.
	 * @return
	 */
	@PutMapping("/comentario/{id}")
	public ResponseEntity<?> updateComment(
			@PathVariable Long id,
			@RequestBody CommentDTO commentDTO,
			@AuthenticationPrincipal UserPrincipal user) {
		Comment newComment = commentService.updateComment(id, commentDTO, user.getId());
		
		return ResponseEntity.ok(newComment);
	}
	
	/**
	 * Remove o comentário com o id especificado.
	 * @return
	 */
	@DeleteMapping("/comentario/{id}")
	public ResponseEntity<?> removeComment(
			@PathVariable Long id,
			@AuthenticationPrincipal UserPrincipal user) {
	
		commentService.deleteComment(id, user.getId());
		
		BodyBuilder VemMonstro = ResponseEntity.ok();
		
		return VemMonstro.build();
	}


}
