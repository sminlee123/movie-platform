package com.example.movieplatform.screen.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "seats")
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @Column(nullable = false, name = "is_available")
    private Boolean available;

    public Seat(String name, Screen screen) {
        this.name = name;
        this.screen = screen;
        this.available = true;
    }

    public void updateAvailability(boolean available) {
        this.available = available;
    }
}
