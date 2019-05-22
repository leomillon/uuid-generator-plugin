#!/usr/bin/env bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

PROJECT_VERSION=$(./gradlew -q printVersion | tail -n 1)

echo "Project version is ${PROJECT_VERSION}"

if [[ "${PROJECT_VERSION}" == "${TRAVIS_TAG}" && "${PROJECT_VERSION}" =~ ^.*-BETA.*$ ]]; then
    ./gradlew clean check publishPlugin -PdownloadIdeaSources=false -PintellijPublishToken="${INTELLIJ_PUBLISH_TOKEN}" -PintellijPublishChannels="beta"
elif [[ "${PROJECT_VERSION}" == "${TRAVIS_TAG}" ]]; then
    ./gradlew clean check publishPlugin -PdownloadIdeaSources=false -PintellijPublishToken="${INTELLIJ_PUBLISH_TOKEN}"
else
    echo "The version and/or the tag are invalid : Nothing to deploy"
fi
