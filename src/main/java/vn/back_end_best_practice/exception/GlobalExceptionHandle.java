package vn.back_end_best_practice.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.back_end_best_practice.dto.response.ResponseData;

@ControllerAdvice // Cho hệ thống biết đây là lớp xử lý ngoại lệ toàn cục
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ResponseData> hanleRuntimeException(RuntimeException ex) {
       ResponseData responseData = new ResponseData(
               ErrorCode.UNCATEGORIZED_EXCEPTION.getCode(),
              ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage()
       );
       return ResponseEntity.badRequest().body(responseData);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseData> hanleAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ResponseData responseData = new ResponseData(errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.badRequest().body(responseData);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ResponseData> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ResponseData responseData = new ResponseData<>(
                ErrorCode.UNCATEGORIZED_EXCEPTION.getCode(),
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        );
        return ResponseEntity.badRequest().body(responseData);
    }

}
