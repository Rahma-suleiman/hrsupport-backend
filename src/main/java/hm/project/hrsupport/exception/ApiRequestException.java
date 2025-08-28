package hm.project.hrsupport.exception;

public class ApiRequestException extends RuntimeException {
     // generate these 2 constructors
    public ApiRequestException(String message){
        super (message);
    }
    // public ApiRequestException(String message, Throwable cause){
    //     super (message, cause);
    // }
}
