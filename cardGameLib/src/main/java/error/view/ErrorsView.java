package error.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
public class ErrorsView {

    private List<FieldErrorView> fieldErrors;

    public static ErrorsView of(BindingResult bindingResult) {

        List<FieldErrorView> fieldErrors = collectErrors(bindingResult);

        return new ErrorsView(fieldErrors);
    }

    private static List<FieldErrorView> collectErrors(BindingResult bindingResult) {
        return bindingResult
                .getFieldErrors()
                .stream()
                .map(e -> new FieldErrorView(e.getField(), e.getDefaultMessage()))
                .collect(toList());
    }
}
