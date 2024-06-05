package algafood2api.exceptionhandler;


import lombok.Getter;

@Getter
public enum ApiExceptionType {

    ENTITY_NOT_FOUND("/entidade-nao-encontrada", "Entidade não encontrada");

    private String uri;
    private String title;

    ApiExceptionType(String patch, String title){
        this.uri = "https://algafood2.com.br" + patch;
        this.title = title;
    }
}
