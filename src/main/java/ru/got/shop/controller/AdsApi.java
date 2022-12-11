package ru.got.shop.controller;

import ru.got.shop.model.dto.Ads;
import ru.got.shop.model.dto.CreateAds;
import ru.got.shop.model.dto.FullAds;
import ru.got.shop.model.dto.ResponseWrapperAds;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Validated
@Tag(name = "Ads", description = "the Ads API")
public interface AdsApi {

    /**
     * POST /ads : addAds
     * Добавить ads
     *
     * @param createAds createAds (required)
     * @return OK (status code 200)
     *         or Created (status code 201)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "addAdsUsingPOST",
        summary = "addAds",
        tags = { "Ads" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  Ads.class))),
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/ads",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<Ads> addAdsUsingPOST(
        @Parameter(name = "createAds", description = "createAds", required = true, schema = @Schema(description = "")) @Valid @RequestBody CreateAds createAds
    );


    /**
     * GET /ads : getALLAds
     *
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "getALLAdsUsingGET",
        summary = "getALLAds",
        tags = { "Ads" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ResponseWrapperAds.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/ads",
        produces = { "application/json" }
    )
    ResponseEntity<ResponseWrapperAds> getALLAdsUsingGET();


    /**
     * GET /ads/me : getAdsMe
     *
     * @param authenticated  (optional)
     * @param authorities0Authority  (optional)
     * @param credentials  (optional)
     * @param details  (optional)
     * @param principal  (optional)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "getAdsMeUsingGET",
        summary = "getAdsMe",
        tags = { "Ads" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ResponseWrapperAds.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/ads/me",
        produces = { "application/json" }
    )
    ResponseEntity<ResponseWrapperAds> getAdsMeUsingGET(
        @Parameter(name = "authenticated", description = "", schema = @Schema(description = "")) @Valid @RequestParam(value = "authenticated", required = false) Boolean authenticated,
        @Parameter(name = "authorities[0].authority", description = "", schema = @Schema(description = "")) @Valid @RequestParam(value = "authorities[0].authority", required = false) String authorities0Authority,
        @Parameter(name = "credentials", description = "", schema = @Schema(description = "")) @Valid @RequestParam(value = "", required = false) Object credentials,
        @Parameter(name = "details", description = "", schema = @Schema(description = "")) @Valid @RequestParam(value = "", required = false) Object details,
        @Parameter(name = "principal", description = "", schema = @Schema(description = "")) @Valid @RequestParam(value = "", required = false) Object principal
    );


    /**
     * GET /ads/{id} : getAds
     *
     * @param id id (required)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "getAdsUsingGET",
        summary = "getAds",
        tags = { "Ads" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  FullAds.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/ads/{id}",
        produces = { "application/json" }
    )
    ResponseEntity<FullAds> getAdsUsingGET(
        @Parameter(name = "id", description = "id", required = true, schema = @Schema(description = "")) @PathVariable("id") Integer id
    );


    /**
     * DELETE /ads/{id} : removeAds
     *
     * @param id id (required)
     * @return No Content (status code 204)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     */
    @Operation(
        operationId = "removeAdsUsingDELETE",
        summary = "removeAds",
        tags = { "Ads" },
        responses = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/ads/{id}"
    )
    ResponseEntity<Void> removeAdsUsingDELETE(
        @Parameter(name = "id", description = "id", required = true, schema = @Schema(description = "")) @PathVariable("id") Integer id
    );


    /**
     * PATCH /ads/{id} : updateAds
     *
     * @param id id (required)
     * @param ads ads (required)
     * @return OK (status code 200)
     *         or No Content (status code 204)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     */
    @Operation(
        operationId = "updateAdsUsingPATCH",
        summary = "updateAds",
        tags = { "Ads" },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  Ads.class))),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
        }
    )
    @RequestMapping(
        method = RequestMethod.PATCH,
        value = "/ads/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<Ads> updateAdsUsingPATCH(
        @Parameter(name = "id", description = "id", required = true, schema = @Schema(description = "")) @PathVariable("id") Integer id,
        @Parameter(name = "ads", description = "ads", required = true, schema = @Schema(description = "")) @Valid @RequestBody Ads ads
    );

}
