/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.4.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
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
import ru.got.shop.dto.AdCreateDto;
import ru.got.shop.dto.AdDto;
import ru.got.shop.dto.FullAdDto;
import ru.got.shop.dto.ResponseWrapperAdsDto;

import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Validated
@Tag(name = "Ads", description = "EndPoints related to Ads")
public interface AdsApi {

    /**
     * POST /ad : adDto
     * Добавить ads
     *
     * @param createAds createAds (required)
     * @return OK (status code 200)
     * or Created (status code 201)
     * or Unauthorized (status code 401)
     * or Bad Request (status code 400)
     * or Not Found (status code 404)
     */
    @Operation(operationId = "addAd", summary = "adding an advertisment", tags = { "Ads" }, responses = {
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
            summary = "Edit picture by advertisment id",
            tags = { "Ads" },
            responses = @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdDto.class))))
    @PatchMapping(value = "/ads/{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AdDto> editPicture(@PathVariable Integer id, @RequestParam(value = "image") MultipartFile file);

    /**
     * GET /ads : getALLAds
     *
     * @return OK (status code 200)
     */
    @Operation(operationId = "getAllAds", summary = "geting all ads wrapped into ResponseWrapperAds", tags = { "Ads" })
    @RequestMapping(method = RequestMethod.GET, value = "/ads", produces = { "application/json" })
    ResponseEntity<ResponseWrapperAdsDto> getAllAds();

    /**
     * GET /ads/me : Ads
     *
     * @param authorId Author id
     * @return ResponseEntity<ResponseWrapperAds>
     */
    @Operation(operationId = "getMyAds", summary = "get all ads of me", tags = { "Ads" })
    @RequestMapping(method = RequestMethod.GET, value = "/ads/me", produces = { "application/json" })
    ResponseEntity<ResponseWrapperAdsDto> getMyAds();

    /**
     * GET /ads/{id} : getAds
     *
     * @param id id (required)
     * @return OK (status code 200)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not Found (status code 404)
     */
    @Operation(operationId = "getFullAd", summary = "get certain full ad", tags = { "Ads" })
    @RequestMapping(method = RequestMethod.GET, value = "/ads/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FullAdDto> getFullAd(
            @Parameter(name = "id", description = "id", required = true, schema = @Schema(description = ""))
            @PathVariable("id") Integer id);

    /**
     * GET /ads/image/{uuid} : get image by ads id
     *
     * @param id id (required)
     * @return OK (status code 200)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not Found (status code 404)
     */
    @Operation(operationId = "getImage", summary = "get ad image", tags = { "Ads" })
    @RequestMapping(method = RequestMethod.GET, value = "/ads/image/{uuid}", produces = MediaType.IMAGE_PNG_VALUE)
    ResponseEntity<?> getAdImage(@PathVariable("uuid") String uuid);

    /**
     * DELETE /ads/{id} : removeAds
     *
     * @param id id (required)
     * @return No Content (status code 203)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(operationId = "removeAd",
            summary = "remove an advertisment",
            tags = { "Ads" },
            responses = @ApiResponse(responseCode = "203", description = "No content"))
    @RequestMapping(method = RequestMethod.DELETE, value = "/ads/{id}", produces = { "application/json" })
    ResponseEntity<Void> removeAd(
            @Parameter(name = "id", description = "id", required = true, schema = @Schema(description = ""))
            @PathVariable("id") Integer id);

    /**
     * PATCH /ads/{id} : updateAds
     *
     * @param id    id (required)
     * @param adDto ads (required)
     * @return OK (status code 200)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(operationId = "updateAd", summary = "update an advertisment", tags = { "Ads" })
    @RequestMapping(method = RequestMethod.PATCH,
            value = "/ads/{id}",
            produces = { "application/json" },
            consumes = { "application/json" })
    ResponseEntity<AdDto> updateAd(
            @Parameter(name = "id", description = "id", required = true, schema = @Schema(description = ""))
            @PathVariable("id") Integer id,
            @Parameter(name = "ads", description = "ads", required = true, schema = @Schema(description = ""))
            @RequestBody AdCreateDto adCreateDto);
}
