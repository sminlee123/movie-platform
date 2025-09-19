package com.example.movieplatform.showinginfo.domain;

import com.example.movieplatform.movie.domain.Movie;
import com.example.movieplatform.screen.domain.Screen;
import com.example.movieplatform.showinginfo.domain.request.ShowingInfoCreateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@Table(name = "showing_info")
@NoArgsConstructor
@AllArgsConstructor
public class ShowingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @Column(nullable = false, name = "showing_date")
    private LocalDate showingDate;

    @Column(nullable = false, name = "start_time")
    private LocalTime startTime;

    @Column(nullable = false, name = "end_time")
    private LocalTime endTime;

    @Column(nullable = false)
    private int price;

    protected ShowingInfo(
            Movie movie,
            Screen screen,
            LocalDate showingDate,
            LocalTime startTime,
            LocalTime endTime,
            int price) {
        this.movie = movie;
        this.screen = screen;
        this.showingDate = showingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public static ShowingInfo create(
            ShowingInfoCreateRequest request,
            Movie movie,
            Screen screen,
            LocalTime endTime) {
        return new ShowingInfo(
                movie,
                screen,
                request.showingDate(),
                request.startTime(),
                endTime,
                request.price()
        );
    }

}
