package com.peaksoft.entity;

import com.peaksoft.entity.enums.Specialization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@Table(name = "instructors")
@NoArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "instructor_seq")
    @SequenceGenerator(name = "instructor_seq", sequenceName = "instructor_seq", allocationSize = 1)
    private Long id;

    @Column(length = 500)
    private String firstname;

    @Column(length = 500)
    private String lastname;

    @Column(length = 500)
    private Integer phoneNumber;

    @Column(length = 500)
    private String email;

    @Column
    private Specialization specialization;

    @Column
    private int student = 0;

    public Instructor(String firstname, String lastname, Integer phoneNumber, String email, Specialization specialization) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.specialization = specialization;
    }

    @ManyToOne(cascade = {DETACH, MERGE, REFRESH}, fetch = EAGER)
    private Course course;

    public void plus() {
        student++;
    }
    public void minus() {
        student--;
    }

}
