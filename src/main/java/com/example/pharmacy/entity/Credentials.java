package com.example.pharmacy.entity;

import com.example.pharmacy.util.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
@Entity
@Table(name = "credentials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Credentials extends BaseEntity{
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Credentials{");
        sb.append("login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
    public static Credentials.Builder newBuilder() {
        return new Credentials().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Credentials.Builder setId(int id) {
            Credentials.super.setId(id);
            return this;
        }

        public Credentials.Builder setLogin(String login) {
            Credentials.this.login = login;
            return this;
        }

        public Credentials.Builder setPassword(String password) {
            Credentials.this.password = password;
            return this;
        }
        public Credentials.Builder setRole(Role role) {
            Credentials.this.role = role;
            return this;
        }

        public Credentials build() {
            return Credentials.this;
        }

    }

}
