package io.github.mrvictor42.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectsIsNullExceptions : RuntimeException {

    constructor() : super("It's not allowed to persist a null object!!")
    constructor(exception: String?) : super(exception)
}