package com.example.pharmacy.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Objects;
@Entity
@Table(name = "users")
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credentials_id", nullable = false)
    private Credentials credentials;
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", dateOfBirth=").append(dateOfBirth);
        sb.append('}');
        return sb.toString();
    }
    public static Builder newBuilder() {
        return new User().new Builder();
    }
    public class Builder {

        private Builder() {
        }

        public Builder setId(int id) {
            User.super.setId(id);
            return this;
        }

        public Builder setFirstName(String firstName) {
            User.this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            User.this.lastName = lastName;
            return this;
        }

        public Builder setDateOfBirth(LocalDate dateOfBirth) {
            User.this.dateOfBirth = dateOfBirth;
            return this;
        }
        public Builder setCredentials(Credentials credentials) {
            User.this.credentials = credentials;
            return this;
        }
        public User build() {
            return User.this;
        }

    }
}
