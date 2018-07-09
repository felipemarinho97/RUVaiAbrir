package ml.darklyn.RUVaiAbrir.comments;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ml.darklyn.RUVaiAbrir.dto.CommentDTO;
import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.exceptions.NoContentException;
import ml.darklyn.RUVaiAbrir.exceptions.NotFoundException;
import ml.darklyn.RUVaiAbrir.user.User;
import ml.darklyn.RUVaiAbrir.user.UserRepository;
import ml.darklyn.RUVaiAbrir.util.AuthValidator;
import ml.darklyn.RUVaiAbrir.util.EntityIncluder;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EntityIncluder util;
	
	@Resource
	private CommentService self;
	
	@Cacheable("comments")
	public Page<Comment> getComments(LocalDate date, MealType mealType, Pageable pageable) {
		Page<Comment> comments = commentRepository.findByDateAndMealType(date, mealType, pageable)
				.orElseThrow(() -> new NoContentException("Não existe nenhum comentário para hoje."));
		return comments;
	}
	
	public Page<Comment> getComments(LocalDate date, MealType mealType, Pageable pageable, List<String> include) {
		Page<Comment> comments = self.getComments(date, mealType, pageable);
		
		comments.getContent()
				.forEach((comment) -> util.applyIncludeParams(include, comment));
		
		return comments;
	}
	
	@Transactional
	@CacheEvict(value = "comments", allEntries = true)
	public Comment createComment(@Valid CommentDTO commentDTO, Long userId) {
		User user = userRepository.findById(userId).get();
		Comment comment = new Comment(user, commentDTO.getMessage(), commentDTO.getDate(), 
				commentDTO.getMealType());

		return commentRepository.save(comment);
	}
	
	public Comment getComment(Long commentId, List<String> include) {
		final Comment comment = self.getComment(commentId);
		
		util.applyIncludeParams(include, comment);
		
		return comment;
	}
	
	@Cacheable(value = "comment", key = "#commentId")
	public Comment getComment(Long commentId) {
		return commentRepository.findById(commentId)
				.orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum comentário com o ID especificado."));
	}
	
	@Transactional
	@Caching(evict = {
		@CacheEvict(value = "comment", key = "#id"),
		@CacheEvict(value = "comments", allEntries = true)
	})
	public void deleteComment(Long id, Long userId) {
		Comment comment = self.getComment(id);
		
		AuthValidator.validate(comment, userId);
		
		commentRepository.deleteById(id);
	}
	
	@Transactional
	@Caching(evict = {
		@CacheEvict(value = "comments", allEntries = true)	
	}, put = {
		@CachePut(value = "comment", key = "#id")			
	})
	public Comment updateComment(Long id, CommentDTO commentDTO, Long userId) {
		Comment comment = self.getComment(id);
		
		AuthValidator.validate(comment, userId);
		
		comment.setMessage(commentDTO.getMessage());
		return commentRepository.save(comment);
	}

}
