package com.fzoo.zoomanagementsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
public class MultipleExceptions extends RuntimeException {
    private List<RuntimeException> exceptions;
}
