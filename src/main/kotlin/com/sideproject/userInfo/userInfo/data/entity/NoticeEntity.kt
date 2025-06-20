package com.sideproject.userInfo.userInfo.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "notices")
class NoticeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false)
    var admin: AdminEntity,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    var content: String,

    @Column(name = "view_count", nullable = false)
    var viewCount: Int = 0,
) : BasicEntity()