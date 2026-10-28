#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

cd "$PROJECT_DIR"

echo "========================================"
echo " Installing jar to local repository ..."
echo "========================================"

mvn clean install

echo ""
echo "========================================"
echo " Install completed!"
echo "========================================"