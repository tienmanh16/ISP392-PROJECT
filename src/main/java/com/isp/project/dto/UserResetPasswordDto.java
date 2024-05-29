package com.isp.project.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResetPasswordDto {
    @NotNull
    @Size(min = 6, message = "password must be at least 6 characters")
    private String oldPassword;
    @NotNull
    @Size(min = 6, message = "password must be at least 6 characters")
    private String newPassword;
    @NotNull
    @Size(min = 6, message = "password must be at least 6 characters")
    private String confirmPassword;
}
