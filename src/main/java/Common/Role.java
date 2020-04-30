package Common;

public class Role {

    private Long id;
    private String category;
    private String role;

    public Role(Long id, String category, String role) {
        this.id = id;
        this.category = category;
        this.role = role;
    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getRole() {
        return role;
    }
}
