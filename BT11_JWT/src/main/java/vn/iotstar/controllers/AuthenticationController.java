package vn.iotstar.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // <--- MISSING IMPORT
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// --- CORRECTED IMPORTS ---
import vn.iotstar.entity.User;              // <--- CORRECT IMPORT for your User Entity (from the images)
import vn.iotstar.models.LoginResponse;
import vn.iotstar.models.LoginUserModel;    // <--- MISSING IMPORT
import vn.iotstar.models.RegisterUserModel; // <--- MISSING IMPORT
import vn.iotstar.services.AuthenticationService;
import vn.iotstar.services.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<User> register(@RequestBody RegisterUserModel registerUser) {
        User registeredUser = authenticationService.signup(registerUser);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping(path="/login")
    @Transactional
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserModel loginUser) {
        // 1. Authenticate the user credentials
        User authenticatedUser = authenticationService.authenticate(loginUser);

        // 2. Generate a JWT token for the authenticated user
        String jwtToken = jwtService.generateToken(authenticatedUser);

        // 3. Create the response object
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        // 4. Return the response
        return ResponseEntity.ok(loginResponse);
    }
}