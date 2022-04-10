package com.vire.dao;

import javax.persistence.*;

@Entity
@Table(name = "t_social_post_send_to")
public class SocialPostSendToDao_old {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", length = 191)
    private String type;

    @Column(name = "value", length = 191)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "social_id")
    private SocialDao social;

    public SocialDao getSocial() {
        return social;
    }

    public void setSocial(SocialDao social) {
        this.social = social;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}