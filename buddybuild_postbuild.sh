#!/usr/bin/env bash

echo "Send coverage information to Coveralls"
./gradlew jacocoTestReport coveralls