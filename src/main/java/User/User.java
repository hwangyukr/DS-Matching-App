package User;

import Team.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import Team.Role;

import java.util.Objects;

public class User {

    private Long id;

    @JsonIgnore
    private Team team;
    private String email;
    private String name;
    private String password;
    private Role role;

    public User() {

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {

        private String email;
        private String name;
        private String password;
        private Long id;
        private Team team;
        private Role role;

        public Builder() {
        }

        public Builder email(String val) {
            email = val;
            return this;
        }
        public Builder role(Role val) {
            role = val;
            return this;
        }
        public Builder name(String val) {
            name = val;
            return this;
        }
        public Builder password(String val) {
            // TODO : maybe encrypt the password
            password = val;
            return this;
        }
        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder team(Team val) {
            team = val;
            return this;
        }

        public User build() {
            return new User(this);
        }


    }

    public User(Builder builder) {
        this.id = builder.id;
        this.team = builder.team;
        this.email = builder.email;
        this.name = builder.name;
        this.password = builder.password;
        this.role = builder.role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", team=" + team +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
