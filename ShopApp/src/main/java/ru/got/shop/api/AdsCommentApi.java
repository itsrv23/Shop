package ru.got.shop.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.got.shop.dto.AdCommentDto;
import ru.got.shop.dto.ResponseWrapperAdsCommentDto;

import javax.validation.Valid;

@Validated
@Tag(name = "Comments", description = "the Ads API")
public interface AdsCommentApi {

    @Operation(operationId = "addAdsComment", summary = "addAdsComment", tags = { "Comments" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdCommentDto.class))),
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @PostMapping(value = "/ads/{ad_id}/comments", produces = { "application/json" }, consumes = { "application/json" })
    ResponseEntity<AdCommentDto> addAdsComment(
            @Parameter(name = "ad_id", description = "ad_id", required = true) @PathVariable("ad_id") Integer adId,
            @Parameter(name = "comments", description = "comments", required = true) @Valid @RequestBody
            AdCommentDto comment);

    @Operation(operationId = "deleteAdsComment", summary = "deleteAdsComment", tags = { "Comments" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdCommentDto.class))),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping(value = "/ads/{ad_id}/comments/{id}")
    ResponseEntity<AdCommentDto> deleteAdsComment(
            @Parameter(name = "ad_id", description = "ad_id", required = true) @PathVariable("ad_id") Integer adId,
            @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id);

    @Operation(operationId = "getAdComment", summary = "getAdComment", tags = { "Comments" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdCommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping(value = "/ads/{ad_id}/comments/{id}", produces = { "application/json" })
    ResponseEntity<AdCommentDto> getAdsComment(
            @Parameter(name = "ad_id", description = "ad_id", required = true) @PathVariable("ad_id") Integer adId,
            @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id);

    @Operation(operationId = "getAdsComments", summary = "getAdsComments", tags = { "Comments" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapperAdsCommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping(value = "/ads/{ad_id}/comments", produces = { "application/json" })
    ResponseEntity<ResponseWrapperAdsCommentDto> getAdsComments(
            @Parameter(name = "ad_id", description = "ad_id", required = true) @PathVariable("ad_id") Integer adId);

    @Operation(operationId = "updateAdsComment", summary = "updateAdsComment", tags = { "Comments" }, responses = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AdCommentDto.class))),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PatchMapping(value = "/ads/{ad_id}/comments/{id}",
            produces = { "application/json" },
            consumes = { "application/json" })
    ResponseEntity<AdCommentDto> updateAdsComment(
            @Parameter(name = "ad_id", description = "ad_id", required = true) @PathVariable("ad_id") Integer adId,
            @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id,
            @Parameter(name = "comments", description = "comments", required = true) @Valid @RequestBody
            AdCommentDto comments);
}
