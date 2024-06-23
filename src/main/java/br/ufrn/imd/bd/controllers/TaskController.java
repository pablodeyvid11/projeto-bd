package br.ufrn.imd.bd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.bd.model.Task;
import br.ufrn.imd.bd.model.dto.ApiErrorDTO;
import br.ufrn.imd.bd.services.TaskService;
import br.ufrn.imd.bd.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Obter task por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task obtida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Task não encontrada")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTaskById(@PathVariable Long id, HttpServletRequest request) {
        try {
            Task task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiErrorDTO(HttpStatus.NOT_FOUND.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Obter tasks por evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tasks obtidas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    @GetMapping(value = "/by-evento/{eventoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTasksByEventoId(@PathVariable Long eventoId, HttpServletRequest request) {
        try {
            List<Task> tasks = taskService.getTasksByEventoId(eventoId);
            return ResponseEntity.ok(tasks);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiErrorDTO(HttpStatus.NOT_FOUND.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Criar uma nova task")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Task criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTask(@RequestBody Task task, @RequestParam Long eventoId,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                        HttpServletRequest request) {
        try {
            Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
            task.setEventId(eventoId);
            taskService.createTask(task, organizadorId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Atualizar uma task")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Task não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                        HttpServletRequest request) {
        try {
            Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
            task.setId(id);
            taskService.updateTask(task, organizadorId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Deletar uma task")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Task não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTask(@PathVariable Long id,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                        HttpServletRequest request) {
        try {
            Long organizadorId = userService.findUserFromToken(authorizationHeader).getId();
            taskService.deleteTask(id, organizadorId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }

    @Operation(summary = "Iniciar uma task")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task iniciada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping(value = "/iniciar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> iniciarTask(@RequestParam Long taskId,
                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                         HttpServletRequest request) {
        try {
            Long festeiroId = userService.findUserFromToken(authorizationHeader).getId();
            taskService.iniciarTask(taskId, festeiroId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI(), e.getMessage()));
        }
    }
}
