package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.NamedQueries;

/**
 * @author Muhammad Mo'minov
 * 12.10.2021
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ErrorData {
    private String message; // Bla bla fildni bermading
    private Integer code; // 400
}
