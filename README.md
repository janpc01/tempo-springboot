User entity:
- username: required
    - if a user registers with google SSO, initial username will be set to gmail username (example: janpc01@gmail.com -> janpc01)
- email: required
- password: optional, only for non google SSO registration
- googleId: only for google SSO registration


Routes:
- Google SSO: http://localhost:8080/oauth2/authorization/google
- username, email, password registration: http://localhost:8080/api/auth/register