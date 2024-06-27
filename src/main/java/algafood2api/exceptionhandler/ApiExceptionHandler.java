package algafood2api.exceptionhandler;

import algafood2api.domain.exceptions.EntidadeNaoEncontradaException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {

        ApiException exDetail = createApiExceptionBuilder(HttpStatus.NOT_FOUND, ApiExceptionType.ENTITY_NOT_FOUND, ex.getMessage()).build();

        return handleExceptionInternal(ex, exDetail, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {

        ApiExceptionType apiExceptionType = ApiExceptionType.INVALID_DATA;

        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        BindingResult bindingResult = ex.getBindingResult();

        List<FieldsApiException> fields = bindingResult.getFieldErrors().stream()
                .map(fieldError -> FieldsApiException.builder()
                        .name(fieldError.getField())
                        .detail(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        ApiException apiException = createApiExceptionBuilder(status, apiExceptionType, detail)
                .fields(fields)
                .build();

        return handleExceptionInternal(ex, apiException, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if (body == null) {
            body = ApiException.builder()
                    .timestamp(LocalDateTime.now())
                    .title(ex.getMessage())
                    .status(statusCode.value())
                    .detail(ex.getMessage())
                    .build();
        } else if (body instanceof String) {
            body = ApiException.builder()
                    .timestamp(LocalDateTime.now())
                    .title((String) body)
                    .status(statusCode.value())
                    .detail(ex.getMessage())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private ApiException.ApiExceptionBuilder createApiExceptionBuilder(HttpStatusCode status, ApiExceptionType type,
                                                                       String detail) {

        return ApiException.builder()
                .status(status.value())
                .type(type.getUri())
                .title(type.getTitle())
                .detail(detail)
                .timestamp(LocalDateTime.now());
    }
}
