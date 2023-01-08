package ru.got.shop.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.dto.*;

import java.util.List;

@Validated
@Tag(name = "Ads", description = "EndPoints related to Ads")
public interface AdsApi {

    @Operation(operationId = "addAd", summary = "adding an advertisement", tags = { "Ads" }, responses = {
            @ApiResponse(responseCode = "201",
                    description = "Created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })

    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
            @Content(encoding = @Encoding(name = "properties", contentType = MediaType.APPLICATION_JSON_VALUE)),
            @Content(encoding = @Encoding(name = "image", contentType = "image/*"))
    })
    @PostMapping(value = "/ads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<AdDto> addAd(@RequestPart(value = "properties") AdCreateDto adCreateDto,
                                @RequestParam(value = "image") MultipartFile file);

    @Operation(operationId = "Edit picture",
            summary = "Edit picture by advertisement id",
            tags = { "Ads" },
            responses = @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdDto.class))))
    @PatchMapping(value = "/ads/{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AdDto> editPicture(@PathVariable Integer id, @RequestParam(value = "image") MultipartFile file);

    @Operation(operationId = "getAllAds", summary = "getting all ads wrapped into ResponseWrapperAds", tags = { "Ads" })
    @GetMapping(value = "/ads", produces = { "application/json" })
    ResponseEntity<ResponseWrapperAdsDto> getAllAds();

    @Operation(operationId = "getMyAds",
            summary = "get all ads of me",
            tags = { "Ads" },
            responses = @ApiResponse(responseCode = "401", description = "Unauthorized"))
    @GetMapping(value = "/ads/me", produces = { "application/json" })
    ResponseEntity<ResponseWrapperAdsDto> getMyAds();

    @Operation(operationId = "getFullAd", summary = "get certain full ad", tags = { "Ads" })
    @GetMapping(value = "/ads/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FullAdDto> getFullAd(
            @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id);

    @Operation(operationId = "getImage", summary = "get ad image", tags = { "Ads" })
    @GetMapping(value = "/ads/image/{uuid}", produces = MediaType.IMAGE_PNG_VALUE)
    ResponseEntity<byte[]> getAdImage(@PathVariable("uuid") String uuid);

    @Operation(operationId = "removeAd",
            summary = "remove an advertisement",
            tags = { "Ads" },
            responses = @ApiResponse(responseCode = "203", description = "No content"))
    @DeleteMapping(value = "/ads/{id}", produces = { "application/json" })
    ResponseEntity<Void> removeAd(
            @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id);

    @Operation(operationId = "updateAd", summary = "update an advertisement", tags = { "Ads" })
    @PatchMapping(value = "/ads/{id}", produces = { "application/json" }, consumes = { "application/json" })
    ResponseEntity<AdDto> updateAd(
            @Parameter(name = "id", description = "id", required = true) @PathVariable("id") Integer id,
            @Parameter(name = "ads", description = "ads", required = true) @RequestBody AdCreateDto adCreateDto);

    @Operation(operationId = "getAdsByCriteria", summary = "get ads by Criteria", tags = { "Ads" })
    @PostMapping(value = "/ads/filter", produces = { "application/json" }, consumes = { "application/json" })
    ResponseEntity<List<AdDto>> getAdsByCriteria(
            @Parameter(name = "adCriteriaDto", description = "adCriteriaDto", required = true) @RequestBody
            AdCriteriaDto adCriteriaDto,
            @Parameter(name = "page", description = "page") @RequestParam("page") Integer page,
            @Parameter(name = "size", description = "size") @RequestParam("size") Integer size,
            @Parameter(name = "sort", description = "sort") @RequestParam("sort") String sort);
}
