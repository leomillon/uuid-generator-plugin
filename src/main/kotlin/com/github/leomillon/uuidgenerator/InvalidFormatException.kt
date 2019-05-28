package com.github.leomillon.uuidgenerator

class InvalidFormatException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}
