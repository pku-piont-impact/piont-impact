package com.example.compose;

import java.util.List;

public class LoginResponse {
    private int statusCode;
    private Body body;

    public int getStatusCode() {
        return statusCode;
    }

    public Body getBody() {
        return body;
    }

    public static class Body {
        private List<UserPair> user_pairs;

        public List<UserPair> getUserPairs() {
            return user_pairs;
        }
    }

    public static class UserPair {
        private String userName;
        private String password;

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }
}
