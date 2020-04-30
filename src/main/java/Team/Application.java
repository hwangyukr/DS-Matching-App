package Team;

import User.User;

public class Application {
    private Long id;
    private Team team;
    private User user;

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
