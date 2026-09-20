#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

cd "$PROJECT_DIR"

if [ -z "$MAVEN_TOKEN" ]; then
    echo "Error: MAVEN_TOKEN environment variable is not set."
    echo "Please export MAVEN_TOKEN=your_github_token before running this script."
    exit 1
fi

echo "========================================"
echo " Deploying jar to remote repository ..."
echo "========================================"

mvn clean deploy

echo ""
echo "========================================"
echo " Deploy completed!"
echo "========================================"