#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p out
javac --release 17 -Xlint:all -Werror -d out src/bridge/*.java test/bridge/*.java
java -cp out bridge.BridgePatternTest
