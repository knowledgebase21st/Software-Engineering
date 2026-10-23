#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd -- "$SCRIPT_DIR/.." && pwd)"

CERTIFICATE="$PROJECT_DIR/server-cert.pem"
TRUSTSTORE="$PROJECT_DIR/test-truststore.p12"

if [[ ! -f "$CERTIFICATE" ]]; then
    echo "Error: Certificate not found: $CERTIFICATE" >&2
    echo "Run scripts/export-test-certificate.sh first." >&2
    exit 1
fi

if [[ -e "$TRUSTSTORE" ]]; then
    echo "Truststore already exists: $TRUSTSTORE" >&2
    echo "Remove it manually if you want to recreate it." >&2
    exit 1
fi

keytool -importcert \
    -alias test-server \
    -file "$CERTIFICATE" \
    -keystore "$TRUSTSTORE" \
    -storetype PKCS12

echo "Truststore created: $TRUSTSTORE"
echo "For this local demo, the imported self-signed certificate is trusted."
echo "Do not use this test truststore as a production trust configuration."
