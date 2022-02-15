package horenso.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder()
public class Table {
    private Long id;
    private String name;
}
