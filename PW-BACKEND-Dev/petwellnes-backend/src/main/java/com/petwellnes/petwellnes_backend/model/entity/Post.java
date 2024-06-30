package com.petwellnes.petwellnes_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATE")
    private Date date;

    @Column(columnDefinition = "TIME")
    private Time time;

    @Column(nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "pet_type_id", nullable = false)
    private Type petType;

    @Column(length = 10485760)
    private String image;

    @Column(length = 10485760)
    private String video;

    @Column(nullable = false, length = 10485760)
    private String content;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;

    @Column
    private int reactions = 0;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Race race;

    public Post(User user, String category, Long topicId, Long petTypeId, String image, String video, String content) {
        this.date = new Date();
        this.time = new Time(System.currentTimeMillis());
        this.user = user;
        this.category = category;
        this.topic = new Topic();
        this.topic.setId(topicId);
        this.petType = new Type();
        this.petType.setId(petTypeId);
        this.image = image;
        this.video = video;
        this.content = content;

    }

    public Post(User user, String category, Long topicId, Long petTypeId, String image, String video, String content, Long breedId) {
        this(user, category, topicId, petTypeId, image, video, content);
        if (breedId != null) {
            this.race = new Race();
            this.race.setId(breedId);
        }
    }

    public Post() {
        this.date = new Date();
        this.time = new Time(System.currentTimeMillis());
    }
}
