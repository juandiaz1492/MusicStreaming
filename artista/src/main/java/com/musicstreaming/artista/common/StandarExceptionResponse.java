package com.musicstreaming.artista.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class StandarExceptionResponse {
    @Schema(
        description = "The unique uri identifier that categorizes the error",
        name = "type",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "/errors/authentication/not-authorized"
    )
    private String type;

    @Schema(
        description = "A brief, human-readable message about the error",
        name = "title",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "The user does not have authorization"
    )
    private String title;

    @Schema(
        description = "The unique error code",
        name = "code",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "192"
    )
    private int code;

    @Schema(
        description = "A human-readable explanation of the error",
        name = "detail",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "The user does not have the properly permissions to access the resource, please contact with us https://sotobotero.com"
    )
    private String detail;

    @Schema(
        description = "A URI that identifies the specific occurrence of the error",
        name = "detail",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "/errors/authentication/not-authorized/01"
    )
    private String instance;

    public StandarExceptionResponse(String type, String title, int code, String detail) {
        super();
        this.type = type;
        this.title = title;
        this.code = code;
        this.detail = detail;
    }

}
