package Team;

import User.User;

import java.lang.reflect.Array;
import java.util.*;

public class Team {

    private Long id;
    private String name;
    private List<User> users = new ArrayList<>();
    private User teamLeader;
    private List<Application> applications = new ArrayList<>();
    private Map<Role, Integer> currentRoles = new HashMap<>();
    public Team() {
    }

    public Team(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.users = builder.users;
        this.teamLeader = builder.teamLeader;
        this.applications = builder.applications;
        this.currentRoles = builder.currentRoles;
    }


    public static class Builder {

        private String name;
        private User teamLeader;
        private Map<Role, Integer> currentRoles = new HashMap<>();
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
        public Builder teamRoles(Map<Role, Integer> val) {
            currentRoles = val;
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

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", teamLeader=" + teamLeader +
                ", applications=" + applications +
                ", currentRoles=" + currentRoles +
                '}';
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

    public Map<Role, Integer> getCurrentRoles() {
        return currentRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
