package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomPage<T> {
    private List<T> content;
    private int numberOfElements;
    private int number;
    private long totalElements;
    private int totalPages;
    private int size;

    public CustomPage(int numberOfElements, int number, long totalElements, int totalPages, int size) {
        this.numberOfElements = numberOfElements;
        this.number = number;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.size = size;
    }
}
