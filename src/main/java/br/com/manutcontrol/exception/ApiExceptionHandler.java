package br.com.manutcontrol.exception;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.*;
@RestControllerAdvice public class ApiExceptionHandler {
 @ExceptionHandler(ApiException.class) ResponseEntity<Map<String,Object>> api(ApiException e){return ResponseEntity.status(e.status).body(Map.of("erro",e.getMessage(),"status",e.status.value()));}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException e){
  var errors=e.getBindingResult().getFieldErrors().stream().map(f->f.getField()+": "+f.getDefaultMessage()).toList();
  return ResponseEntity.badRequest().body(Map.of("erro","Dados invalidos","detalhes",errors));
 }
 @ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<Map<String,Object>> integrity(DataIntegrityViolationException e){return ResponseEntity.status(409).body(Map.of("erro","Registro duplicado ou violacao de integridade"));}
 @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class) ResponseEntity<Map<String,Object>> json(Exception e){return ResponseEntity.badRequest().body(Map.of("erro","JSON invalido ou tipo de campo incorreto"));}
}
