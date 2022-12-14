package ru.got.shop.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.got.shop.dto.AdsCommentDto;
import ru.got.shop.dto.ResponseWrapperAdsCommentDto;

import javax.annotation.Generated;
import javax.validation.Valid;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Validated
@Tag(name = "Comments", description = "the Ads API")
public interface AdsCommentApi {
    /**
     * POST /ads/{ad_id}/comments : addAdsComment
     *
     * @param adId    ad_id (required)
     * @param comment comment (required)
     * @return OK (status code 200)
     * or Created (status code 201)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not Found (status code 404)
     */
    @Operation(
            operationId = "addAdsComment",
            summary = "addAdsComment",
            tags = {"Comments"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdsCommentDto.class))),
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/ads/{ad_id}/comments",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<AdsCommentDto> addAdsComment(
            @Parameter(name = "ad_id", description = "ad_id", required = true, schema = @Schema(description = "")) @PathVariable("ad_id") Integer adId,
            @Parameter(name = "comments", description = "comments", required = true, schema = @Schema(description = "")) @Valid @RequestBody AdsCommentDto comment
    );


    /**
     * DELETE /ads/{ad_id}/comments/{id} : deleteAdsComment
     *
     * @param adId ad_id (required)
     * @param id   id (required)
     * @return OK (status code 200)
     * No Content (status code 204)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(
            operationId = "deleteAdsComment",
            summary = "deleteAdsComment",
            tags = {"Comments"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdsCommentDto.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/ads/{ad_id}/comments/{id}"
    )
    ResponseEntity<AdsCommentDto> deleteAdsComment(
            @Parameter(name = "ad_id", description = "ad_id", required = true, schema = @Schema(description = "")) @PathVariable("ad_id") Integer adId,
            @Parameter(name = "id", description = "id", required = true, schema = @Schema(description = "")) @PathVariable("id") Integer id
    );


    /**
     * GET /ads/{ad_id}/comments/{id} : getAdsComment
     *
     * @param adId ad_id (required)
     * @param id   id (required)
     * @return OK (status code 200)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not Found (status code 404)
     */
    @Operation(
            operationId = "getAdsComment",
            summary = "getAdsComment",
            tags = {"Comments"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdsCommentDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/ads/{ad_id}/comments/{id}",
            produces = {"application/json"}
    )
    ResponseEntity<AdsCommentDto> getAdsComment(
            @Parameter(name = "ad_id", description = "ad_id", required = true, schema = @Schema(description = "")) @PathVariable("ad_id") Integer adId,
            @Parameter(name = "id", description = "id", required = true, schema = @Schema(description = "")) @PathVariable("id") Integer id
    );


    /**
     * GET /ads/{ad_id}/comments : getAdsComments
     *
     * @param adId ad_id (required)
     * @return OK (status code 200)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     * or Not Found (status code 404)
     */
    @Operation(
            operationId = "getAdsComments",
            summary = "getAdsComments",
            tags = {"Comments"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapperAdsCommentDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/ads/{ad_id}/comments",
            produces = {"application/json"}
    )
    ResponseEntity<ResponseWrapperAdsCommentDto> getAdsComments(
            @Parameter(name = "ad_id", description = "ad_id", required = true, schema = @Schema(description = "")) @PathVariable("ad_id") Integer adId
    );


    /**
     * PATCH /ads/{ad_id}/comments/{id} : updateAdsComment
     *
     * @param adId     ad_id (required)
     * @param id       id (required)
     * @param comments comments (required)
     * @return OK (status code 200)
     * or No Content (status code 204)
     * or Unauthorized (status code 401)
     * or Forbidden (status code 403)
     */
    @Operation(
            operationId = "updateAdsComment",
            summary = "updateAdsComment",
            tags = {"Comments"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AdsCommentDto.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/ads/{ad_id}/comments/{id}",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<AdsCommentDto> updateAdsComment(
            @Parameter(name = "ad_id", description = "ad_id", required = true, schema = @Schema(description = "")) @PathVariable("ad_id") Integer adId,
            @Parameter(name = "id", description = "id", required = true, schema = @Schema(description = "")) @PathVariable("id") Integer id,
            @Parameter(name = "comments", description = "comments", required = true, schema = @Schema(description = "")) @Valid @RequestBody AdsCommentDto comments
    );
}
