package algafood2api.exceptionhandler;

import algafood2api.domain.exceptions.EntidadeNaoEncontradaException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {

        ApiException exDetail = createApiExceptionBuilder(HttpStatus.NOT_FOUND, ApiExceptionType.ENTITY_NOT_FOUND, ex.getMessage()).build();

        return handleExceptionInternal(ex, exDetail, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if (body instanceof  String) {
            body = ApiException.builder()
                    .status(statusCode.value())
                    .title((String) body)
                    .detail(ex.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();
        } else if (body instanceof ApiException){
            body = ApiException.builder()
                    .status(statusCode.value())
                    .title(((ApiException) body).title())
                    .detail(ex.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();
        }


        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private ApiException.ApiExceptionBuilder createApiExceptionBuilder(HttpStatus status, ApiExceptionType type,
                                                                       String detail){

        return ApiException.builder()
                .status(status.value())
                .type(type.getUri())
                .title(type.getTitle())
                .detail(detail)
                .timestamp(LocalDateTime.now());
    }
}
