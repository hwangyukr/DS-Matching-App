package Board;

import java.util.List;

import Team.Application;
import Team.Team;
import User.User;

public class Board {

    private Long id;
    private User user;
    private Team team;
    private String title;
    private String content;

    public Board(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.team = builder.team;
        this.title = builder.title;
        this.content = builder.content;
    }

    public static class Builder {
        private User user;
        private Team team;
        private String title;
        private String content;
        private Long id;

        public Builder() {

        }

        public Builder user(User val) {
            user = val;
            return this;
        }
        public Builder team(Team val) {
            team = val;
            return this;
        }
        public Builder title(String val) {
            title = val;
            return this;
        }
        public Builder content(String val) {
            content = val;
            return this;
        }
        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Team getTeam() {
        return team;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void setTeam(Team team) {
    	this.team = team;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public void setContent(String content) {
    	this.content = content;
    }
}
