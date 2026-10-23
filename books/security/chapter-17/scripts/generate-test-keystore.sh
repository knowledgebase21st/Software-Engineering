#!/usr/bin/env bash
set -euo pipefail

# Project: Software Engineering Knowledge Base
# Chapter: 17 - Keystore and Truststore
# Purpose: Generate a local test keystore for demonstration.
# Requirements: Java 17 JDK (keytool), Bash

SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd -- "$SCRIPT_DIR/.." && pwd)"

KEYSTORE="$PROJECT_DIR/server-keystore.p12"

if [[ -e "$KEYSTORE" ]]; then
    echo "Keystore already exists: $KEYSTORE"
    echo "Remove it manually if you want to generate a new one."
    exit 1
fi

keytool -genkeypair \
    -alias server \
    -keyalg RSA \
    -keysize 3072 \
    -storetype PKCS12 \
    -keystore "$KEYSTORE" \
    -validity 365 \
    -dname "CN=localhost" \
    -ext "SAN=dns:localhost,ip:127.0.0.1"

echo
echo "Test keystore created: $KEYSTORE"
echo "This keystore is for local demonstrations only."
echo "Do not commit it or use it in production."
