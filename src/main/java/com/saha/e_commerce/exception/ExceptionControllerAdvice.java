package com.saha.e_commerce.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = CustomException.class)
    public final ResponseEntity<String> handleUpdateFailException(CustomException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public final ResponseEntity<String> handleAuthenticationException(AuthenticationException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ProductException.class)
    public final ResponseEntity<String> handleProductException(ProductException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CartItemException.class)
    public final ResponseEntity<String> handleCartItemException(CartItemException exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
