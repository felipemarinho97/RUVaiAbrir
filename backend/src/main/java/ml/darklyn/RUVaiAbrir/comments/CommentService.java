package ml.darklyn.RUVaiAbrir.comments;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ml.darklyn.RUVaiAbrir.dto.CommentDTO;
import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.exceptions.BadRequestException;
import ml.darklyn.RUVaiAbrir.exceptions.NoContentException;
import ml.darklyn.RUVaiAbrir.exceptions.NotFoundException;
import ml.darklyn.RUVaiAbrir.exceptions.UnauthorizedException;
import ml.darklyn.RUVaiAbrir.rating.Rating;
import ml.darklyn.RUVaiAbrir.rating.RatingService;
import ml.darklyn.RUVaiAbrir.time.TimeService;
import ml.darklyn.RUVaiAbrir.user.User;
import ml.darklyn.RUVaiAbrir.user.UserRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentReporitory commentRepository;
	
	@Autowired
	private TimeService timeService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RatingService ratingService;

	public Page<Comment> getComments(LocalDate date, MealType mealType, Pageable pageable) {
		Page<Comment> comments = commentRepository.findByDateAndMealType(date, mealType, pageable)
				.orElseThrow(() -> new NoContentException("Não existe nenhum comentário para hoje."));
		return comments;
	}
	
	public Page<Comment> getComments(LocalDate date, MealType mealType, Pageable pageable, List<String> include) {
		Page<Comment> comments = this.getComments(date, mealType, pageable);
		
		comments.getContent().forEach((comment) -> applyIncludeParams(include, comment));
		
		return comments;
	}

	public Comment createComment(@Valid CommentDTO commentDTO, Long userId) {
		User user = userRepository.findById(userId).get();
		Comment comment = new Comment(user, commentDTO.getMessage(), commentDTO.getDate(), 
				commentDTO.getMealType());

		return commentRepository.save(comment);
	}

	public Comment getComment(Long commentId, List<String> include) {
		final Comment comment = this.getComment(commentId);
		
		applyIncludeParams(include, comment);
		
		return comment;
	}
	
	private void applyIncludeParams(List<String> include, final Comment comment) {
		include.forEach((toInclude) -> {
			switch (toInclude) {
			case "classificacao":
				applyRatingParam(comment);
				break;
			default:
				throw new BadRequestException("O atributo '"+toInclude+"' passado para o parâmetro 'inc' é inválido.");
			}
		});
	}

	private void applyRatingParam(final Comment comment) {
		try {
			Rating rating = ratingService.getRating(comment.getUser(), comment.getDate(), comment.getMealType());
			comment.setRating(rating.getRating());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public Comment getComment(Long commentId) {
		return commentRepository.findById(commentId)
				.orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum comentário com o ID especificado."));
	}

	public void deleteComment(Long id, Long userId) {
		Comment comment = getComment(id);
		
		validateCommentAuth(comment, userId);
		
		commentRepository.deleteById(userId);
	}

	public Comment updateComment(Long id, CommentDTO commentDTO, Long userId) {
		Comment comment = getComment(id);
		validateCommentAuth(comment, userId);
		comment.setMessage(commentDTO.getMessage());
		return commentRepository.save(comment);
	}
	
	private void validateCommentAuth(Comment comment, Long userId) {
		if (!comment.getUser().getId().equals(userId)) {
			throw new UnauthorizedException("Você não tem autorização para modificar este recurso.");
		}
	}

}
