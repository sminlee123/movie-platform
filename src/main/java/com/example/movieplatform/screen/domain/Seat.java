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

    @Column(name = "row_num", nullable = false)
    private int rowNumber;
    @Column(name = "col_num", nullable = false)
    private int colNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    public Seat(String name, Screen screen, int rowNumber, int colNumber) {
        this.name = name;
        this.screen = screen;
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
    }
}
