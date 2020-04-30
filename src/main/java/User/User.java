package User;

import Team.Team;

public class User {
    private Long id;
    private Team team;
    private String email;
    private String name;
    private String password;

    public static class Builder {

        private String email;
        private String name;
        private String password;
        private Long id;
        private Team team;

        public Builder() {
        }

        public Builder email(String val) {
            email = val;
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
}
