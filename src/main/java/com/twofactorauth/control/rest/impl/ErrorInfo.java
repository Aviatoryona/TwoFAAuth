package com.twofactorauth.control.rest.impl;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {
  private String errorKey;
  @NotNull
  @NotEmpty
  private int statusCode;
  @NotNull
  @NotEmpty
  private String briefSummary;
  @NotNull
  @NotEmpty
  private String stackTrace;
  @NotNull
  @NotEmpty
  private String descriptionURL;
  private String logId;

  public ErrorInfo(@NotNull @NotEmpty int statusCode,
                   @NotNull @NotEmpty String briefSummary,
                   @NotNull @NotEmpty String stackTrace,
                   @NotNull @NotEmpty String descriptionURL) {
    this.statusCode = statusCode;
    this.briefSummary = briefSummary;
    this.stackTrace = stackTrace;
    this.descriptionURL = descriptionURL;
  }
}
