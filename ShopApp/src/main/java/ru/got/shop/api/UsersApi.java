/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.4.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package ru.got.shop.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.dto.NewPasswordDto;
import ru.got.shop.dto.ResponseWrapperUserDto;
import ru.got.shop.dto.UserDto;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@Tag(name = "Users", description = "the Users API")
public interface UsersApi {

    @Operation(operationId = "getUserUsingGET", summary = "getUser", tags = { "Users" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Access is denied"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping(value = "/users/{id}", produces = { "application/json" })
    ResponseEntity<UserDto> getUserUsingGET(
            @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id);

    @Operation(operationId = "getUsersUsingGET", summary = "getUsers", tags = { "Users" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapperUserDto.class))),
            @ApiResponse(responseCode = "400", description = "Access is denied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping(value = "/users", produces = { "application/json" })
    ResponseEntity<ResponseWrapperUserDto> getUsersUsingGET();

    @Operation(operationId = "getUsersUsingGET", summary = "getUsers", tags = { "Users" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Access is denied")
    })
    @GetMapping(value = "/users/me", produces = { "application/json" })
    ResponseEntity<UserDto> getUserMeUsingGET();

    @Operation(operationId = "setPasswordUsingPOST", summary = "setPassword", tags = { "Users" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NewPasswordDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PostMapping(value = "/users/set_password", produces = { "application/json" }, consumes = { "application/json" })
    ResponseEntity<NewPasswordDto> setPasswordUsingPOST(
            @Parameter(name = "newPassword", description = "newPassword", required = true) @Valid @RequestBody
            NewPasswordDto newPasswordDto);

    @Operation(operationId = "updateUserUsingPATCH", summary = "updateUser", tags = { "Users" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Access is denied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PatchMapping(value = "/users/me", produces = { "application/json" }, consumes = { "application/json" })
    ResponseEntity<UserDto> updateUserUsingPATCH(
            @Parameter(name = "user", description = "user", required = true) @Valid @RequestBody UserDto userDto);

    @PatchMapping(value = "/users/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> updateUserAvatar(@RequestPart(value = "image") MultipartFile file);

    @GetMapping(value = "/users/avatar/{id}/", produces = { MediaType.IMAGE_PNG_VALUE })
    byte[] getImage(@PathVariable("id") UUID uuid);
}
