package algafood2api.exceptionhandler;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiException(LocalDateTime timestamp, String message) {
}
