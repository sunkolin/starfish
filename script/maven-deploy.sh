#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

cd "$PROJECT_DIR"

ENV_FILE="$PROJECT_DIR/.mvn/.env"
if [ -f "$ENV_FILE" ]; then
    echo "Loading environment from $ENV_FILE"
    set -a
    source "$ENV_FILE"
    set +a
fi

if [ -z "$GITHUB_TOKEN" ]; then
    echo "Error: GITHUB_TOKEN is not set."
    echo "Please set it in $ENV_FILE or export GITHUB_TOKEN=your_token before running this script."
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