package com.sideproject.userInfo.userInfo.data.entity

import com.sideproject.userInfo.userInfo.data.dto.services.CommentRequest
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "movie_comment")
class MovieCommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    var user: UserEntity,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "target_date", nullable = false)
    var targetDate: LocalDate,
) : BasicEntity() {
    companion object {
        fun fromDto(commentRequest: CommentRequest, userEntity: UserEntity): MovieCommentEntity {
            return MovieCommentEntity(
                id = null,
                user = userEntity,
                content = commentRequest.content,
                targetDate = LocalDate.parse(commentRequest.targetDate),
            )
        }
    }
}