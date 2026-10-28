#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

ENV_FILE="$PROJECT_DIR/.mvn/.env"
PROFILE_FILE="$HOME/.profile"

if [ ! -f "$ENV_FILE" ]; then
    echo "Error: $ENV_FILE not found."
    exit 1
fi

if [ ! -f "$PROFILE_FILE" ]; then
    touch "$PROFILE_FILE"
fi

while IFS= read -r line || [ -n "$line" ]; do
    line="$(echo "$line" | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')"
    if [ -z "$line" ] || [[ "$line" =~ ^# ]]; then
        continue
    fi
    if [[ "$line" =~ ^([A-Za-z_][A-Za-z0-9_]*)=(.*)$ ]]; then
        key="${BASH_REMATCH[1]}"
        value="${BASH_REMATCH[2]}"
        if grep -q "^export $key=" "$PROFILE_FILE" 2>/dev/null; then
            sed -i.bak "s|^export $key=.*|export $key=$value|" "$PROFILE_FILE"
            rm -f "$PROFILE_FILE.bak"
            echo "Updated: $key"
        else
            echo "export $key=$value" >> "$PROFILE_FILE"
            echo "Added: $key"
        fi
    fi
done < "$ENV_FILE"

ensure_sources_profile() {
    local rc_file="$1"
    local target="source ~/.profile"
    if [ ! -f "$rc_file" ]; then
        touch "$rc_file"
    fi
    if ! grep -qF "$target" "$rc_file"; then
        echo "" >> "$rc_file"
        echo "$target" >> "$rc_file"
        echo "Added '$target' to $rc_file"
    fi
}

case "$SHELL" in
    *zsh*)  ensure_sources_profile "$HOME/.zshrc" ;;
    *bash*) ensure_sources_profile "$HOME/.bashrc" ;;
esac

echo ""
echo "Done. Variables synced to $PROFILE_FILE"
echo "Open a new terminal or run 'source ~/.profile' to apply."