package Team;

import User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Application {

    private Long id;
    @JsonIgnore
    private Team team;
    private User user;
    private Boolean didRead;

    public Application() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getDidRead() {
        return didRead;
    }

    public void setDidRead(Boolean didRead) {
        this.didRead = didRead;
    }

    public Application(Builder builder) {
        this.id = builder.id;
        this.team = builder.team;
        this.user = builder.user;
    }

    public static class Builder {

        private Team team;
        private User user;
        private Long id;

        public Builder() {
        }

        public Builder team(Team val) {
            team = val;
            return this;
        }
        public Builder user(User val) {
            user = val;
            return this;
        }
        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Application build() {
            return new Application(this);
        }
    }
}
