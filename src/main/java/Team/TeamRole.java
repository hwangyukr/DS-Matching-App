package Team;

import Common.Role;

public class TeamRole {

    private Team team;
    private Role role;
    private Integer current;
    private Integer maximum;

    public TeamRole(Team team, Role role, Integer current, Integer maximum) {
        this.team = team;
        this.role = role;
        this.current = current;
        this.maximum = maximum;
    }

    public Team getTeam() {
        return team;
    }

    public Role getRole() {
        return role;
    }

    public Integer getCurrent() {
        return current;
    }

    public Integer getMaximum() {
        return maximum;
    }
}
