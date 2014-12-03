#!/bin/sh
mvn clean install -Dskip.unit.tests=true failsafe:integration-test
