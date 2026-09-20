#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

cd "$PROJECT_DIR"

echo "========================================"
echo " Packaging jar ..."
echo "========================================"

mvn clean package

echo ""
echo "========================================"
echo " Package completed!"
echo " Jar file is in: $PROJECT_DIR/target/"
echo "========================================"