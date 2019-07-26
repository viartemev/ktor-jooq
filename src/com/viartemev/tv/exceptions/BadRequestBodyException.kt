package com.viartemev.tv.exceptions

import io.ktor.http.HttpStatusCode

class BadRequestBodyException(
        details: String
) : FindAnotherNameException(HttpStatusCode.BadRequest, "Invalid body ", details)