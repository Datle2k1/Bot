package org.example;

import org.apache.hc.core5.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class API_Error {

        private HttpStatus status;
        private String message;
        private List<String> errors;

        public API_Error(HttpStatus status, String message, List<String> errors) {
            super();
            this.status = status;
            this.message = message;
            this.errors = errors;
        }

        public API_Error(HttpStatus status, String message, String error) {
            super();
            this.status = status;
            this.message = message;
            errors = Arrays.asList(error);
        }

}
