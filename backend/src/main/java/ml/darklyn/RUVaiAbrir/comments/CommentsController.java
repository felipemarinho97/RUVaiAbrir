package ml.darklyn.RUVaiAbrir.comments

import java.net.URI
import java.time.LocalDate
import java.util.Optional

import javax.validation.Valid

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.BodyBuilder
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import ml.darklyn.RUVaiAbrir.dto.CommentDTO
import ml.darklyn.RUVaiAbrir.enumeration.MealType
import ml.darklyn.RUVaiAbrir.time.TimeService
import ml.darklyn.RUVaiAbrir.user.UserPrincipal


@RestController
class CommentsController {

    @Autowired
    private val timeService: TimeService? = null

    @Autowired
    private val commentService: CommentService? = null

    /**
     * Obtém uma página contendo todos os comentários ordenados pelo mais recente.
     * @return
     */
    @GetMapping("/comentarios")
    fun getComments(
            @RequestParam(name = "dataInicial", required = false)
            @DateTimeFormat(iso = ISO.DATE)
            dateParam: Optional<LocalDate>,
            @RequestParam(name = "tipoDeRefeicao", required = false) mealTypeParam: Optional<MealType>,
            @RequestParam(name = "pagina", defaultValue = "0") page: Int?,
            @RequestParam(name = "limite", defaultValue = "20") limit: Int?,
            @RequestParam(name = "inc", required = false) includeOpt: Optional<List<String>>): ResponseEntity<Page<*>> {
        val sort = Sort.by(Direction.ASC, "date")
        val pageable = PageRequest.of(page!!, limit!!, sort)
        val date = dateParam.orElse(timeService!!.currentDate)
        val mealType = mealTypeParam.orElse(timeService.currentMealType)

        val comments = includeOpt
                .map { include -> commentService!!.getComments(date, mealType, pageable, include) }
                .orElse(commentService!!.getComments(date, mealType, pageable))

        return ResponseEntity.ok(comments)
    }

    /**
     * Cria um comentario.
     * @return
     */
    @PostMapping("/comentario")
    fun createComment(
            @RequestBody @Valid comment: CommentDTO,
            @AuthenticationPrincipal user: UserPrincipal): ResponseEntity<*> {
        val createdComment = commentService!!.createComment(comment, user.id)

        return ResponseEntity
                .created(URI.create("/comentario/" + createdComment.id!!))
                .body(createdComment)
    }

    /**
     * Obtém o comentário com o id especificado.
     * @return
     */
    @GetMapping("/comentario/{id}")
    fun getComment(
            @PathVariable("id") commentId: Long?,
            @RequestParam(name = "inc", required = false) includeOpt: Optional<List<String>>): ResponseEntity<*> {

        val responseComment = includeOpt
                .map { include -> commentService!!.getComment(commentId, include) }
                .orElse(commentService!!.getComment(commentId))

        return ResponseEntity.ok(responseComment)
    }

    /**
     * Atualiza o comentário com o id especificado.
     * @return
     */
    @PutMapping("/comentario/{id}")
    fun updateComment(
            @PathVariable id: Long?,
            @RequestBody commentDTO: CommentDTO,
            @AuthenticationPrincipal user: UserPrincipal): ResponseEntity<*> {
        val newComment = commentService!!.updateComment(id, commentDTO, user.id)

        return ResponseEntity.ok(newComment)
    }

    /**
     * Remove o comentário com o id especificado.
     * @return
     */
    @DeleteMapping("/comentario/{id}")
    fun removeComment(
            @PathVariable id: Long?,
            @AuthenticationPrincipal user: UserPrincipal): ResponseEntity<*> {

        commentService!!.deleteComment(id, user.id)

        val VemMonstro = ResponseEntity.ok()

        return VemMonstro.build<Any>()
    }


}
