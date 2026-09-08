#!/bin/bash

# ==============================================================================
# Chapter 6 – Authentication Attacks
# Cleanup Script
# ==============================================================================

set -e

echo "============================================================"
echo " Chapter 6 – Authentication Attacks"
echo " Cleanup"
echo "============================================================"
echo

# Determine the Chapter 6 directory.
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CHAPTER_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

echo "Chapter directory:"
echo "  $CHAPTER_DIR"
echo

# Remove Maven build output.
if [ -d "$CHAPTER_DIR/target" ]; then
    echo "Removing Maven build directory..."
    rm -rf "$CHAPTER_DIR/target"
fi

# Remove build output from individual examples.
for directory in \
    "$CHAPTER_DIR/brute-force" \
    "$CHAPTER_DIR/dictionary-attack" \
    "$CHAPTER_DIR/rainbow-table" \
    "$CHAPTER_DIR/password-spraying" \
    "$CHAPTER_DIR/credential-stuffing"
do
    if [ -d "$directory/target" ]; then
        echo "Removing target directory from $(basename "$directory")..."
        rm -rf "$directory/target"
    fi
done

echo
echo "Cleanup completed successfully."
echo "============================================================"
