package com.eventify.controller;


import com.eventify.dto.VenueDTO;
import com.eventify.service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/venues")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    // 1. CREAR LUGAR (POST) -> Criterio de Aceptación: 201 Created
    @PostMapping
    @Operation(summary = "Registrar un nuevo lugar", description = "Crea un nuevo lugar en el catálogo y lo persiste en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lugar creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos")
    })
    public ResponseEntity<VenueDTO> createVenue(@RequestBody VenueDTO venueDTO) {
        VenueDTO createdVenue = venueService.createVenue(venueDTO);
        return new ResponseEntity<>(createdVenue, HttpStatus.CREATED);
    }

    // 2. CONSULTAR TODOS CON PAGINACIÓN (GET) -> Escenario 3: Paginación de Resultados
    @GetMapping
    @Operation(summary = "Listar lugares con paginación y ordenamiento",
            description = "Permite obtener el listado escalable de lugares. Ejemplo: ?page=0&size=10&sort=name,asc")
    @ApiResponse(responseCode = "200", description = "Consulta paginada realizada con éxito")
    public ResponseEntity<Page<VenueDTO>> getAllVenues(
            @Parameter(description = "Parámetros de paginación (page, size, sort)")
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        Page<VenueDTO> venuesPage = venueService.getAllVenues(pageable);
        return ResponseEntity.ok(venuesPage);
    }

    // 3. CONSULTAR POR ID (GET) -> Criterio de Aceptación: 200 OK / 404 Not Found
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un lugar por ID", description = "Retorna los detalles de un lugar específico a partir de su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar encontrado"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado - El ID proporcionado no existe")
    })
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        VenueDTO venueDTO = venueService.getVenueById(id);
        return new ResponseEntity<>(venueDTO, HttpStatus.CREATED);
    }

    // 4. ACTUALIZAR LUGAR (PUT) -> Criterio de Aceptación: 200 OK / 404 Not Found
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un lugar existente", description = "Modifica los atributos de un lugar si el ID existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se puede actualizar - El lugar con el ID especificado no existe")
    })
    public ResponseEntity<VenueDTO> updateVenue(@PathVariable Long id, @RequestBody VenueDTO venueDTO) {
        VenueDTO updatedVenue = venueService.updateVenue(id, venueDTO);
        return ResponseEntity.ok(updatedVenue);
    }

    // 5. ELIMINAR LUGAR (DELETE) -> Escenario 4: Eliminación Exitosa (204 No Content)
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un lugar por ID", description = "Realiza el borrado físico de un lugar existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lugar eliminado exitosamente (Sin contenido)"),
            @ApiResponse(responseCode = "404", description = "No se puede eliminar - El lugar con el ID especificado no existe")
    })
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}


