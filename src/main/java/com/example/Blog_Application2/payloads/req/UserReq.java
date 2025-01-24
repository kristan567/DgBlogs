package com.example.Blog_Application2.payloads.req;


import com.example.Blog_Application2.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReq {
    @NotEmpty(message = "First name cannot be blank")
    private String firstName;

    private String middleName;

    @NotEmpty(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Invalid Email")
    private String email;

    @Length(min = 5, max = 15, message = "password must be 5 to 15 character long")
    private String password;

    @NotEmpty(message = "confirm Password is required")
    private String confirmPassword;

    private Role role;

    private String phone;

    private String imageName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
