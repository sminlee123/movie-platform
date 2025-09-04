package com.example.movieplatform.genre.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "genres",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    public Genre(String name) {
        this.name = name;
    }
}
