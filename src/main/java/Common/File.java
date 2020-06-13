package Common;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File {
    private Long id;
    private String fileName;
}

