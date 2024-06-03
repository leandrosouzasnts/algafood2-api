package algafood2api.exceptionhandler;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiException(Integer status,
                           String type,
                           String title,
                           String detail,
                           String instance,
                           LocalDateTime timestamp) {
}
