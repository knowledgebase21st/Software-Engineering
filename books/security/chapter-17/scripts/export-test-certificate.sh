#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd -- "$SCRIPT_DIR/.." && pwd)"

KEYSTORE="$PROJECT_DIR/server-keystore.p12"
CERTIFICATE="$PROJECT_DIR/server-cert.pem"

if [[ ! -f "$KEYSTORE" ]]; then
    echo "Error: Test keystore not found: $KEYSTORE" >&2
    echo "Run scripts/generate-test-keystore.sh first." >&2
    exit 1
fi

if [[ -e "$CERTIFICATE" ]]; then
    echo "Certificate already exists: $CERTIFICATE" >&2
    echo "Remove it manually if you want to export it again." >&2
    exit 1
fi

keytool -exportcert \
    -alias server \
    -keystore "$KEYSTORE" \
    -storetype PKCS12 \
    -rfc \
    -file "$CERTIFICATE"

echo "Certificate exported to: $CERTIFICATE"
