package Team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDto {
    private String name;
    private String fileName;
    private String originalFileName;
    private Map<Role, Integer> roleLimits = new HashMap<>();
}
