package response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T>  implements CardGameResponse {

    private int totalPages;
    private int currentPageNumber;
    @Singular("content")
    private List<T> content;

}
