package algafood2api.exceptionhandler;

import lombok.Builder;

@Builder
public record FieldsApiException(String name,
                                 String detail) {
}
