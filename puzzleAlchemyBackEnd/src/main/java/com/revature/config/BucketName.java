package com.revature.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    TODO_IMAGE("puzzle-alchemy-pieces");
    private final String bucketName;
}