package com.adme.products.prices.rest.config;

import org.springframework.http.HttpStatus;

record ApiError(HttpStatus status, String message, String error) {

}
