package dev.igor.picpay.controller;

import dev.igor.picpay.exceptions.PicPayException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(PicPayException.class)
    public ProblemDetail handlePicPayException(PicPayException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var fieldErrors = e.getFieldErrors().stream().map(f -> new InvalidParam(f.getField(), f.getDefaultMessage())).toList();
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Your request parameters are invalid");
        pb.setProperty("invalid-parameters", fieldErrors);
        return pb;
    }

    private record InvalidParam(String fieldName, String readon){}
}
