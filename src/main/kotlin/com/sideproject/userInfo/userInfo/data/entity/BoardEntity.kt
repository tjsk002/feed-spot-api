package com.sideproject.userInfo.userInfo.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "boards")
class BoardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    var user: UserEntity,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    var content: String,

    @Column(name = "comment_count", nullable = false)
    var commentCount: Int = 0,

    @Column(name = "view_count", nullable = false)
    var viewCount: Int = 0,
) : BasicEntity()