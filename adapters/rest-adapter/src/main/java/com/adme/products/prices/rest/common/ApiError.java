package com.adme.products.prices.rest.common;

/**
 *
 * @param status Http status code
 * @param code Identifies uniquely an Api Error case, useful for things like i18n, or identify it on more detail than using status
 * @param detail Details description about the issue
 */
record ApiError(Integer status, String code, String detail) {

}
