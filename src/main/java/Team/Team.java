package Team;

import User.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Team {

    private Long id;
    private String name;
    private List<User> users = new ArrayList<>();
    private User teamLeader;
    private List<Application> applications = new ArrayList<>();
    private List<TeamRole> teamRoles = new ArrayList<>();

    public Team() {

    }

    public Team(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.users = builder.users;
        this.teamLeader = builder.teamLeader;
        this.applications = builder.applications;
        this.teamRoles = builder.teamRoles;
    }


    public static class Builder {

        private String name;
        private User teamLeader;
        private List<TeamRole> teamRoles;
        private Long id;
        private List<User> users = new ArrayList<>();
        private List<Application> applications = new ArrayList<>();

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }
        public Builder teamLeader(User val) {
            teamLeader = val;
            return this;
        }
        public Builder teamRoles(List<TeamRole> val) {
            teamRoles = val;
            return this;
        }
        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Team build() {
            return new Team(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public List<TeamRole> getTeamRoles() {
        return teamRoles;
    }

    public User getTeamLeader() {
        return teamLeader;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setTeamLeader(User teamLeader) {
        this.teamLeader = teamLeader;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public void setTeamRoles(List<TeamRole> teamRoles) {
        this.teamRoles = teamRoles;
    }
}
