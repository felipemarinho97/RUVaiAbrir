package ml.darklyn.RUVaiAbrir.comments;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ml.darklyn.RUVaiAbrir.dto.CommentDTO;
import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.exceptions.NoContentException;
import ml.darklyn.RUVaiAbrir.exceptions.NotFoundException;
import ml.darklyn.RUVaiAbrir.user.User;
import ml.darklyn.RUVaiAbrir.user.UserRepository;
import ml.darklyn.RUVaiAbrir.util.AuthValidator;
import ml.darklyn.RUVaiAbrir.util.Util;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Util util;

	public Page<Comment> getComments(LocalDate date, MealType mealType, Pageable pageable) {
		Page<Comment> comments = commentRepository.findByDateAndMealType(date, mealType, pageable)
				.orElseThrow(() -> new NoContentException("Não existe nenhum comentário para hoje."));
		return comments;
	}
	
	public Page<Comment> getComments(LocalDate date, MealType mealType, Pageable pageable, List<String> include) {
		Page<Comment> comments = this.getComments(date, mealType, pageable);
		
		comments.getContent()
				.forEach((comment) -> util.applyIncludeParams(include, comment));
		
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
		
		util.applyIncludeParams(include, comment);
		
		return comment;
	}
	
	@Cacheable(value = "comment", key = "#commentId")
	public Comment getComment(Long commentId) {
		System.out.println("Not from cache");
		return commentRepository.findById(commentId)
				.orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum comentário com o ID especificado."));
	}
	
	@CacheEvict(value = "comment", key = "#id")
	public void deleteComment(Long id, Long userId) {
		Comment comment = getComment(id);
		
		AuthValidator.validate(comment, userId);
		
		commentRepository.deleteById(userId);
	}
	
	@CachePut(value = "comment", key = "#id")
	public Comment updateComment(Long id, CommentDTO commentDTO, Long userId) {
		Comment comment = getComment(id);
		
		AuthValidator.validate(comment, userId);
		
		comment.setMessage(commentDTO.getMessage());
		return commentRepository.save(comment);
	}

}
