package algafood2api.exceptionhandler;

import algafood2api.domain.exceptions.EntidadeNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ApiException> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiException.builder()
                .timestamp(LocalDateTime.now())
                        .message(ex.getMessage())
                .build());
    }
}
