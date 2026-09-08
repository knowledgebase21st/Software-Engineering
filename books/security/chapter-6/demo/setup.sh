#!/bin/bash

# ==============================================================================
# Chapter 6 – Authentication Attacks
# Setup Script
# ==============================================================================

set -e

echo "============================================================"
echo " Chapter 6 – Authentication Attacks"
echo " Setup"
echo "============================================================"
echo

# Determine the Chapter 6 directory.
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CHAPTER_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

echo "Chapter directory:"
echo "  $CHAPTER_DIR"
echo

# Check Java.
if ! command -v java >/dev/null 2>&1; then
    echo "ERROR: Java is not installed or is not in PATH."
    echo "Please install Java 17 or later."
    exit 1
fi

echo "Java version:"
java -version
echo

# Check Maven.
if ! command -v mvn >/dev/null 2>&1; then
    echo "ERROR: Maven is not installed or is not in PATH."
    echo "Please install Maven 3.8 or later."
    exit 1
fi

echo "Maven version:"
mvn -version
echo

# Create the directories used by the examples.
echo "Creating Chapter 6 directories..."

mkdir -p "$CHAPTER_DIR/brute-force"
mkdir -p "$CHAPTER_DIR/dictionary-attack"
mkdir -p "$CHAPTER_DIR/rainbow-table"
mkdir -p "$CHAPTER_DIR/password-spraying"
mkdir -p "$CHAPTER_DIR/credential-stuffing"

echo
echo "Chapter 6 setup completed successfully."
echo
echo "The environment is ready for the Chapter 6 examples."
echo "============================================================"
